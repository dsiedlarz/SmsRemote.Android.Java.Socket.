package com.example.dawid.smsremote;

import java.io.Serializable;

/**
 * Created by Dawid on 25.05.2016.
 */
public class Sms implements Serializable {
    String number,message,name;
    int type;

    public Sms(String number, String message, String name, int type) {
        this.number = number;
        this.message = message;
        this.name = name;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
