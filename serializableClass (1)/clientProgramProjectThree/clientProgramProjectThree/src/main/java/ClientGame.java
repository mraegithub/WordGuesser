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
	//ClientNetworking networking = new ClientNetworking();
	Button clientStart;
	BorderPane loadScreen;
	Scene firstScene;
	ClientNetworking networking;
	TextField t1;
	VBox v1, v2;
	ListView<String> listItems1;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		
		primaryStage.setTitle("Welcome to word guesser!");
		clientStart = new Button("Enter");
		TextField t1 = new TextField();
		v1 = new VBox();
		clientStart.setMinHeight(100);
		clientStart.setMinWidth(200);
		
		clientStart.setOnAction(e->{ primaryStage.setScene(inputScene(primaryStage));
		networking = new ClientNetworking(Integer.parseInt(t1.getText()), data->{ 
				Platform.runLater(()->{
					listItems1.getItems().add(data.toString());
					t1.clear();
				});
		});
		networking.start();
		});
					
		listItems1 = new ListView<>();
		
		
		loadScreen = new BorderPane();
		v1.getChildren().addAll(t1, clientStart);
		loadScreen.setCenter(v1);
		firstScene = new Scene(loadScreen, 700, 700);
		primaryStage.setScene(firstScene);
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
	}
	
	public Scene inputScene(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		Button b1,b2,b3;
		Label l1 = new Label("Select Category");
		l1.setAlignment(Pos.CENTER);
		VBox v1 = new VBox();
		b1 = new Button("Items In Your House");
		b2 = new Button("1 Word Movie Titles");
		b3 = new Button("Animals");
		HBox h1 = new HBox();
		h1.setSpacing(30);
		h1.getChildren().addAll(b1,b2,b3);
		v1.getChildren().addAll(l1, h1);
		
		pane.setCenter(v1);
		
		b1.setOnAction(e->{ 
			primaryStage.setScene(MenuScene(primaryStage));
		
		});

		b2.setOnAction(e->{ 
			primaryStage.setScene(MenuScene(primaryStage));
		
		});

		b3.setOnAction(e->{ 
			primaryStage.setScene(MenuScene(primaryStage));
		
		});
		
		return new Scene(pane, 500, 500);
		
	}
	
	public Scene MenuScene(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		TextField t1, t2, t3;
		t1 = new TextField();
		t1.setEditable(false);
		t2 = new TextField();
		t2.setEditable(false);
		t3 = new TextField();
		t3.setEditable(false);
		VBox v1,v2,v3,v4,v5;
		v1 = new VBox();
		v2 = new VBox();
		v3 = new VBox();
		v4 = new VBox();
		v5= new VBox();
		HBox h1,h2, h3;
		h1 = new HBox();
		h2 = new HBox();
		h3 = new HBox();
		Label l1,l2,l3,l4;
		Button b1, b2, b3;
		b1 = new Button("GUESS");
		b2 = new Button("Play Again");
		b3 = new Button("Quit");
		l1 = new Label("Guesses Remaining");
		l2 = new Label("Word");
		l3 = new Label("Enter letter here");
		l4 = new Label("Log");
		v1.getChildren().addAll(l1,t1);
		v2.getChildren().addAll(l2,t2);
		v3.getChildren().addAll(l3, t3, b1);
		v4.getChildren().addAll(l4, listItems1);
		h1.getChildren().addAll(v1, v2);
		h2.getChildren().addAll(v3,v4);
		h3.getChildren().addAll(b2, b3);
		v5.getChildren().addAll(h1,h2,h3);
		pane.setCenter(v5);
		
		return new Scene(pane, 700, 700);
		
	}
}
