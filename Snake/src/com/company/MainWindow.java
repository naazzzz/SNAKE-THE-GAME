package com.company;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonExit;

    @FXML
    private Button buttonStart;

    @FXML
    private RadioButton speed_1;

    @FXML
    private RadioButton speed_2;

    @FXML
    private RadioButton speed_3;

    @FXML
    private AnchorPane pane;

    @FXML
     void initialize() {

        speed_1.setOnMouseClicked(mouseEvent -> {
            speed_1.setSelected(true);
            speed_2.setSelected(false);
            speed_3.setSelected(false);
        });
        speed_2.setOnMouseClicked(mouseEvent -> {
            speed_2.setSelected(true);
            speed_1.setSelected(false);
            speed_3.setSelected(false);
        });
        speed_3.setOnMouseClicked(mouseEvent -> {
            speed_3.setSelected(true);
            speed_2.setSelected(false);
            speed_1.setSelected(false);
        });


        buttonExit.setOnAction(event ->{
            Stage stage = (Stage) buttonExit.getScene().getWindow();
            stage.close();
        } );
        buttonStart.setOnAction(event ->{
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayGroundWindow.fxml"));
                Parent root = (Parent) fxmlLoader.load();

                PlayGroundWindow pgw = fxmlLoader.getController();

                if(speed_1.isSelected()) {
                    pgw.speed =5;
                }
                if(speed_2.isSelected()){
                    pgw.speed =10;
                }
                if(speed_3.isSelected()) {
                    pgw.speed =15;
                }
                Stage window = (Stage) buttonStart.getScene().getWindow();
                window.setResizable(false);
                Scene scene = new Scene(root);
                window.setScene(scene);

                scene.getRoot().requestFocus();//ОЧЕНЬ ВАЖНЫЙ МОМЕНТ АААААААААА
            } catch (IOException e) {
                e.printStackTrace();
            }
        } );

    }
}