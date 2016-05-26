package com.example.dawid.smsremote;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Dawid on 26.05.2016.
 */
public class Viewer  extends AsyncTask<Void, Void, Void> {
    MainActivity app;
String ip;
   Switch last;

    public Viewer(MainActivity app, String ip,Switch last) {
        this.app = app;
        this.ip = ip;
        this.last = last;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.v("tag2","--------------------doInBackground-------------------------");

        while(true) {
            Log.v("tag2","---------------whilez------------------------------");
            Cursor cursor = app.getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);


            ArrayList<ArrayList<String>> messages = new ArrayList<ArrayList<String>>();
            int i =0;

            if (cursor.moveToFirst()) { // must check the result to prevent exception
                do {


                    if ((cursor.getString(cursor.getColumnIndex("type")).compareToIgnoreCase("1") == 0)) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add(cursor.getString(cursor.getColumnIndex("address")));
                        tmp.add(cursor.getString(cursor.getColumnIndex("body")));
                        tmp.add("");
                        tmp.add(1 + "");
                        messages.add(tmp);

                    }
                    if ((cursor.getString(cursor.getColumnIndex("type")).compareToIgnoreCase("2") == 0)) {
                        ArrayList<String> tmp = new ArrayList<>();
                        tmp.add(cursor.getString(cursor.getColumnIndex("address")));
                        tmp.add(cursor.getString(cursor.getColumnIndex("body")));
                        tmp.add("");
                        tmp.add(2 + "");
                        messages.add(tmp);
                    }
                    i++;
                    if((i>20))break;

                    // use msgData
                } while (cursor.moveToNext());
            } else {
                // empty box, no SMS
            }
Log.v("tag2","wysyłam");


            try {
                Log.v("tag2","---------------------------------------------");
                Socket socket = new Socket(ip,4449);
                Log.v("tag2","-------+++++++++++--------------------------------------");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(messages);
                objectOutputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //app.send1(ip, messages);

cursor.close();

        }





    }
}
