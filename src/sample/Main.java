package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.control.Button;

import javax.swing.*;// imports the swing component

public class Main extends Application {  //Application contains all the core functionalities of javafx so this needs to be inherited



    public static void main(String[] args) {
        launch(args);  /* method inside the application class that allows us to set-up the application class */
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //start of main java fx code
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("TITLE HEADER");  //set's the title of the window
        Button button1 = new Button("Click ME!");  // button.setText("Click ME!");

        //The Layout1
        StackPane layout1= new StackPane();
        layout1.getChildren().add(button1);  //add button to the layout

        //Create Scene and set its size and layout
        Scene scene1=new Scene(layout1, 300, 250);
        primaryStage.setScene(scene1);
        /*  primaryStage.setScene(new Scene(root, 300, 250));
        Doest Work..does not show button
         set's the size of the window*/



        //Creating Scene_2 with VBox
        Label label2=new Label("Welcome to Scene 2 with VBox layout");
        Button button2=new Button("Go back to scene 1");
        VBox layout2= new VBox(20);  //this layout positions items line by line //you can also add the space you want to keep between each line in the parameter
        layout2.getChildren().addAll(label2,button2);
        Scene scene2 = new Scene(layout2); //Scene scene2 = new Scene(layout2,300,250);



        /*This class will handle button event*/
        /* button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });*/
        button1.setOnAction(e -> primaryStage.setScene(scene2));  //prints hi! on console  //will perfrom this action as many time as you press


        button2.setOnAction(e -> primaryStage.setScene(scene1));

        primaryStage.setScene(scene1);
        primaryStage.show(); //displays the window to the user



    }





}

