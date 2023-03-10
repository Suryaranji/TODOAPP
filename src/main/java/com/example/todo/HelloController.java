package com.example.todo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class HelloController {
    @FXML
    private BorderPane id;
    @FXML

    private ListView<TodoList> smalldetails; //specify type of list it takes< Type>
    @FXML
    private TextArea largedetails;
    @FXML
    private Label duedate;
    private List<TodoList> items = new ArrayList<>();

    public void initialize()  {
/*
         TodoList list1 = new TodoList("Study Machine Learning",
                 "Today Clustering Topic needed to be finished", LocalDate.of(2023, Month.MARCH, 5));
         TodoList list2 = new TodoList("Study JavaFX",
                 "Today Todo items Basics Topic needed to be finished", LocalDate.of(2023, Month.MARCH, 6));
         TodoList list3 = new TodoList("Study Dynamic programming",
                 "Today sequences Topic needed to be finished", LocalDate.of(2023, Month.MARCH, 7));
         items.add(list1);
         items.add(list2);
         items.add(list3);
         TodoInstance.getTodoInstance().setItemslist(items);
 Populating List of items
*/
        items = TodoInstance.getTodoInstance().getItemslist();
        /*
            one way to populate listview
            smalldetails.getItems().setAll(items);
         */
        //By using observablelist the changes are reflected dynamically
        smalldetails.setItems((ObservableList<TodoList>) items);
        smalldetails.getSelectionModel().selectedItemProperty().addListener(

                (observableValue, t1, todoList) -> {
                    if (observableValue != null) {
                        TodoList list = smalldetails.getSelectionModel().getSelectedItem();
                        largedetails.setText(list.getLongDescription());
                        duedate.setText(list.getDueDate().toString());

                    }
                }
        );
        smalldetails.getSelectionModel().selectFirst();
        //smalldetails.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); to select multiple options
    }
   public void showNewDialog()
   {
       Dialog<ButtonType> dialog=new Dialog<>();

       dialog.initOwner(id.getScene().getWindow());//this method controls parent window not being used while it is open alerts user ---
       FXMLLoader root= new FXMLLoader(HelloApplication.class.getResource("Dialog.fxml"));
       try{
           //load dialog pane in current window

           //setting dialog pane content
           dialog.getDialogPane().setContent(root.load());

       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       //getButton types method returns observable arraylist of button types
       dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
       dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

       Optional<ButtonType> result=dialog.showAndWait();
       if(result.isPresent()&&result.get()==ButtonType.OK)
       {
           //getting controller access
         DialogController c=root.getController();//return controller associated with root object
          TodoList list=c.processResults();//add item to list and return instance os list
          smalldetails.getSelectionModel().select(list);//select the newly added item
       }


   }

}
