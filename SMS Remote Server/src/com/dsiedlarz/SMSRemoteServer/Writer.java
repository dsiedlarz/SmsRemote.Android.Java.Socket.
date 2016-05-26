package com.dsiedlarz.SMSRemoteServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Dawid on 24.05.2016.
 */
public class Writer extends Thread {

    static Socket socket=null;

    @Override
    public void run() {
        super.run();
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4441);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true) {
            try {

                System.out.println("włączyłem i czekam na klienta");
                socket = serverSocket.accept();

                System.out.println("zaakceptowano");

while(!socket.isClosed()){
    sleep(25);

}
                System.out.println("zamkniete");
                // socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    public void send(ArrayList<String> message){
        System.out.println("wysyłam");
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("wysyłam");
        try {
            objectOutputStream.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectOutputStream.flush();
            objectOutputStream.close();
            socket.close();
            //objectOutputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




}
