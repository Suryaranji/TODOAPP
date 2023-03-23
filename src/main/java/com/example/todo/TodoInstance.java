package com.example.todo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TodoInstance {
    private static final TodoInstance todoInstance = new TodoInstance();
    private ObservableList<TodoList> itemslist;
    private DateTimeFormatter format;


    public TodoInstance() {
        format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public static TodoInstance getTodoInstance() {
        return todoInstance;
    }

    public ObservableList<TodoList> getItemslist() {
        return itemslist;
    }

    public void loaditems() {
        itemslist = FXCollections.observableArrayList();
        Path p = Paths.get("D:\\JAVA\\TODO\\items.txt");
        try (BufferedReader reader = Files.newBufferedReader(p)) {
            String s;
            while ((s = reader.readLine()) != null) {
                String[] arr = s.split("\t");
                TodoList list = new TodoList(arr[0], arr[1], LocalDate.parse(arr[2], format));
                itemslist.add(list);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void storeItems() {
        try (

                FileWriter writer = new FileWriter("items.txt")) {

            for (TodoList item : itemslist) {

                writer.write(String.format("%s\t%s\t%s", item.getShortDescription(), item.getLongDescription(), item.getDueDate().format(format)));
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void additem(TodoList list)
    {
        this.itemslist.add(list);
    }
    public void removeItem(TodoList list)
    {
        itemslist.remove(list);
    }


}


