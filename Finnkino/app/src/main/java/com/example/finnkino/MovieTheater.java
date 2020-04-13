package com.example.finnkino;

public class MovieTheater {
    private Integer id;
    private String name;

    public MovieTheater(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
