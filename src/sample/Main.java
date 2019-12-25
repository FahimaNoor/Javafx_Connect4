package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.Button;


import java.io.IOException;


public class Main extends Application {

    public static void main(String[] args){
        launch(args);
    }

    Pane pane = new Pane();
    int[][] board  = new int[7][6];
    int player = 1;

    //insert
    public void update(){
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                Circle circle = new Circle((i * 100) + 50, (j * 100) + 50, 50, javafx.scene.paint.Color.WHITE);
                if(board[i][j] == 1 || board[i][j] == 2){
                    if (board[i][j] == 1){
                        circle.setFill(javafx.scene.paint.Color.RED);
                        InnerShadow is = new InnerShadow();
                        is.setOffsetX(2.0f);
                        is.setOffsetY(2.0f);
                        circle.setEffect(is);
                    }
                    if (board[i][j] == 2){
                        circle.setFill(javafx.scene.paint.Color.YELLOW);
                        InnerShadow is = new InnerShadow();
                        is.setOffsetX(2.0f);
                        is.setOffsetY(2.0f);
                        circle.setEffect(is);
                    }
                }
                pane.getChildren().add(circle);
            }
        }
    }

    public void updateWinner(int y1, int x1, int y2, int x2, int y3, int x3, int y4, int x4){

        Circle circle1 = new Circle((x1 * 100) + 50, (y1 * 100) + 50, 50, javafx.scene.paint.Color.WHITE);
          /*  FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.1), circle1);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.setCycleCount(Animation.INDEFINITE);
            fadeTransition.play();*/
        Circle circle2 = new Circle((x2 * 100) + 50, (y2 * 100) + 50, 50, javafx.scene.paint.Color.WHITE);
            /*FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(0.1), circle2);
            fadeTransition2.setFromValue(1.0);
            fadeTransition2.setToValue(0.0);
            fadeTransition2.setCycleCount(Animation.INDEFINITE);
            fadeTransition2.play();*/
        Circle circle3 = new Circle((x3 * 100) + 50, (y3 * 100) + 50, 50, javafx.scene.paint.Color.WHITE);
          /*  FadeTransition fadeTransition3 = new FadeTransition(Duration.seconds(0.1), circle3);
            fadeTransition3.setFromValue(1.0);
            fadeTransition3.setToValue(0.0);
            fadeTransition3.setCycleCount(Animation.INDEFINITE);
            fadeTransition3.play();*/
        Circle circle4 = new Circle((x4 * 100) + 50, (y4 * 100) + 50, 50, javafx.scene.paint.Color.WHITE);
          /*  FadeTransition fadeTransition4 = new FadeTransition(Duration.seconds(0.1), circle4);
            fadeTransition4.setFromValue(1.0);
            fadeTransition4.setToValue(0.0);
            fadeTransition4.setCycleCount(Animation.INDEFINITE);
            fadeTransition4.play();*/
        pane.getChildren().addAll(circle1, circle2, circle3, circle4);
    }

    public int bottom(int x){
        //finds next available spot on x axis
        //finds the empty place in the column
        for (int y = 5; y >= 0; y-- )
            if (board[x][y] == 0) return y;
        return -1;
    }

    public int looper(int y, int x){
        //loops back the positions that are off the board on either side of last clicked.
        if (y < 0 || x < 0 || y >= 6 || x >= 7)
            return 0;
        return board[x][y];
    }


    public int winner(){
        //row checker
        for(int x = 0; x < 7; x ++)
            for (int y = 0; y < 8; y++){
                if (looper(y, x) != 0 &&
                        looper(y,x) == looper(y,x+1)&&
                        looper(y,x) == looper(y,x+2)&&
                        looper(y,x) == looper(y,x+3)){
                    //updateWinner(y,x+1, y,x+2, y,x+3, y, x);
                    return looper(y,x);
                }}
        //column checker
        for(int x = 0; x < 7; x ++)
            for (int y = 0; y < 8; y++) {
                if (looper(y, x) != 0 &&
                        looper(y, x) == looper(y + 1, x) &&
                        looper(y, x) == looper(y + 2, x) &&
                        looper(y, x) == looper(y + 3, x)) {
                    //  updateWinner(y + 1, x, y + 3, x, y + 2, x, y, x);
                    return looper(y, x);
                }
            }
        //diagonal checker
        for(int x = 0; x < 7; x ++)
            for (int y = 0; y < 8; y++)
                for (int d = -1; d <= 1; d +=2){
                    if (looper(y, x) != 0 &&
                            looper(y, x) == looper(y + 1 * d, x + 1) &&
                            looper(y, x) == looper(y + 2 * d, x + 2) &&
                            looper(y, x) == looper(y + 3 * d, x + 3)) {
                        // updateWinner( y + 1 * d, x + 1 , y + 2 * d, x + 2, y + 3 * d, x + 3, y, x);
                        return looper(y, x);
                    }
                }
        for (int x = 0; x <7; x++)
            for (int y = 0;y < 6;y++)
                if (looper(y,x) == 0)
                    return 0;
        //tie checker
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
        pane.getChildren().add(rectangle);

        update();

        pane.setOnMouseClicked(e ->{
            // if (e.getButton() == MouseButton.PRIMARY) {
            int x = (int) (e.getX() / 100);
            int y = bottom(x);
            if(y >= 0){
                board[x][y] = player;
                if (player == 1) player = 2;
                else player = 1;
                // }

                update();
                if (winner() != 0 ) {
                    if (winner() == 3){
                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("DRAW.fxml"));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        Scene s=new Scene(root);
                        primaryStage.setScene(s);

                    }else{
                        if(winner()==1){
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(getClass().getResource("RED.fxml"));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            Scene s=new Scene(root);
                            primaryStage.setScene(s);
                        }
                        if(winner()==2){
                            Parent root = null;
                            try {
                                root = FXMLLoader.load(getClass().getResource("yellow.fxml"));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            Scene s=new Scene(root);
                            primaryStage.setScene(s);
                        }
                    }

                }
            }
        });

        Image img= new Image("6h2b338yzfo11.jpg");
        Pane first_layout= new Pane();
        Button play=new Button("PLAY");
        Button exit=new Button("EXIT");
        play.setOnAction(v ->{
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
        });
        exit.setOnAction(v ->{
            System.exit(0);
        });
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
        primaryStage.show();
    }

}
