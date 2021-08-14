package com.example.quizapp;

public class Category {
    public static final int DataStructures_Algorithms = 1;
    public static final int DBMS = 2;
    public static final int OS = 3;

    private int id;
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString()
    {
        return  getName();
    }
}
