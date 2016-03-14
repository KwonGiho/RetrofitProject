package com.example.kwongiho.retrofitproject.model.dto;

import java.io.Serializable;

/**
 * Created by adminstrator on 2016-03-11.
 */
public class Contributor implements Serializable{
    private String login;
    private int id;

    public Contributor(int id, String login) {
        this.id = id;
        this.login = login;
    }

    public String getLogin() {

        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "login='" + login + '\'' +
                ", id=" + id;
    }
}
