package com.example.dawid.smsremote;

/**
 * Created by Dawid on 13.05.2016.
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


import android.os.AsyncTask;
import android.widget.TextView;

public class Client extends AsyncTask<Void, Void, Void> {

    String dstAddress;
    int dstPort;
    String response = "";
    ArrayList<ArrayList<String>> messages;
    private PrintWriter printwriter;


    Client(String addr, int port, ArrayList<ArrayList<String>> messages) {
        dstAddress = addr;
        dstPort = port;
       this.messages = messages;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            socket = new Socket(dstAddress, dstPort);

          //  ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
          //  byte[] buffer = new byte[1024];


            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
           // printwriter = new PrintWriter(socket.getOutputStream(),true);

           // printwriter.write(text);  //write the message to output stream
           // printwriter.flush();
            objectOutputStream.writeObject(messages);
            //printwriter.print("asdasd");
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
       // textResponse.setText(response);
        super.onPostExecute(result);
    }

}