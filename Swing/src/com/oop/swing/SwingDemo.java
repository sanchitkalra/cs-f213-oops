package com.oop.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/*
 optionally the class can implement the ActionListener interface
 and override the actionPerformed method and
 then use this common override for all action listener
 by retrieving the component that triggered the action by using the
 e.getActionCommand() method
*/
public class SwingDemo implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        System.out.println(action);
    }

    SwingDemo() {
        // create a top level container, in this case, a JFrame (this should always be at the top of the component hierarchy)
        // JFrame does not inherit JComponent; JPanel inherits JComponent and is a lighter version
        JFrame jFrame = new JFrame("Swing Demo");
        jFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        jFrame.setSize(475, 350);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // now adding a label inside this frame
        // this is not an interactive element
        JLabel jLabel = new JLabel("OOP");
        // this will by default be positioned in the center vertically and to the extreme left horizontally, if no layout specified
        jFrame.add(jLabel);
        // now since FlowLayout is specified, they will be positioned next to one another horizontally in the center
        jFrame.add(new JLabel("is fun"));

        // these will also be positioned next to the above two texts, until space continues to be available, and then to the next line
        JButton jButton = new JButton("Button 1");
        jButton.setActionCommand("new action command"); // using this we can change to anything we want
        JButton jButton1 = new JButton("Button 2");

        // now we attach an action listener to our button that waits for an action to happen on our button
        // the parameter is the functional interface ActionListener which has one method called `actionPerformed(ActionEvent) -> void`
        // specified it using a lambda here
//        jButton.addActionListener((ActionEvent e) -> {
//            // change the text of label1
//            jLabel.setText("Yo, you clicked this yay");
//        });

        // alt to the above, we can also now use the override implemented in this class
        jButton.addActionListener(this); // this should now print "new action command"

        // the following is another representation of how the functional interface can be used
        jButton1.addActionListener(new ActionListener() {
            // here we are overriding the `actionPerformed(ActionEvent) -> void` method defined by the ActionListener interface
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand(); // this is by default the button label; but can be changed
                System.out.println(command + " pressed"); // should print the label + pressed
                jButton1.setText("you pressed me! :)");

                // we can also set icons for an element as follows
                // jLabel.setIcon(new ImageIcon(imagePath));
            }
        });

        jFrame.add(jButton);
        jFrame.add(jButton1);

        /*
         we can also add images with text using the Icon overload of JLabel
                String path = "/Users/mk/Downloads/Sanchit/BITS/Semester 8/ilovepdf_images-extracted/img19.jpg";
                JLabel labelWithIcon = new JLabel("Image", new ImageIcon(path), JLabel.CENTER);
                jFrame.add(labelWithIcon);
        */

        // text fields
        JTextField jTextField = new JTextField("default text", 15); // 15 -> width of the text field in terms of characters
        // we can add event listeners on text fields as well
        jTextField.addActionListener((actionEvent) -> {
            // this event will be triggered only when the enter key is pressed
            jLabel.setText(jTextField.getText());
        });
        jFrame.add(jTextField);

        // adding a toggle button
        JToggleButton jToggleButton = new JToggleButton("Toggle button");
        // this has a special action listener called the
        jToggleButton.addItemListener(new ItemListener() {
            // this can also be done with the same approach as for ActionListener
            // by implementing the ItemListener interface and
            // overriding the itemStateChanged method and passing `this` to the addItemListener method
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (jToggleButton.isSelected()) {
                    jToggleButton.setText("Selected");
                } else {
                    jToggleButton.setText("Not Selected");
                }
            }
        });
        jFrame.add(jToggleButton);

        jFrame.setVisible(true); // default visibility is false
    }

    public static void main(String[] args) {

        // we are asking Swing to create a new thread and run our UI in that thread
        // the logic is that UI rendering is heavy and we don't want to block our main thread, hence, we do it on a separate thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // create an instance of our UI
                new SwingDemo();
            }
        });

        /*
         the following lambda representation for the functional interface Runnable is also ok
         if both the above and this are run, two windows will be created but even if one exits, both will exit
         this behaviour is because of the JFrame.EXIT_ON_CLOSE setup
                SwingUtilities.invokeLater(() -> {
                    new SwingDemo();
                });
        */
    }
}
