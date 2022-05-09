package com.example.cardgameproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This program displays 5 cards selected at random from a com.example.cardgameforlong.Deck.
 * It depends on the files com.example.cardgameforlong.Deck.java, com.example.cardgameforlong.Card.java, and cards.png.
 * There is a button that the user can click to redraw the
 * image using new random cards.
 */
public class RandomCards extends Application {

    //TODO: Scoreboard system
    private long game_score;
    private boolean game_over;

    private Canvas canvas;  // The canvas on which the strings are drawn
    private TextField textInput;
    private ArrayList<String> types = new ArrayList<String>(){{
        add("odd");
        add("even");
        add("prime");
        add("composite");
        add("fibonacci");
    }};

    private Image cardImages;  // Contains images of all of the cards.

    public static void main(String[] args) {
        launch();
    }
    public static void display(int [] nums){
        //set of numbers to display cards

    }

    

    public void start( Stage stage ) {
        
        cardImages = new Image("cards.png");
        canvas = new Canvas(5*79 + 120, 123 + 40);
        draw();  // draw content of canvas the first time.
        guess();
        //comboBox with drop down menu
        ComboBox comboBox = new ComboBox();

        comboBox.getItems().add("odd");
        comboBox.getItems().add("even");
        comboBox.getItems().add("prime");
        comboBox.getItems().add("composite");
        comboBox.getItems().add("fibonacci");

        HBox hbox = new HBox(comboBox);


        //create button to submit guess
        StackPane hbGuess= new StackPane();
        Button submitBtn = new Button();
        submitBtn.setText("Submit Guess");
        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Submit button pressed.");
                submitBtn.setOnAction(e -> guess());


            }
        });
        hbGuess.getChildren().add(submitBtn);
        hbGuess.setAlignment(Pos.BOTTOM_LEFT);
       // StackPane hbGuess = new StackPane(hbGuess)
        hbGuess.setStyle("-fx-background-color: gray; -fx-padding:5px;" +
               " -fx-border-color:blue; -fx-border-width: 2px 0 0 0");

        //redraw button to show next set of numbers

        Button redraw = new Button("Next");
        redraw.setOnAction( e -> draw() );
        redraw.setAlignment(Pos.BOTTOM_RIGHT);
        HBox leftBox = new HBox(submitBtn);
        leftBox.setAlignment(Pos.BOTTOM_LEFT);
        HBox rightBox = new HBox(redraw);
        rightBox.setAlignment(Pos.BOTTOM_RIGHT);

        HBox bottom = new HBox(leftBox,rightBox);
        bottom.setSpacing(30.0);

        bottom.setStyle("-fx-background-color: gray; -fx-padding:5px;" +
                " -fx-border-color:blue; -fx-border-width: 2px 0 0 0");

        BorderPane root = new BorderPane(canvas);
      //  BorderPane root2 = new BorderPane(canvas);
        root.setBottom(bottom);
      //  root2.setBottom(hbGuess);
        root.setTop(hbox);
        root.setStyle("-fx-border-color:blue; -fx-border-width: 2px; -fx-background-color: lightblue");
        //input

        
        stage.setScene( new Scene(root, Color.BLACK) );
        stage.setTitle("Guess the set of Natural Numbers");
        stage.setResizable(false);
        stage.show();

    }
    

    /**
     * The draw() method is responsible for drawing the content of the canvas.
     * It draws 5 cards in a row.  The first card has top left corner at (20,20),
     * and there is a 20 pixel gap between each card and the next.
     */
    private void draw() {



        Deck deck = new Deck();
        deck.shuffle();
        

        // Checking if types is empty
        if (types.size() == 0) {
            // TODO: Display scoreboard and end the game.
            game_score = 0;
            game_over = false;



            try {
                Thread.sleep(10);
            }
            catch (Exception e){}
            System.exit(0);
        }

        // Shuffle types
        Collections.shuffle(types);

        // Get the first item from types
        String type = types.get(0);

        // Remove the item from types
        types.remove(0);


        if (type.equals("odd")) {
            int[] nums = new int[]{1,3,5,7,9};
            for (int i=0; i<nums.length; i++)
            {
                Card card = deck.dealCard(nums[i]);
                drawCard(deck, card, i);
            }
        }
        else if (type.equals("even")) {
            int[] nums = new int[]{2,4,6,8,10};
            for (int i=0; i<nums.length; i++)
            {
                Card card = deck.dealCard(nums[i]);
                drawCard(deck, card, i);
            }
        }
        else if (type.equals("prime"))
        {
            int[] nums = new int[]{2,3,5,7,11};
            for (int i=0; i<nums.length; i++)
            {
                Card card = deck.dealCard(nums[i]);
                drawCard(deck, card, i);
            }

        }
        else if (type.equals("composite"))
        {
            int[] nums = new int[]{4,6,8,10,12};
            for (int i=0; i<nums.length; i++)
            {
                Card card = deck.dealCard(nums[i]);
                drawCard(deck, card, i);
            }

        }
        else if (type.equals("fibonacci"))
        {
            int[] nums = new int[]{1,1,2,8,13};
            for (int i=0; i<nums.length; i++)
            {
                Card card = deck.dealCard(nums[i]);
                drawCard(deck, card, i);
            }

        }


        // Display input - this asks the user for the answer
       // displayInput();

        
    } // end draw()

    public void drawCard(Deck deck, Card card, int numCard) {
        GraphicsContext g = canvas.getGraphicsContext2D();

        double sx,sy;  // top left corner of source rect for card in cardImages
        double dx,dy;  // top left corner of destination rect for card in the canvas


        // Shuffle after getting a random card
        deck.shuffle();

        sx = 79 * (card.getValue()-1);
        sy = 123 * (3 - card.getSuit());
        dx = 20 + (79+20) * numCard;
        dy = 20;
        g.drawImage( cardImages, sx,sy,79,123, dx,dy,79,123 );
    }

    // TODO: Finish this implementation - Ask the user for the answer and check if its right.
    private void guess(){
        ComboBox comboBox = new ComboBox();

        comboBox.getItems().add("odd");
        comboBox.getItems().add("even");
        comboBox.getItems().add("prime");
        comboBox.getItems().add("composite");
        comboBox.getItems().add("fibonacci");
        if (types.equals("odd") && comboBox.getItems().equals("odd")){
            System.out.println("correctly guessed odd");
            System.out.println("update score");

        }
    }



}





    //updating scoreboard




//}  // end class com.example.cardgameforlong.RandomCards
