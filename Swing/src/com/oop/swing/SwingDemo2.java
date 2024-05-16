package com.oop.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SwingDemo2 implements ItemListener, ActionListener {
    JLabel jLabel;

    @Override
    public void actionPerformed(ActionEvent e) {
        jLabel.setText(e.getActionCommand() + " chosen");
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JCheckBox checkBox = (JCheckBox) e.getItem();
        System.out.println(checkBox.getText() + " is " + (checkBox.isSelected() ? "selected" : "unselected"));
        if (checkBox.isSelected()) {
            jLabel.setText(checkBox.getText() + " is selected!");
        } else {
            jLabel.setText(checkBox.getText() + " is unselected!");
        }
    }

    SwingDemo2() {
        JFrame jFrame = new JFrame("Demo 2");
        jFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(275, 100);

        jLabel = new JLabel("Waiting");
        jFrame.add(jLabel);

        JCheckBox jCheckBox = new JCheckBox("C");
        jCheckBox.addItemListener(this);
        jFrame.add(jCheckBox);

        jCheckBox = new JCheckBox("C++");
        jCheckBox.addItemListener(this);
        jFrame.add(jCheckBox);

        jCheckBox = new JCheckBox("Java");
        jCheckBox.addItemListener(this);
        jFrame.add(jCheckBox);

        JRadioButton jRadioButton = new JRadioButton("Opt 1");
        jRadioButton.addActionListener(this);
        jFrame.add(jRadioButton);

        JRadioButton jRadioButton2 = new JRadioButton("Opt 2");
        jRadioButton2.addActionListener(this);
        jFrame.add(jRadioButton2);

        JRadioButton jRadioButton3 = new JRadioButton("Opt 3");
        jRadioButton3.addActionListener(this);
        jFrame.add(jRadioButton3);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(jRadioButton);
        buttonGroup.add(jRadioButton2);
        buttonGroup.add(jRadioButton3);

        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SwingDemo2();
        });
    }
}
