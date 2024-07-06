package com.example.chatapp;

public class User {
    private String id;
    private String name;
    private String profile;

    public User() {
        // Constructor vacíoo
    }

    public User(String id, String name, String profile) {
        this.id = id;
        this.name = name;
        this.profile = profile;
    }

    /////// Getters y setters
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

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
