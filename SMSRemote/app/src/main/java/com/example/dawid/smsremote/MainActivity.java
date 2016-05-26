package com.example.dawid.smsremote;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Socket client;
    private PrintWriter printwriter;
    private EditText ipAddress;
    Switch last;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipAddress = (EditText) findViewById(R.id.ipAddress);
        last = (Switch) findViewById(R.id.switch1);



    }


    public void tralalala(View view) {

        Log.v("tag1", "tralalala2");
String ip =ipAddress.getText().toString();
       // sms(ip);

        ArrayList<ArrayList<String>> messages = new ArrayList<>();
        messages.add(new ArrayList<String>());



        Viewer viewer = new Viewer(this,ip,last);
        //Log.v("tag1", "tralalala5");
        viewer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
       // Client client = new Client(ip,4444,messages);
       // run(ip);
        Sender sender= new Sender(ip);
        Log.v("tag1", "tralalala3");
        sender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);




    }


public void send1(String ip, ArrayList<ArrayList<String>> messages){
    Log.v("tag2","send1"+ip+messages);
    Client myClient = new Client(ip, 4444, messages);
    myClient.execute();
    Log.v("tag2","send1"+ip+messages);
}

    public void sms(String ip) {
        SmsManager smsManager = SmsManager.getDefault();
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);

        StringBuilder stringBuilder = new StringBuilder();

        ArrayList<ArrayList<String>> messages = new ArrayList<ArrayList<String>>();

        TextView textView = (TextView) findViewById(R.id.text);

        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                String msgData = "";

//                if ((cursor.getString(cursor.getColumnIndex("type")).compareToIgnoreCase("1") == 0))
//                    stringBuilder.append((cursor.getString(cursor.getColumnIndex("address")).compareToIgnoreCase("+48509764485") == 0 ? "Olenka" : cursor.getString(cursor.getColumnIndex("address"))) + ":\n"
//                            + cursor.getString(cursor.getColumnIndex("body")) + "\n\n");
//                if ((cursor.getString(cursor.getColumnIndex("type")).compareToIgnoreCase("2") == 0))
//                    stringBuilder.append("Ja do --->" + (cursor.getString(cursor.getColumnIndex("address")).compareToIgnoreCase("+48509764485") == 0 ? "Olenka" : cursor.getString(cursor.getColumnIndex("address"))) + ":\n"
//                            + cursor.getString(cursor.getColumnIndex("body")) + "\n\n");
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



                // use msgData
            } while (cursor.moveToNext());
        } else {
            // empty box, no SMS
        }

        Client myClient = new Client(ip, 4449, messages);
        myClient.execute();


    }








    public void run(String ip) {
        while(true) {
            Cursor cursor = getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);


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
            send1(ip, messages);

            cursor.close();


        }





    }



}
