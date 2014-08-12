package com.roommateAPI.models;

import java.util.List;

public class Residence {

    public Long id;
    public String name;
    public List<User> residents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getResidents() {
        return residents;
    }

    public void setResidents(List<User> residents) {
        this.residents = residents;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
