package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	
	public static void main(String [] args) {
		
		launch(args);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		String fxmlPath = "/gui/ToDoListGUI.fxml";
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
		Scene scene = new Scene(loader.load());
		primaryStage.setResizable(false);
		primaryStage.setTitle("To-Do List");
		primaryStage.setScene(scene);
		primaryStage.show();



	}


}
