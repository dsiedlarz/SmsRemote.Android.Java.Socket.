package com.dsiedlarz.SMSRemoteServer;

/**
 * Created by Dawid on 24.05.2016.
 */
import com.example.dawid.smsremote.Sms;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Dawid on 13.05.2016.
 */
public class Server extends Thread {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static InputStreamReader inputStreamReader;
    private static BufferedReader bufferedReader;
    private static ArrayList<ArrayList<String>> messages;
    private SMSRemoteServer app;

    public Server(SMSRemoteServer app){
        this.app=app;
    }

     public  void run() {
         super.run();
        try {
            serverSocket = new ServerSocket(4449);  //Client socket

        } catch (IOException e) {
            System.out.println("Could not listen on port: 4444");
        }

        System.out.println("Client started. Listening to the port 4444");

        while (true) {
            try {
                System.out.println("czekam na klienta");
                clientSocket = serverSocket.accept();   //accept the client connection
                inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

                messages= (ArrayList<ArrayList<String>>)objectInputStream.readObject();


StringBuilder stringBuilder = new StringBuilder();
                for (int i=messages.size()-1;i>=0;i--)
                      {
                          if(messages.get(i).get(3).compareToIgnoreCase("1")==0)
                              stringBuilder.append("od: ");
                              else
                              stringBuilder.append("do: ");
                    stringBuilder.append(messages.get(i).get(0)+"\n"+messages.get(i).get(1)+"\n\n");

                }
                app.getMessages().setText(stringBuilder.toString());

                inputStreamReader.close();
                clientSocket.close();

            } catch (IOException ex) {
                System.out.println("Problem in message reading");
                ex.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
    }
}