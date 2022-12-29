package com.moutamid.sra_admin.models;

import java.io.Serializable;

public class RequestModel implements Serializable {
    String ID, image, userID, hashKey;
    int amount;
    long timestamps;
    String status, type;

    public RequestModel() {
    }

    public RequestModel(String ID, String image, String userID, String hashKey, int amount, long timestamps, String status, String type) {
        this.ID = ID;
        this.image = image;
        this.userID = userID;
        this.hashKey = hashKey;
        this.amount = amount;
        this.timestamps = timestamps;
        this.status = status;
        this.type = type;
    }

    public RequestModel(String ID, String userID, String hashKey, String status, String type, int amount, long timestamps) {
        this.ID = ID;
        this.userID = userID;
        this.hashKey = hashKey;
        this.amount = amount;
        this.timestamps = timestamps;
        this.status = status;
        this.type = type;
    }

    public RequestModel(String ID, String image, String userID, int amount, long timestamps, String status, String type) {
        this.ID = ID;
        this.image = image;
        this.userID = userID;
        this.amount = amount;
        this.timestamps = timestamps;
        this.status = status;
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(long timestamps) {
        this.timestamps = timestamps;
    }
}
