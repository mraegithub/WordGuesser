import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
public class ClientGame extends Application{
  Button clientStart;
  Button guess = new Button("GUESS");
  Button playAgain = new Button("Play Again");
  Button quit = new Button("Quit");
  Button itemsHouse = new Button("Items In Your House");
  Button movieTitles = new Button("1 Word Movie Titles");
  Button animals = new Button("Animals");
  BorderPane loadScreen;
  Scene firstScene;
  ClientNetworking networking;
  TextField t1;
  VBox v1, v2;
  ListView<String> listItems1;
  SerializedClass a;
  Boolean lostGame = false;
  int gameStatus = 0;

  //Launch method for first scene
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    launch(args);
  }

  //First Scene. Asks the user for port number
  public void start(Stage primaryStage) throws Exception {
    // TODO Auto-generated method stub

  playAgain.setDisable(true);
    primaryStage.setTitle("Welcome to word guesser!");
    clientStart = new Button("Enter");
    TextField t1 = new TextField();
    v1 = new VBox();
    clientStart.setMinHeight(100);
    clientStart.setMinWidth(200);

    clientStart.setOnAction(e->{                   // Creates Client object that will connect to port
      primaryStage.setScene(inputScene(primaryStage));// Category scene
      networking = new ClientNetworking(Integer.parseInt(t1.getText()), data->{ // client object
        Platform.runLater(()->{
          SerializedClass a = (SerializedClass)data;
          listItems1.getItems().add(a.message.toString()); //Displays client log to clientGUI
              if (a.message.toString().equals("Game over!")) { // 'Game over!' flag that signals game is over
                  listItems1.getItems().clear();
                  guess.setDisable(true);
                playAgain.setDisable(false);
              }
                if ((Boolean)a.catOneFinish.equals(true)) { // Checks if player won on any category
                  itemsHouse.setDisable(true);
                } else if ((Boolean)a.catTwoFinish.equals(true)) {
                  movieTitles.setDisable(true);
                } else if ((Boolean)a.catThreeFinish.equals(true)) {
                  animals.setDisable(true);
                }
                if ((Boolean)a.haveTheyLost.equals(true)) { // sent by server to signify a game loss
                  lostGame = true;
                }
        });
      });
        networking.start(); // starts client networking object
    });

    listItems1 = new ListView<>();


    loadScreen = new BorderPane();
    v1.getChildren().addAll(t1, clientStart);
    loadScreen.setCenter(v1);
    firstScene = new Scene(loadScreen, 700, 700);
    primaryStage.setScene(firstScene);
    primaryStage.show();

    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() { // terminates program when user closes window
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
  }

  public Scene inputScene(Stage primaryStage) { // category scene
  listItems1.getItems().clear();
    BorderPane pane = new BorderPane();
    Label l1 = new Label("Select Category");
    l1.setAlignment(Pos.CENTER);
    VBox v1 = new VBox();
    HBox h1 = new HBox();
    h1.setSpacing(30);

    itemsHouse.setOnAction(e->{ // Sends serialized to server to indicate selected category
    guess.setDisable(false);
      a = new SerializedClass();
      a.categoryChosen = 1;
      networking.sendSerializedClass(a);
      primaryStage.setScene(MenuScene(primaryStage)); // game scene

    });

    movieTitles.setOnAction(e->{ 
    guess.setDisable(false);
      a = new SerializedClass();
      a.categoryChosen = 2;
      networking.sendSerializedClass(a);
      primaryStage.setScene(MenuScene(primaryStage));

    });
    animals.setOnAction(e->{
    guess.setDisable(false);
      a = new SerializedClass();
      a.categoryChosen = 3;
      networking.sendSerializedClass(a);
      primaryStage.setScene(MenuScene(primaryStage));

    });
    if (animals.isDisabled() == true && itemsHouse.isDisabled() == true && movieTitles.isDisabled() == true) { // win screen

      VBox v2 = new VBox();           
      Button quit = new Button("QUIT");
      Label youWin = new Label("You have won! Press 'Quit' to leave");
      h1.getChildren().addAll(itemsHouse,movieTitles,animals);
      v2.getChildren().addAll(youWin,quit);
      v1.getChildren().addAll(l1, h1);
      pane.setCenter(v1);
      pane.setBottom(v2);

      quit.setOnAction(e->{
        Platform.exit();
        System.exit(0);
      });
      return new Scene(pane, 500, 500);


  }

    if (lostGame == true) { // Player loss screen
      itemsHouse.setDisable(true);
      movieTitles.setDisable(true);
      animals.setDisable(true);

      VBox v2 = new VBox();
      Button quit = new Button("QUIT");
      Label youWin = new Label("You have LOST! Press 'Quit' to leave");

      h1.getChildren().addAll(itemsHouse,movieTitles,animals);
      v2.getChildren().addAll(youWin,quit);
      v1.getChildren().addAll(l1, h1);
      pane.setCenter(v1);
      pane.setBottom(v2);

      quit.setOnAction(e->{
        Platform.exit();
        System.exit(0);
      });
      return new Scene(pane, 500, 500);
    }
    h1.getChildren().addAll(itemsHouse,movieTitles,animals);
    v1.getChildren().addAll(l1, h1);
    pane.setCenter(v1);
    return new Scene(pane, 500, 500);

  }

  public Scene MenuScene(Stage primaryStage) { // game screen
    BorderPane pane = new BorderPane();
    TextField t3;
    t3 = new TextField();
    VBox v3,v4,v5;
    v3 = new VBox();
    v4 = new VBox();
    v5= new VBox();
    HBox h2, h3;
    h2 = new HBox();
    h3 = new HBox();
    Label l3,l4;
    playAgain = new Button("Play Again");
    playAgain.setDisable(true);
    quit = new Button("Quit");
    l3 = new Label("Enter letter here");
    l4 = new Label("Log");
    v3.getChildren().addAll(l3, t3, guess);
    v4.getChildren().addAll(l4, listItems1);
    h2.getChildren().addAll(v3,v4);
    h3.getChildren().addAll(playAgain, quit);
    v5.getChildren().addAll(h2,h3);
    pane.setCenter(v5);

    guess.setOnAction(e->{ // sends guess as serialized client to server
      a = new SerializedClass();
      a.guess = t3.getText().charAt(0);
      networking.sendSerializedClass(a);
      t3.clear();
    });

    playAgain.setOnAction(e->{ // goes back to category screen
      primaryStage.setScene(inputScene(primaryStage));
   });

    quit.setOnAction(e->{ // quits out of program
      Platform.exit();
        System.exit(0);
    });


    return new Scene(pane, 700, 700);

  }
}
