package com.example.todo;

import java.time.LocalDate;

public class TodoList {
    private String shortDescription;
    private String longDescription;
    private LocalDate dueDate;


    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TodoList(String shortDescription, String longDescription, LocalDate dueDate) {
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.dueDate = dueDate;
    }



}
