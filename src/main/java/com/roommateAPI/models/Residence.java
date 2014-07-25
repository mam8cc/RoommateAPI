package com.roommateAPI.models;

import java.util.List;

public class Residence {

    public Long id;
    public String name;
    public List<UserModel> residents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserModel> getResidents() {
        return residents;
    }

    public void setResidents(List<UserModel> residents) {
        this.residents = residents;
    }
}
