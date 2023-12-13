package com.example.btl_2023;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DictionaryApplication extends Application {

    private DictionaryManagement dictionaryManagement;
    private TabPane tabPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Dictionary dictionary = new Dictionary();
        dictionaryManagement = new DictionaryManagement(dictionary);

        tabPane = new TabPane();

        Scene scene = new Scene(tabPane, 800, 600);

        primaryStage.setTitle("Dictionary Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        showMainMenuTab();
    }

    private void showMainMenuTab() {
        Tab mainMenuTab = new Tab("Main Menu");
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));

        Label label = new Label("Welcome to Dictionary Application!");
        vbox.getChildren().add(label);

        mainMenuTab.setContent(vbox);
        tabPane.getTabs().add(mainMenuTab);
    }


}
