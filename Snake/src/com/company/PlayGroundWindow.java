package com.company;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class PlayGroundWindow {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ButtonStart;

    @FXML
    private RadioButton GreenSnake;

    @FXML
    private AnchorPane MenuPane;

    @FXML
    private ImageView SnakeBody;

    @FXML
    private ImageView SnakeHead;

    @FXML
    private Button again_button;

    @FXML
    private Button back_button;

    @FXML
    private AnchorPane gameover_pane;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField result_field;

    @FXML
    private ImageView SnakeTail;

    @FXML
    private ImageView apple;

    public int LastDirection=0;//0-right 1-left 2-up 3-down // НАПРАВЛЕНИЕ КОТОРОЕ БЫЛО В ПРОШЛОМ
    public int direction = 0;//0-right 1-left 2-up 3-down // НАПРАВЛЕНИЕ В НАСТОЯЩЕМ
    public int LastTickDirection=0;
    public static int speed = 10;
    double positionX=0;
    double positionY=0;
    double posBody_X=0;
    double posBody_Y=0;
    double pX=0;
    double pY=0;
    boolean clear_zone_apple=false;//если яблоко заспавнилось на змее;
    int res=0000;

    @FXML
    List<ImageView> snake_body;

    @FXML
    List<Image> old_snake_body;


    double tail_oldX=0;//координаты хвоста в прошлом тике для смещения при добавлении новой части тела
    double tail_oldY=0;

    @FXML
    ImageView old_body = new ImageView();

    @FXML
   public AnimationTimer at = new AnimationTimer() {
        long lastTick = 0;
        public void handle(long now) {

            if (lastTick == 0) {
                lastTick = now;
                try {
                    tick();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

            if (now - lastTick > 1000000000 / speed) {
                lastTick = now;
                try {
                    tick();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @FXML
    void initialize() {
        back_button.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage window = (Stage) back_button.getScene().getWindow();
                window.setResizable(false);
                Scene scene = new Scene(root);
                window.setScene(scene);
                scene.getRoot().requestFocus();//ОЧЕНЬ ВАЖНЫЙ МОМЕНТ АААААААААА
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        again_button.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayGroundWindow.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage window = (Stage) again_button.getScene().getWindow();
                window.setResizable(false);
                Scene scene = new Scene(root);
                window.setScene(scene);
                scene.getRoot().requestFocus();//ОЧЕНЬ ВАЖНЫЙ МОМЕНТ АААААААААА
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        snake_body = new ArrayList<>();
        snake_body.add(SnakeHead);
        snake_body.add(SnakeBody);
            //Случайная генерация яблока в начальной позиции
            Random random = new Random();
            apple.setLayoutX((random.nextInt((8 - 4) + 4))*64);
            apple.setLayoutY((random.nextInt((8 - 4) + 4))*64);
            at.start();
    }

    @FXML
    void Move(KeyEvent event) {//Смена направления движения
        switch (event.getCode()){
            case W :
                if(direction==3 || direction==2){
                    break;
                }
                LastDirection=direction;
                direction=2;
                break;
            case S:
                if(direction==2 ||  direction==3){
                    break;
                }
                LastDirection=direction;
                direction=3;
                break;
            case A:
                if(direction==0 || direction==1){
                    break;
                }
                LastDirection=direction;
                direction=1;
                break;
            case D:
                if(direction==1 || direction==0){
                    break;
                }
                LastDirection=direction;
                direction=0;
                break;
        }
    }

   public void tick() throws IOException {

        tail_oldX=SnakeTail.getLayoutX();
        tail_oldY=SnakeTail.getLayoutY();

        positionX=snake_body.get(0).getLayoutX();
        positionY=snake_body.get(0).getLayoutY();

        posBody_X=snake_body.get(snake_body.size()-1).getLayoutX();
        posBody_Y=snake_body.get(snake_body.size()-1).getLayoutY();


       //Повороты
       switch (direction){
           case 0:
               snake_body.get(0).setLayoutX(snake_body.get(0).getLayoutX()+64);
               Class<?> clazz = this.getClass();
               InputStream input = clazz.getResourceAsStream("img/GreenSnake/HeadRight.bmp");
               Image image = new Image(input);
               snake_body.get(0).setImage(image);
               if(LastDirection==2){
                   clazz = this.getClass();
                   input = clazz.getResourceAsStream("img/GreenSnake/BodyDR.bmp");
                   image = new Image(input);
                   snake_body.get(1).setImage(image);
               }
               if(LastDirection==3){
                   clazz = this.getClass();
                   input = clazz.getResourceAsStream("img/GreenSnake/BodyDL.bmp");
                   image = new Image(input);
                   snake_body.get(1).setImage(image);
               }
               if(LastTickDirection==direction){
                   clazz = this.getClass();
                   input = clazz.getResourceAsStream("img/GreenSnake/BodyLR.bmp");
                   image = new Image(input);
                   snake_body.get(1).setImage(image);
               }
               break;
           case 1:
               snake_body.get(0).setLayoutX(snake_body.get(0).getLayoutX()-64);
                clazz = this.getClass();
                input = clazz.getResourceAsStream("img/GreenSnake/HeadLeft.bmp");
                image = new Image(input);
               snake_body.get(0).setImage(image);
               if(LastDirection==2){
                   clazz = this.getClass();
                   input = clazz.getResourceAsStream("img/GreenSnake/BodyLD.bmp");
                   image = new Image(input);
                   snake_body.get(1).setImage(image);
               }
               if(LastDirection==3){
                   clazz = this.getClass();
                   input = clazz.getResourceAsStream("img/GreenSnake/BodyRD.bmp");
                   image = new Image(input);
                   snake_body.get(1).setImage(image);
               }
               if(LastTickDirection==direction){
                   clazz = this.getClass();
                   input = clazz.getResourceAsStream("img/GreenSnake/BodyLR.bmp");
                   image = new Image(input);
                   snake_body.get(1).setImage(image);
               }
               break;
           case 2:
               snake_body.get(0).setLayoutY(snake_body.get(0).getLayoutY()-64);
                clazz = this.getClass();
                input = clazz.getResourceAsStream("img/GreenSnake/HeadUp.bmp");
                image = new Image(input);
               snake_body.get(0).setImage(image);
               if(LastDirection==0){
                   clazz = this.getClass();
                   input = clazz.getResourceAsStream("img/GreenSnake/BodyRD.bmp");
                   image = new Image(input);
                   snake_body.get(1).setImage(image);
               }
               if(LastDirection==1){
                   clazz = this.getClass();
                   input = clazz.getResourceAsStream("img/GreenSnake/BodyDL.bmp");
                   image = new Image(input);
                   snake_body.get(1).setImage(image);
               }
               if(LastTickDirection==direction){
                   clazz = this.getClass();
                   input = clazz.getResourceAsStream("img/GreenSnake/BodyTD.bmp");
                   image = new Image(input);
                   snake_body.get(1).setImage(image);
               }
               break;
           case 3:
               snake_body.get(0).setLayoutY(snake_body.get(0).getLayoutY()+64);
               clazz = this.getClass();
               input = clazz.getResourceAsStream("img/GreenSnake/HeadDown.bmp");
               image = new Image(input);
               snake_body.get(0).setImage(image);
               if(LastDirection==0){
                   clazz = this.getClass();
                   input = clazz.getResourceAsStream("img/GreenSnake/BodyLD.bmp");
                   image = new Image(input);
                   snake_body.get(1).setImage(image);
               }
               if(LastDirection==1){
                   clazz = this.getClass();
                   input = clazz.getResourceAsStream("img/GreenSnake/BodyDR.bmp");
                   image = new Image(input);
                   snake_body.get(1).setImage(image);
               }
               if(LastTickDirection==direction){
                   clazz = this.getClass();
                   input = clazz.getResourceAsStream("img/GreenSnake/BodyTD.bmp");
                   image = new Image(input);
                   snake_body.get(1).setImage(image);
               }
               break;
       }

       //Движение друг за другом
       for(int i =1;i<snake_body.size();i++) {
           pX=snake_body.get(i).getLayoutX();
           pY=snake_body.get(i).getLayoutY();
           snake_body.get(i).setLayoutX(positionX);
           snake_body.get(i).setLayoutY(positionY);
           positionX=pX;
           positionY=pY;
       }


        if(snake_body.size()>2){
            for (int i=2;i<snake_body.size();i++){
                snake_body.get(i).setImage(old_snake_body.get(i-1));
            }
        }


        SnakeTail.setLayoutY(posBody_Y);
        SnakeTail.setLayoutX(posBody_X);


        //Смена текстурки хвоста в зависимости от положения последней части тела
       if (snake_body.get(snake_body.size()-1).getLayoutX() == SnakeTail.getLayoutX()+64){
            Class<?> clazz = this.getClass();
            InputStream input = clazz.getResourceAsStream("img/GreenSnake/TailRight.bmp");
            Image image = new Image(input);
            SnakeTail.setImage(image);
        }
       if (snake_body.get(snake_body.size()-1).getLayoutX() == SnakeTail.getLayoutX()-64){
           Class<?> clazz = this.getClass();
           InputStream input = clazz.getResourceAsStream("img/GreenSnake/TailLeft.bmp");
           Image image = new Image(input);
           SnakeTail.setImage(image);
       }
       if (snake_body.get(snake_body.size()-1).getLayoutY() == SnakeTail.getLayoutY()+64){
           Class<?> clazz = this.getClass();
           InputStream input = clazz.getResourceAsStream("img/GreenSnake/TailDown.bmp");
           Image image = new Image(input);
           SnakeTail.setImage(image);
       }
       if (snake_body.get(snake_body.size()-1).getLayoutY() == SnakeTail.getLayoutY()-64){
           Class<?> clazz = this.getClass();
           InputStream input = clazz.getResourceAsStream("img/GreenSnake/TailUp.bmp");
           Image image = new Image(input);
           SnakeTail.setImage(image);
       }




        if(apple.getLayoutX()==snake_body.get(0).getLayoutX() && apple.getLayoutY()==snake_body.get(0).getLayoutY())  // Момент поедания змейкой яблока
        {
            Random random = new Random();

            apple.setLayoutX((random.nextInt((8 - 4) + 4)) * 64);
            apple.setLayoutY((random.nextInt((8 - 4) + 4)) * 64);

            int n=0;
            while (!clear_zone_apple) {
                n=snake_body.size();
                for (int i = 1; i < snake_body.size(); i++) {
                    if (apple.getLayoutX() == snake_body.get(i).getLayoutX() && apple.getLayoutY() == snake_body.get(i).getLayoutY()) {
                        apple.setLayoutX((random.nextInt((8 - 4) + 4)) * 64);
                        apple.setLayoutY((random.nextInt((8 - 4) + 4)) * 64);
                    }
                    else{
                        n-=1;
                    }
                    if(n==1){
                        clear_zone_apple=true;
                    }
                }

            }
            res+=50;
            result_field.setText("Результат :"+res);
            ImageView IV = new ImageView();
            IV.setBlendMode(BlendMode.DARKEN);
            IV.setImage(old_body.getImage());
            IV.setLayoutX(SnakeTail.getLayoutX());
            IV.setLayoutY(SnakeTail.getLayoutY());
            IV.setVisible(true);
            snake_body.add(IV);
            pane.getChildren().add(snake_body.get(snake_body.size()-1));
            SnakeTail.setLayoutX(tail_oldX);
            SnakeTail.setLayoutY(tail_oldY);
            if (snake_body.get(snake_body.size()-1).getLayoutX() == SnakeTail.getLayoutX()+64){
                Class<?> clazz = this.getClass();
                InputStream input = clazz.getResourceAsStream("img/GreenSnake/TailRight.bmp");
                Image image = new Image(input);
                SnakeTail.setImage(image);
            }
            if (snake_body.get(snake_body.size()-1).getLayoutX() == SnakeTail.getLayoutX()-64){
                Class<?> clazz = this.getClass();
                InputStream input = clazz.getResourceAsStream("img/GreenSnake/TailLeft.bmp");
                Image image = new Image(input);
                SnakeTail.setImage(image);
            }
            if (snake_body.get(snake_body.size()-1).getLayoutY() == SnakeTail.getLayoutY()+64){
                Class<?> clazz = this.getClass();
                InputStream input = clazz.getResourceAsStream("img/GreenSnake/TailDown.bmp");
                Image image = new Image(input);
                SnakeTail.setImage(image);
            }
            if (snake_body.get(snake_body.size()-1).getLayoutY() == SnakeTail.getLayoutY()-64){
                Class<?> clazz = this.getClass();
                InputStream input = clazz.getResourceAsStream("img/GreenSnake/TailUp.bmp");
                Image image = new Image(input);
                SnakeTail.setImage(image);
            }
        }

        for (int i=1;i<snake_body.size();i++){
            if(snake_body.get(0).getLayoutX()==snake_body.get(i).getLayoutX() && snake_body.get(0).getLayoutY()==snake_body.get(i).getLayoutY()){
                at.stop();
                gameover_pane.setVisible(true);
            }
        }

       if (SnakeHead.getLayoutX() > 704 || SnakeHead.getLayoutX() < 0 || SnakeHead.getLayoutY() > 512 ||SnakeHead.getLayoutY() < 0) // Game Over
       {
           at.stop();
           gameover_pane.setVisible(true);
       }

       LastTickDirection=direction;
       old_body.setImage(snake_body.get(snake_body.size()-1).getImage());
       old_snake_body = new ArrayList<>();
       for (int i=0;i<snake_body.size();i++){
           old_snake_body.add(snake_body.get(i).getImage());
       }
   }

}

