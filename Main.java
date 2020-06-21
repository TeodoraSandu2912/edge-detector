package com.bham.pij.assignments.edgedetector;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    VBox root;
    File file;
    ImageView imgView;
    Image image;
    MenuItem closeItem;
    MenuItem edgeDetectorItem;
    MenuItem revertItem;
    MenuItem openItem;


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Main");
        root = new VBox();


        Menu fileMenu = new Menu("File");
        openItem = new MenuItem("Open");
        openItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Image File");
                file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    loadImageFile(file);
                }
                openItem.setDisable(true);
                revertItem.setDisable(true);

            }
        });


        closeItem = new MenuItem("Close");
        closeItem.setDisable(true);
        closeItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent c) {

                root.getChildren().remove(imgView);
                closeItem.setDisable(true);
                edgeDetectorItem.setDisable(true);
                revertItem.setDisable(true);
                openItem.setDisable(false);

            }
        });



        fileMenu.getItems().addAll(openItem, closeItem);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);


        Menu toolsMenu = new Menu("Tools");
        edgeDetectorItem = new MenuItem("Edge Detection");
        edgeDetectorItem.setDisable(true);

        edgeDetectorItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {

                EdgeDetector edge = new EdgeDetector();
                Image fin = edge.filterImage(image);
                root.getChildren().remove(imgView);
                imgView = new ImageView(fin);
                root.getChildren().add(imgView);

                edgeDetectorItem.setDisable(true);
                revertItem.setDisable(false);

            }

        });



        revertItem = new Menu("Revert");
        revertItem.setDisable(true);
        revertItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent r) {
                root.getChildren().remove(imgView);
                imgView = new ImageView(image);
                root.getChildren().add(imgView);
                edgeDetectorItem.setDisable(false);
                revertItem.setDisable(true);

            }
        });

        toolsMenu.getItems().addAll(edgeDetectorItem, revertItem);
        menuBar.getMenus().add(toolsMenu);


        root.getChildren().add(menuBar);



        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }


    private void loadImageFile (File file) {

        image = new Image("file:" + file.getPath());
        imgView = new ImageView(image);
        root.getChildren().add(imgView);
        closeItem.setDisable(false);
        edgeDetectorItem.setDisable(false);
        revertItem.setDisable(false);

    }





    public static void main(String[] args) {
        launch(args);
    }
}

