package com.Erag0.JavaClient.GUI;

import com.Erag0.JavaClient.Network.IncomingReader;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainWindow {
    private JTextArea incoming;
    private JTextField outgoing;
    private PrintWriter writer;
    private Socket socket;
    private BufferedReader reader;
    private String username;
    private JFrame frame;
    private JPanel regPanel;
    private JPanel mainPanel;

    public void go() {
        frame = new JFrame("Chat");
        regPanel = getRegPanel();
        mainPanel = getChatPanel();
        setUpNetworking();

        Thread readerThread = new Thread(new IncomingReader(reader, incoming));
        readerThread.start();

        addComponentToFrame(frame, regPanel);
        frame.setSize(800, 500);
        frame.setVisible(true);
    }

    private void setUpNetworking() {
        try {
            socket = new Socket("", );
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("networking established!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private JPanel getChatPanel() {
        JPanel mainPanel = new JPanel();
        incoming = new JTextArea(15,50);
        incoming.setLineWrap(true);
        incoming.setEditable(false);
        JScrollPane qScroller = new JScrollPane(incoming);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outgoing = new JTextField(20);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener( e -> {
            try {
                writer.println(username + ": " + outgoing.getText());
                writer.flush();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            outgoing.setText("");
            outgoing.requestFocus(); });

        mainPanel.add(qScroller);
        mainPanel.add(outgoing);
        mainPanel.add(sendButton);
        return mainPanel;
    }

    private JPanel getRegPanel() {
        JPanel regPanel = new JPanel();
        JTextField field = new JTextField(20);
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            username = field.getText();
            field.setText("");
            removeComponentFromFrame(frame, regPanel);
            addComponentToFrame(frame, mainPanel);
            writer.println(username + " joined to chat!");
            writer.flush();
        });
        regPanel.add(sendButton);
        regPanel.add(field);
        return regPanel;
    }

    private void removeComponentFromFrame(JFrame frame, Component component) {
        frame.remove(component);
    }

    private void addComponentToFrame(JFrame frame, Component component) {
        frame.add(BorderLayout.CENTER, component);
        frame.validate();
        frame.repaint();
    }

}
