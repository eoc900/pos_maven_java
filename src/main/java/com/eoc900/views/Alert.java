package com.eoc900.views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class Alert {
    public String title;
    public String message;

    public Alert(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public void popupNormal(int width) {
        JFrame window = new JFrame();
        window.setSize(width, 150);
        window.setLocation(500, 400);
        window.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JPanel text = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        JLabel title = new JLabel(this.title);
        title.setFont(new Font("Sans Serif", Font.BOLD, 20));
        title.setForeground(Color.magenta);
        title.setBorder(new EmptyBorder(10, 0, 0, 0));
        JLabel message = new JLabel(this.message);
        message.setFont(new Font("Sans Serif", Font.PLAIN, 14));
        message.setBorder(new EmptyBorder(10, 0, 0, 0));
        text.add(title);
        text.add(message);
        window.add(text);

        window.setVisible(true);

    }
}
