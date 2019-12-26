package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.text.Font;
//needed for the image in the first_scene
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import javafx.scene.control.Button;

public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    Pane game_layout = new Pane();
    int[][] board  = new int[7][6];
    int player = 1;  //player=1 is red and player=2 is yellow

    public void CircleChange(){
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                Circle circle = new Circle((i * 100) + 50, (j * 100) + 50, 50, Color.WHITE);
                if(board[i][j] == 1 || board[i][j] == 2){
                    if (board[i][j] == 1){
                        circle.setFill(Color.RED);
                        InnerShadow is = new InnerShadow();
                        is.setOffsetX(2.0f);
                        is.setOffsetY(2.0f);
                        circle.setEffect(is);
                    }
                    if (board[i][j] == 2){
                        circle.setFill(Color.YELLOW);
                        InnerShadow is = new InnerShadow();
                        is.setOffsetX(2.0f);
                        is.setOffsetY(2.0f);
                        circle.setEffect(is);
                    }
                }
                game_layout.getChildren().add(circle);
            }
        }
    }

    public int EmptyRow(int x){
        //finds the empty place in the column from the x.
        for (int y = 5; y >= 0; y-- )
            if (board[x][y] == 0) return y;
        return -1;
    }

    public int BoardIndexValue(int y, int x){  //returns whether at that index there is 1 , 2 or 0
        if (y < 0 || x < 0 || y >= 6 || x >= 7)
            return 0;
        return board[x][y];
    }

    public int Winner() {
        //this loop will check if there is a match in the row
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 8; y++) {
                if (BoardIndexValue(y, x) != 0 &&
                        BoardIndexValue(y, x) == BoardIndexValue(y, x + 1) &&
                        BoardIndexValue(y, x) == BoardIndexValue(y, x + 2) &&
                        BoardIndexValue(y, x) ==BoardIndexValue(y, x + 3)) {
                    return BoardIndexValue(y, x);
                }
            }
        }


        //this loop will check if there is a match in the column
        for (int x = 0; x < 7; x++){
            for (int y = 0; y < 8; y++) {
                if (BoardIndexValue(y, x) != 0 &&
                        BoardIndexValue(y, x) == BoardIndexValue(y + 1, x) &&
                        BoardIndexValue(y, x) == BoardIndexValue(y + 2, x) &&
                        BoardIndexValue(y, x) == BoardIndexValue(y + 3, x)) {

                    return BoardIndexValue(y, x);
                }
            }
        }

        //this loop will check if there is a match in the diagonal
        for(int x = 0; x < 7; x ++) {
            for (int y = 0; y < 8; y++) {
                for (int d = -1; d <= 1; d += 2) {
                    if (BoardIndexValue(y, x) != 0 &&
                            BoardIndexValue(y, x) == BoardIndexValue(y + 1 * d, x + 1) &&
                            BoardIndexValue(y, x) == BoardIndexValue(y + 2 * d, x + 2) &&
                            BoardIndexValue(y, x) ==BoardIndexValue(y + 3 * d, x + 3)) {
                        return BoardIndexValue(y, x);
                    }
                }
            }
        }


        for (int x = 0; x <7; x++) {  //As there is still empty disc the game will continue
            for (int y = 0; y < 6; y++) {
                if (BoardIndexValue(y, x) == 0)
                    return 0;
            }
        }
        //Since there is no empty disc yet ther is no winner then the game is draw
        return 3;
    }

    @Override
    public void start(Stage primaryStage){
        Rectangle rectangle = new Rectangle(0,0,700,600);
        Light.Distant light = new Light.Distant();
        light.setAzimuth(45.0);
        light.setElevation(30.0);
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);
        rectangle.setEffect(lighting);
        rectangle.setFill(Color.BLUE);
        game_layout.getChildren().add(rectangle);

        CircleChange();//all the white circles are now added to the layout

        game_layout.setOnMouseClicked(e ->{

            int x = (int) (e.getX() / 100);
            int y =EmptyRow(x);
            if(y >= 0){
                board[x][y] = player;
                if (player == 1) player = 2;
                else player = 1;

                CircleChange();


                if(Winner()==1){
                    Parent red = null;
                    try {
                        red = FXMLLoader.load(getClass().getResource("RED.fxml"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    Scene s=new Scene(red);
                    primaryStage.setScene(s); //goes to red scene
                }
                if(Winner()==2){
                    Parent yellow = null;
                    try {
                        yellow = FXMLLoader.load(getClass().getResource("yellow.fxml"));

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    Scene s=new Scene(yellow);
                    primaryStage.setScene(s); //goes to yellow scene
                }
                if (Winner() == 3){
                    Parent draw = null;
                    try {
                        draw = FXMLLoader.load(getClass().getResource("DrawFinal.fxml"));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    Scene s=new Scene(draw);
                    primaryStage.setScene(s); //goes to draw scene

                }


            }
        });
        //Creating the entrance Scene
        Image img= new Image("6h2b338yzfo11.jpg");
        Pane first_layout= new Pane();
        Button play=new Button("PLAY");
        Button exit=new Button("EXIT");

        play.setOnAction(v ->{
            Scene scene = new Scene(game_layout);
            primaryStage.setScene(scene); //this will transition to the board scene
        });

        exit.setOnAction(v -> System.exit(0));

        play.setLayoutX(143);
        play.setLayoutY(315);
        play.setPrefSize(100,50);

        exit.setLayoutX(479);
        exit.setLayoutY(315);
        exit.setPrefSize(100,50);

        Label label=new Label();
        label.setText("ConnectFourFX");
        label.setLayoutX(138);
        label.setLayoutY(115);
        label.setPrefSize(411,62);
        label.setFont(new Font("Candara", 63));
        first_layout.getChildren().addAll(new ImageView(img),label,play,exit);

        Scene first_scene = new Scene(first_layout,700,600);
        primaryStage.setScene(first_scene);
        primaryStage.show();//aplication will start from first scene
    }
}
