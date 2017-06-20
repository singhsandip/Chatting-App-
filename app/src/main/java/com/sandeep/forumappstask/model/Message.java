package com.sandeep.forumappstask.model;

/**
 * Created by sandeep on 17-06-2017.
 */

public class Message
{
    private int id;
    private int type;
    private String email;
    private String senderid;
    private String receiverid;
    private String message;
    private String time;

    public Message(int id, int type, String email, String senderid, String receiverid, String message, String time) {
        this.id = id;
        this.type = type;
        this.email = email;
        this.senderid = senderid;
        this.receiverid = receiverid;
        this.message = message;
        this.time = time;
    }

    public Message(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
