import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class Game extends Application{
	GameLogic gameHandler = new GameLogic();
	ServerNetworking server;
	Button startGame;
	BorderPane loadScreen;
	Scene firstScene;
	VBox v1, v2;
	TextField t1;
	
	ListView<String> items1;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		primaryStage.setTitle("Welcome to word guesser!");
		t1 = new TextField();
		startGame = new Button("PLAY");
		startGame.setMinHeight(100);
		startGame.setMinWidth(200);
		startGame.setOnAction(e->{ primaryStage.setScene(createServer());
			primaryStage.setTitle("Server client");
				server = new ServerNetworking(Integer.parseInt(t1.getText()), data->{ 
					Platform.runLater(()->{
						items1.getItems().add(data.toString());
					});
					
				});
	
			});
		
		
		items1 = new ListView<>();
		loadScreen = new BorderPane();
		//loadScreen.setPadding(new Insets(70));
		v1 = new VBox();
		v1.getChildren().addAll(t1, startGame);
		loadScreen.setCenter(v1);
		firstScene = new Scene(loadScreen, 400, 400);
		
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
	public Scene createServer() {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		v2 = new VBox();
		v2.getChildren().addAll(items1);
		pane.setCenter(v2);
		return new Scene(pane, 500, 400);
	}
	
	
	

	
	
}
