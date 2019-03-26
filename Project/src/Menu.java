import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Menu extends Application implements Initializable{
	
	@FXML
	Button myButton,cal,free,todo;
	
	public static void main(String[] args) {
		launch(args);
	
}
	
	
	public void start(Stage primaryStage) throws Exception {
		final FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
		final Pane p = loader.load();
		
		primaryStage.setScene(new Scene(p));
		primaryStage.show();

	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		free.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = new Stage();
				stage.setTitle("Free Time");
				stage.setScene(new Scene(new Button("Free Time"), 400, 300));
				stage.show();
			}});
		
		cal.setOnAction(new EventHandler<ActionEvent>() {
		@Override
			public void handle(ActionEvent eventl) {
			Stage stage = new Stage();
			stage.setTitle("Calender");
			stage.setScene(new Scene(new Button("Calender"), 400, 300));
			stage.show();			
		}});
		
		todo.setOnAction(new EventHandler<ActionEvent>() {
		@Override
				public void handle(ActionEvent event2) {
				Stage stage = new Stage();
				stage.setTitle("To Do");
				stage.setScene(new Scene(new Button("To Do"), 400, 300));
				stage.show();
			}
		});
		
	
	}
	}

