package com.example.todo;

import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class HelloController {
    @FXML
    private ToggleButton TodaysItem;
    @FXML
    private BorderPane id;
    @FXML

    private ListView<TodoList> smalldetails; //specify type of list it takes< Type>
    @FXML
    private TextArea largedetails;
    @FXML
    private Label duedate;
    private List<TodoList> items = new ArrayList<>();
    //ContextMenu
    @FXML
    private ContextMenu listmenuitem;
    private Predicate<TodoList> allItems;
    private Predicate<TodoList> todaysItemsOnly;
    private FilteredList<TodoList> filteredList;

    public void initialize() {
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
        //adding context menu item  delete
        listmenuitem = new ContextMenu();
        MenuItem deleteitem = new MenuItem("Delete");//item
        //event handler when that clicked
        deleteitem.setOnAction(actionEvent -> {
            deleteItem(smalldetails.getSelectionModel().getSelectedItem());//calls method
        });
        listmenuitem.getItems().addAll(deleteitem);

      //  items =TodoInstance.getTodoInstance().getItemslist() ;
        /*
            one way to populate listview
            smalldetails.getItems().setAll(items);
         */
        //By using observablelist the changes are reflected dynamically

            todaysItemsOnly=new Predicate<TodoList>() {
                @Override
                public boolean test(TodoList todoList) {
                    return todoList.getDueDate().equals(LocalDate.now());
                }
            };


            allItems=new Predicate<TodoList>() {
                @Override
                public boolean test(TodoList todoList) {
                    return true;
                }
            };
        filteredList=new FilteredList<>(TodoInstance.getTodoInstance().getItemslist(), allItems);

        SortedList<TodoList> sorted=new SortedList<>(filteredList, new Comparator<TodoList>() {
            @Override
            public int compare(TodoList o1, TodoList o2) {
                return o1.getDueDate().compareTo(o2.getDueDate());
            }
        });

       // smalldetails.setItems((ObservableList<TodoList>) items);
        smalldetails.setItems(sorted);


        smalldetails.getSelectionModel().selectedItemProperty().addListener(

                (observableValue, t1, todoList) -> {
                    if (todoList != null) {
                        TodoList list = smalldetails.getSelectionModel().getSelectedItem();
                        largedetails.setText(list.getLongDescription());
                        duedate.setText(list.getDueDate().toString());

                    }
                }
        );
        smalldetails.getSelectionModel().selectFirst();
        //Instead of overriding to string method to provide listview text an alternate way
        smalldetails.setCellFactory(new Callback<ListView<TodoList>, ListCell<TodoList>>() {
            @Override
            public ListCell<TodoList> call(ListView<TodoList> todoListListView) {
                ListCell<TodoList> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(TodoList todoList, boolean b) {
                        super.updateItem(todoList, b);
                        if (b) setText("");
                        else {
                            //updating listview cllname and text color
                            setText(todoList.getShortDescription());
                            if (todoList.getDueDate().equals(LocalDate.now())) {
                                setTextFill(Color.YELLOW);
                                duedate.setTextFill(Color.YELLOW);
                            }
                            if (todoList.getDueDate().isAfter(LocalDate.now())) {
                                setTextFill(Color.GREEN);
                                duedate.setTextFill(Color.GREEN);
                            }
                            if (todoList.getDueDate().isBefore(LocalDate.now())) {
                                setTextFill(Color.RED);
                                duedate.setTextFill(Color.RED);
                            }
                        }
                    }

                };
                //Associate contextmenu with each valid cells
                cell.emptyProperty().addListener(
                        (observable, empty, nowEmpty) ->
                        {
                            if (nowEmpty) cell.setContextMenu(null);
                            else cell.setContextMenu(listmenuitem);
                        }
                );
                return cell;
            }
        });
    }
    //smalldetails.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); to select multiple options


    private void deleteItem(TodoList selectedItem) {
        Alert message = new Alert(Alert.AlertType.CONFIRMATION);//displaing confirmation message to delete
        message.setTitle("Delete Item");//title for dialog
        message.setHeaderText("Delete " + selectedItem.getShortDescription());//hader text for alert box like purpose
        message.setContentText("Are you Sure to Delete ");//asking confirmation
        Optional<ButtonType> result = message.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            TodoInstance.getTodoInstance().removeItem(selectedItem);//call remove item
        }
    }
    public void showNewDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(id.getScene().getWindow());//this method controls parent window not being used while it is open alerts user ---
        //load dialog pane in current window
        FXMLLoader root = new FXMLLoader(HelloApplication.class.getResource("Dialog.fxml"));
        try {
            //setting dialog pane content
            dialog.getDialogPane().setContent(root.load());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //getButton types method returns observable arraylist of button types
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        //dialog.setHeaderText("Adding new item");//setting header text
        dialog.setTitle("Add new Item");

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //getting controller access
            DialogController c = root.getController();//return controller associated with root object
            TodoList list = c.processResults();//add item to list and return instance os list
            smalldetails.getSelectionModel().select(list);//select the newly added item
        }
    }
@FXML
    public void deleteItem(KeyEvent keyEvent) {
        TodoList c=smalldetails.getSelectionModel().getSelectedItem();
        if(keyEvent.getCode().equals(KeyCode.DELETE))
        {
            deleteItem(c);
        }
    }

    public void handleTodays(ActionEvent actionEvent) {
        TodoList list=smalldetails.getSelectionModel().getSelectedItem();
        if(TodaysItem.isSelected())
        {
            filteredList.setPredicate(todaysItemsOnly);
            if(filteredList.isEmpty())
            {
                largedetails.clear();
                duedate.setText("");
            }
            else if(filteredList.contains(list))
            {
                smalldetails.getSelectionModel().select(list);
            }
            else
            {
                smalldetails.getSelectionModel().selectFirst();
            }
        }
        if(!TodaysItem.isSelected())
        {
            filteredList.setPredicate(allItems);
            smalldetails.getSelectionModel().select(list);
        }
    }

    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }
}
