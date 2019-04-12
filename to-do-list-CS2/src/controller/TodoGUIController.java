package controller;

import java.io.FileInputStream;



import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import model.AppData;
import model.TodoTask;

public class TodoGUIController {
	@FXML
	private SplitPane mainPane;
	@FXML
	private TextField descriptionText;
	@FXML
	private DatePicker datePicker;
	@FXML
	private Button addTaskButton;
	@FXML
	private Button completeButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Label errorLabel;
	@FXML
	private ListView<TodoTask> taskList;
	@FXML
	private ListView<TodoTask> taskListDone;
	@FXML
	private CheckBox noDueDateCheckbox;


	ObservableList<TodoTask> todolist = FXCollections.observableArrayList();
	ObservableList<TodoTask> todolistdone = FXCollections.observableArrayList();


	
	@FXML
	void addNewTask(ActionEvent event) {
		if (addTaskValidate()) {
			addTaskCommit();
		}
	}

	private boolean addTaskValidate() {
		if (descriptionText.getText().equals("") || descriptionText.getText().equals(" ")) {
			errorMsg("Provide a task to add to the list");
			return false;
		} else if (datePicker.getValue().isBefore(LocalDate.now())) {
			errorMsg("Cannot create a task in the past");
			return false;
		}

		if (isDuplicate()) {
			errorMsg("You already have this task");
			return false;
		}
		return true;
	}

	private void addTaskCommit() {
		TodoTask test = new TodoTask(descriptionText.getText(), (noDueDateCheckbox.isSelected() ? null : datePicker.getValue()));
		todolist.add(test);
		sortListByDate(todolist);
		taskList.setItems(todolist);
		descriptionText.setText("");
		errorLabel.setText("");

		toggleButtons(todolist.isEmpty() && todolistdone.isEmpty());
		datePicker.setDisable(false);
		noDueDateCheckbox.setSelected(false);
		noDueDateCheckbox.setDisable(false);
		datePicker.setValue(LocalDate.now());
	}

	@FXML
	void markAsComplete(ActionEvent event) {
		TodoTask task = null;

		if (taskList.isFocused()) {
			task = taskList.getSelectionModel().getSelectedItem();
		} else if (taskListDone.isFocused()) {
			task = taskListDone.getSelectionModel().getSelectedItem();
		}

		if (task != null) {
			if (task.isCompleted()) {
				todolistdone.remove(task);
				todolist.add(task);
				task.setCompleted(false);
				task.setCompletionDate(null);
				sortListByDate(todolist);
				taskList.setItems(todolist);
			} 
			else {
				todolist.remove(task);
				todolistdone.add(task);
				task.setCompleted(true);
				task.setCompletionDate(LocalDate.now());
				sortListByDate(todolistdone);
				taskListDone.setItems(todolistdone);
			}
		}
	}

	@FXML
	void deleteTask(ActionEvent event) {
		todolist.remove(taskList.getSelectionModel().getSelectedItem());
		todolistdone.remove(taskListDone.getSelectionModel().getSelectedItem());
		taskList.refresh();
		taskListDone.refresh();

		toggleButtons(todolist.isEmpty() && todolistdone.isEmpty());
		taskList.getSelectionModel().clearSelection();
		taskListDone.getSelectionModel().clearSelection();
	}

	@FXML
	void doneListClicked(MouseEvent event) {
		if (!todolistdone.isEmpty()) {
			completeButton.setText("Incomplete");
		}

		completeButton.setDisable(todolistdone.isEmpty());
		deleteButton.setDisable(todolistdone.isEmpty());
		taskList.getSelectionModel().clearSelection();
	}

	@FXML
	void listClicked(MouseEvent event) {
		if (!todolist.isEmpty()) {
			completeButton.setText("Mark Done");
		}

		completeButton.setDisable(todolist.isEmpty());
		deleteButton.setDisable(todolist.isEmpty());
	}

	@FXML
	void addTaskClicked(MouseEvent event) {
		taskList.getSelectionModel().clearSelection();
		taskListDone.getSelectionModel().clearSelection();


	}

	@FXML
	void noDueDateSelected(ActionEvent event) {
		if (noDueDateCheckbox.isSelected()) {
			datePicker.setDisable(true);
		} else {
			datePicker.setDisable(false);
		}
	}

	@FXML
	public void initialize() {
		datePicker.setValue(LocalDate.now());
		toggleButtons(todolist.isEmpty() && todolistdone.isEmpty());

		loadAppData();

		// saves events for when application is closed
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				saveAppData();
			}
		});
	}

	private void errorMsg(String text) {
		errorLabel.setText(text);
		errorLabel.setTextFill(Color.ORANGE);
	}
	/////////////////////////////////////////
	public void saveAppData() {
		OutputStream ops = null;
		ObjectOutputStream objOps = null;
		try {
			ArrayList<TodoTask> list1 = (ArrayList<TodoTask>) todolist.stream().collect(Collectors.toList());
			ArrayList<TodoTask> list2 = (ArrayList<TodoTask>) todolistdone.stream().collect(Collectors.toList());

			AppData data = new AppData(list1, list2);

			ops = new FileOutputStream("todolistData.txt");
			objOps = new ObjectOutputStream(ops);
			objOps.writeObject(data);
			objOps.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objOps != null)
					objOps.close();
			} catch (Exception ex) {

			}
		}
	}

	public void loadAppData() {
		InputStream fileIs = null;
		ObjectInputStream objIs = null;
		try {
			fileIs = new FileInputStream("todolistData.txt");
			objIs = new ObjectInputStream(fileIs);
			AppData data = (AppData) objIs.readObject();
			todolist.setAll(data.getList());
			todolistdone.setAll(data.getListDone());
			taskList.setItems(todolist);
			taskListDone.setItems(todolistdone);

		} catch (FileNotFoundException e) {
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objIs != null)
					objIs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	//////////////////////////////////////////
	public void sortListByDate(ObservableList<TodoTask> list) {
		Collections.sort(list, new Comparator<TodoTask>() {
		@Override
		public int compare(TodoTask t1, TodoTask t2) {

			if (t1.getDueDate() == null) {
				return 1;
			} else if (t2.getDueDate() == null) {
				return -1;
			}

			if (t1.getDueDate().isAfter(t2.getDueDate()) || t1.getDueDate().isEqual(t2.getDueDate())) {
				return 1;
			}
			if (t1.getDueDate().isBefore(t2.getDueDate())) {
				return -1;
			}
			return 0;
		}
	});

	}

	private void toggleButtons(boolean listsEmpty) {
		completeButton.setDisable(listsEmpty);
		deleteButton.setDisable(listsEmpty);
	}

	private boolean isDuplicate() {
		for (int i = 0; i < todolist.size(); i++) {
			if (descriptionText.getText().equals(todolist.get(i).getDescription())) {
				if (datePicker.isDisabled()) {
					if (todolist.get(i).getDueDate() == null) {
						return true;
					}
				} else {
					if (todolist.get(i).getDueDate() != null
							&& todolist.get(i).getDueDate().isEqual(datePicker.getValue())) {
						return true;
					}
				}
			}
		}
		return false;
	}




}