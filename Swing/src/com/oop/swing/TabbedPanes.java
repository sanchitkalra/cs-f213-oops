package com.oop.swing;

import javax.swing.*;
import java.awt.*;

class CitiesPane extends JPanel {
    public CitiesPane() {
        JButton jButton1 = new JButton("HYD");
        JButton jButton2 = new JButton("DEL");
        JButton jButton3 = new JButton("IXC");

        add(jButton1);
        add(jButton2);
        add(jButton3);
    }
}

class ColorsPane extends JPanel {
    public ColorsPane() {
        JButton jButton1 = new JButton("R");
        JButton jButton2 = new JButton("G");
        JButton jButton3 = new JButton("B");

        add(jButton1);
        add(jButton2);
        add(jButton3);
    }
}

class CoursesPane extends JPanel {
    public CoursesPane() {
        JComboBox<String> jComboBox = new JComboBox<>();
        jComboBox.addItem("OOP");
        jComboBox.addItem("DBMS");
        jComboBox.addItem("OS");

        add(jComboBox);
    }
}

public class TabbedPanes {
    TabbedPanes() {
        JFrame jFrame = new JFrame();
        jFrame.setSize(600, 400);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(new FlowLayout(FlowLayout.CENTER));

        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab("Cities", new CitiesPane());
        jTabbedPane.addTab("Colors", new ColorsPane());
        jTabbedPane.addTab("Courses", new CoursesPane());

        jFrame.add(jTabbedPane);

        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TabbedPanes();
        });
    }
}
