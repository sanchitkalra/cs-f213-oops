package com.oop;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    Main() {
        JFrame frame = new JFrame("Swing Frontend for SpringBoot backend");
        frame.setLayout(new FlowLayout(FlowLayout.CENTER));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Add Person", new AddPersonPane());
        tabbedPane.addTab("Retrieve Person", new RetrievePersonPane());
        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new Main());
    }
}

class AddPersonPane extends JPanel {
    public AddPersonPane() {
        JTextField nameField = new JTextField("Name", 15);
        JTextField emailField = new JTextField("email", 15);
        JButton saveButton = new JButton("Save");
        JLabel statusLabel = new JLabel("Status");

        nameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(nameField.getText().equals("Name"))
                    nameField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(nameField.getText().equals(""))
                    nameField.setText("Name");
            }
        });



        saveButton.addActionListener(e -> {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/demo/add"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"name\":\"" + nameField.getText() + "\", \"email\":\"" + emailField.getText() + "\"}"))
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println(response.body());
                if (response.statusCode() == 200) {
                    statusLabel.setText("Success");
                }
                else {
                    statusLabel.setText("Error");
                }
                revalidate();
            }
            catch (IOException | InterruptedException ex) {
                ex.printStackTrace();
            }

        });
        add(nameField);
        add(emailField);
        add(saveButton);
        add(statusLabel);
    }
}

class RetrievePersonPane extends JPanel implements ActionListener {
    JTable table;
    JRadioButton b1;
    JRadioButton b2;
    JTextField idField;
    JScrollPane jScrollPane;
    Object[] columnNames = {"ID", "Name", "Email"};
    Object[][] dummy;
    public RetrievePersonPane() {
        b1 = new JRadioButton("All");
        b2 = new JRadioButton("By ID");
        add(b1);
        add(b2);
        ButtonGroup group = new ButtonGroup();
        group.add(b1);
        group.add(b2);
        idField = new JTextField("Enter ID", 15);
        add(idField);
        //columnNames. = {"ID", "Name", "Email"};
        dummy = new Object[20][3];
        table = new JTable(dummy, columnNames);
        jScrollPane = new JScrollPane(table);
        add(jScrollPane);
        JButton fetchButton = new JButton("Fetch");
        add(fetchButton);
        fetchButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (b2.isSelected()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/demo/id?id="+idField.getText()))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            try{
                CompletableFuture<HttpResponse<String>>  response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                JSONObject jo = new JSONObject(response.get().body());
                table.getModel().setValueAt(jo.get("id"), 0, 0);
                table.getModel().setValueAt(jo.get("name"), 0, 1);
                table.getModel().setValueAt(jo.get("email"), 0, 2);

                revalidate();
            }
            catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        }
        else {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/demo/all"))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            HttpClient client = HttpClient.newHttpClient();
            try{
                CompletableFuture<HttpResponse<String>>  response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                JSONArray jsonArray = new JSONArray(response.get().body());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    table.getModel().setValueAt(jo.get("id"), i, 0);
                    table.getModel().setValueAt(jo.get("name"), i, 1);
                    table.getModel().setValueAt(jo.get("email"), i, 2);
                }
                revalidate();
            }
            catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
        }
    }
}