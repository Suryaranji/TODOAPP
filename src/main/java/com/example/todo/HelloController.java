package com.example.todo;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


public class HelloController {
    @FXML

    private ListView<TodoList> smalldetails; //specify type of list it takes< Type>
    @FXML
    private TextArea largedetails;
    @FXML
    private Label duedate;
    private List<TodoList> items = new ArrayList<>();

    public void initialize() {
        TodoList list1 = new TodoList("Study Machine Learning",
                "Today Clustering Topic needed to be finished", LocalDate.of(2023, Month.MARCH, 5));
        TodoList list2 = new TodoList("Study JavaFX",
                "Today TOdo items Basics Topic needed to be finished", LocalDate.of(2023, Month.MARCH, 6));
        TodoList list3 = new TodoList("Study Dynamic programming",
                "Today sequences Topic needed to be finished", LocalDate.of(2023, Month.MARCH, 7));
        items.add(list1);
        items.add(list2);
        items.add(list3);
        //Populating List of items
        smalldetails.getItems().setAll(items);
        smalldetails.getSelectionModel().selectedItemProperty().addListener(

                (observableValue, t1, todoList) -> {
                    if(observableValue!=null)
                    {
                       TodoList list=smalldetails.getSelectionModel().getSelectedItem();
                        largedetails.setText(list.getLongDescription() );
                        duedate.setText(list.getDueDate().toString());

                    }
                }
        );
        smalldetails.getSelectionModel().selectFirst();
        //smalldetails.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); to select multiple options
    }


}
