package com.moutamid.sra_admin.models;

public class Tasks {
    String id ="0";
    String uid;
    String name;
    int amount;
    float income;
    boolean isLock;
    int total;
    String userID;
    long timestamps;
    String status;
    int image;
    String type;

    public Tasks() {
    }

    public Tasks(String id, String uid, String name, int amount, float income, boolean isLock, int total, String userID, long timestamps, String status, int image, String type) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.amount = amount;
        this.income = income;
        this.isLock = isLock;
        this.total = total;
        this.userID = userID;
        this.timestamps = timestamps;
        this.status = status;
        this.image = image;
        this.type = type;
    }

    public Tasks(String id, String uid, String name, int amount, float income, boolean isLock, int total, String userID, long timestamps, String status, String type) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.amount = amount;
        this.income = income;
        this.isLock = isLock;
        this.total = total;
        this.userID = userID;
        this.timestamps = timestamps;
        this.status = status;
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(long timestamps) {
        this.timestamps = timestamps;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
