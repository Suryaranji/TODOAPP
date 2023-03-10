package com.example.todo;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {
    @FXML
    private TextField Short;
    @FXML
    private TextArea Long;
    @FXML
    private DatePicker Duedate;


    @FXML
    public TodoList processResults() {
        String ShortDescription = Short.getText().trim();
        String LongDescription = Long.getText().trim();
        LocalDate date = Duedate.getValue();
        TodoList list = new TodoList(ShortDescription, LongDescription, date);
        //adding item to list
        TodoInstance.getTodoInstance().additem(list);
        return list;
    }
}
