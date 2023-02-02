package com.moutamid.sra_admin.models;

public class UserModel {
    String ID, username, email, password, whatsapp, referralCode, invitationCode;
    int vipLevel;
    double assets, earning, deposit, promotionValue;
    boolean isVIP, receivePrice;

    public UserModel() {
    }

    public UserModel(String ID, String username, String email, String password, String whatsapp, String invitationCode, double assets, double earning, double deposit, double promotionValue, boolean receivePrice) {
        this.ID = ID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.whatsapp = whatsapp;
        this.invitationCode = invitationCode;
        this.assets = assets;
        this.earning = earning;
        this.deposit = deposit;
        this.promotionValue = promotionValue;
        this.receivePrice = receivePrice;
    }

    public double getEarning() {
        return earning;
    }

    public void setEarning(double earning) {
        this.earning = earning;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getPromotionValue() {
        return promotionValue;
    }

    public void setPromotionValue(double promotionValue) {
        this.promotionValue = promotionValue;
    }

    public boolean isReceivePrice() {
        return receivePrice;
    }

    public void setReceivePrice(boolean receivePrice) {
        this.receivePrice = receivePrice;
    }

    public double getAssets() {
        return assets;
    }

    public void setAssets(double assets) {
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
