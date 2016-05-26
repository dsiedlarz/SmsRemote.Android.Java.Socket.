package com.example.dawid.smsremote;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Dawid on 24.05.2016.
 */
public class Sender extends AsyncTask<Void, Void, Void> {
    String ip;

    public Sender(String ip) {
        this.ip = ip;
    }

    @Override
    protected Void doInBackground(Void... params) {


ArrayList<String> message;
        while(true) {
            try {
                //Log.v("tag2","otwieram socket");
                Socket socket = new Socket(ip, 4441);
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

                SmsManager smsManager = SmsManager.getDefault();
                while(true) {
                    Log.v("tag2","czekam");
                    message = (ArrayList<String>) objectInputStream.readObject();
                    Log.v("tag2","Przyszło");
                    smsManager.sendTextMessage(message.get(0) + "", null, message.get(1), null, null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


    }

}}
