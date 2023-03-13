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
        //Check For Empty places in adding dialog
        if(Short.getText().trim().isEmpty()||Long.getText().trim().isEmpty())
        {
            return null;
        }
        String ShortDescription = Short.getText().trim();
        String LongDescription = Long.getText().trim();
        LocalDate date = Duedate.getValue();
        TodoList list = new TodoList(ShortDescription, LongDescription, date);
        //adding item to list

        TodoInstance.getTodoInstance().additem(list);
        return list;
    }
@FXML
    public void editResults(TodoList list1) {
        Long.setText(list1.getLongDescription());
        Short.setText(list1.getShortDescription());
        Duedate.setValue(list1.getDueDate());


    }

    public void setResults(TodoList list1) {
        list1.setShortDescription(Short.getText());
        list1.setLongDescription(Long.getText());
        list1.setDueDate(Duedate.getValue());
        TodoInstance.getTodoInstance().additem(list1);
    }
}
