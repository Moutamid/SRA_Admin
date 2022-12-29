package com.moutamid.sra_admin.models;

public class UserModel {
    String ID, username, email, password, whatsapp, referralCode, invitationCode;
    int vipLevel, assets;
    boolean isVIP, receivePrice;

    public UserModel() {
    }

    public UserModel(String ID, String username, String email, String password, String whatsapp, String referralCode, String invitationCode, int assets, boolean receivePrice) {
        this.ID = ID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.whatsapp = whatsapp;
        this.referralCode = referralCode;
        this.invitationCode = invitationCode;
        this.assets = assets;
        this.receivePrice = receivePrice;
    }

    public UserModel(String ID, String username, String email, String password, String whatsapp, String referralCode, String invitationCode, int vipLevel, int assets, boolean isVIP, boolean receivePrice) {
        this.ID = ID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.whatsapp = whatsapp;
        this.referralCode = referralCode;
        this.invitationCode = invitationCode;
        this.vipLevel = vipLevel;
        this.assets = assets;
        this.isVIP = isVIP;
        this.receivePrice = receivePrice;
    }

    public boolean isReceivePrice() {
        return receivePrice;
    }

    public void setReceivePrice(boolean receivePrice) {
        this.receivePrice = receivePrice;
    }

    public int getAssets() {
        return assets;
    }

    public void setAssets(int assets) {
        this.assets = assets;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public boolean isVIP() {
        return isVIP;
    }

    public void setVIP(boolean VIP) {
        isVIP = VIP;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
