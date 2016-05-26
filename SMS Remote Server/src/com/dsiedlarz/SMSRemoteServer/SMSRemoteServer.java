package com.dsiedlarz.SMSRemoteServer;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created by Dawid on 2016-03-31.
 */
public class SMSRemoteServer extends JFrame {


    private JButton backButton = new JButton("Back");
    private JButton confirmButton = new JButton("Confirm");
    private BackListener backListener = new BackListener();
    private ConfirmListener confirmListener = new ConfirmListener();

    private JTextArea messages = new JTextArea(45,50);
    private JScrollPane jScrollPane;
    private JTextArea message = new JTextArea(3,45);
    private JTextArea number = new JTextArea(1,15);
    private JLabel address = new JLabel("Adresat:");
    private JLabel ipLabel;
    private Writer writer;
    private JLabel toSend=new JLabel("ctr+Enter to send");

    public JTextArea getMessages() {
        return messages;
    }

    private int zmienne, rownania;

    class BackListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {

                    setVisible(false);
                }
            });

        }


    }

    class ConfirmListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
ArrayList<String> tmp = new ArrayList<>();
            if(!number.getText().isEmpty() &&!messages.getText().isEmpty()) {
                tmp.add(number.getText());
                tmp.add(message.getText());
                writer.send(tmp);
            }
            message.setText("");
            //setVisible(false);

        }
    }





    public SMSRemoteServer() {
        Server server = new Server(this);
        server.start();
        InetAddress IP= null;
        try {
            IP = InetAddress.getLocalHost();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        ipLabel = new JLabel("Your IP address: "+IP.getHostAddress()+"     (if you have virtual ethernet cards check ip addres in console)");
        jScrollPane = new JScrollPane(messages);
        DefaultCaret caret = (DefaultCaret)messages.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        setSize(800, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "Are you sure to close?");
                if (i == 0) {

                    System.exit(0);
                }
            }
        });



        backButton.addActionListener(backListener);
        confirmButton.addActionListener(confirmListener);
        setTitle("SMS remote");
        setLayout(new MigLayout());






        messages.setEditable(false);



        add(ipLabel,"wrap");

        add(jScrollPane,"wrap");

        add(address,"wrap");
        add(number,"wrap");
        add(message,"wrap");
        add(toSend,"wrap");
       // add(backButton,"wrap");
        add(confirmButton);


        setVisible(true);

        writer= new Writer();
        writer.start();


        SwingUtilities.getRootPane(confirmButton).setDefaultButton(confirmButton);

    }






}
