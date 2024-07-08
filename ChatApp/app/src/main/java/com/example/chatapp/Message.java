package com.example.chatapp;

public class Message {
    private String senderId;
    private String message;
    private long timestamp;
    private String profileImageUrl;

    public Message() {


    }

    public Message(String remitenteId, String mensaje, long tiempo, String perfilImagenUrl) {
        this.senderId = remitenteId;
        this.message = mensaje;
        this.timestamp = tiempo;
        this.profileImageUrl = perfilImagenUrl;
    }
    ///7Getters y setters


    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String perfilImagenUrl) {
        this.profileImageUrl = perfilImagenUrl;
    }

    public boolean esEntrante(String currentUserId) {
        return !senderId.equals(currentUserId);
    }
}
