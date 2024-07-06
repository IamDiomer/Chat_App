package com.example.chatapp;

import java.util.Map;

public class Group {
    private String id;
    private String name;
    private Map<String, Boolean> members;

    // Constructor vacio
    public Group() {
    }

    ///// Constructor con todos losparametros del grupo
    public Group(String id, String name, Map<String, Boolean> members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }

    ///Constructor nombre y miembros del grupo
    public Group(String name, Map<String, Boolean> members) {
        this.name = name;
        this.members = members;
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

    public Map<String, Boolean> getMembers() {
        return members;
    }

    public void setMembers(Map<String, Boolean> members) {
        this.members = members;
    }
}
