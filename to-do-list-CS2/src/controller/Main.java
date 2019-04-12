package controller;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ResourceBundle;

import calendar.FullCalendarView;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@FXML
	Stage window;
	@FXML
	Scene menu;
	@FXML
	Scene todolist;

	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;

		String menufxmlPath = "Menu.fxml";
		FXMLLoader menuloader = new FXMLLoader(getClass().getResource(menufxmlPath));
		menu = new Scene(menuloader.load());

		window.setTitle("Planner");
		window.setScene(menu);
		window.show();

	}

	@FXML
	private void sceneHandler(ActionEvent event) throws IOException {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TodoGUI.fxml"));
		Parent root1 = (Parent) fxmlLoader.load();
		
		Stage stage = new Stage();
		stage.setTitle("ToDoList");
		stage.setScene(new Scene(root1));
		stage.show();


	}

	@FXML
	private void sceneHandlerCalendar(ActionEvent event) throws IOException, ClassNotFoundException {

		Stage stage = new Stage();
		stage.setTitle("Full Calendar");
		stage.setScene(new Scene(new FullCalendarView(YearMonth.now()).getView()));
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}


}
