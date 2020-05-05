package com.openlol.api.model;

public class EncryptedTransaction {

    String userId;
    //AES encrypted transaction information
    String payload;
    //RSA encypted AES key
    String encAesKey;

    //getters and setters...


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getEncAesKey() {
        return encAesKey;
    }

    public void setEncAesKey(String encAesKey) {
        this.encAesKey = encAesKey;
    }
}
