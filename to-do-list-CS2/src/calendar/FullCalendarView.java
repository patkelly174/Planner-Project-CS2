package calendar;

import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class FullCalendarView {

	private ArrayList<AnchorPaneNode> allDays = new ArrayList<>(35);
	private VBox view;
	private Text title;
	private YearMonth currentYearMonth;

	public FullCalendarView(YearMonth yearMonth) {
		currentYearMonth = yearMonth;

		GridPane calendar = new GridPane();
		calendar.setPrefSize(900, 700);
		calendar.setGridLinesVisible(true);

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 7; j++) {
				AnchorPaneNode pane = new AnchorPaneNode();
				pane.setPrefSize(400, 400);
				calendar.add(pane, j, i);
				allDays.add(pane);
			}
		}

		Text[] dayNames = new Text[] { new Text("Sunday"), new Text("Monday"), new Text("Tuesday"),
				new Text("Wednesday"), new Text("Thursday"), new Text("Friday"), new Text("Saturday") };
		GridPane dayLabels = new GridPane();
		dayLabels.setPrefWidth(600);
		Integer col = 0;
		for (Text text : dayNames) {
			AnchorPane pane = new AnchorPane();
			pane.setPrefSize(200, 10);
			AnchorPane.setBottomAnchor(text, 5.0);
			pane.getChildren().add(text);
			dayLabels.add(pane, col++, 0);
		}
		// creates title and buttons
		title = new Text();
		Button previousMonth = new Button("<--");
		previousMonth.setOnAction(e -> previousMonth());
		Button nextMonth = new Button("-->");
		nextMonth.setOnAction(e -> nextMonth());
		HBox titleBar = new HBox(previousMonth, title, nextMonth);
		titleBar.setAlignment(Pos.BASELINE_CENTER);
		// adds day numbers into calendar
		populateCalendar(yearMonth);
		view = new VBox(titleBar, dayLabels, calendar);
	}

// makes it so the day numbers correspond to the correct days
	public void populateCalendar(YearMonth yearMonth) {
		// gets the day we want to start with
		LocalDate calendarDate = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
		while (!calendarDate.getDayOfWeek().toString().equals("SUNDAY")) {
			calendarDate = calendarDate.minusDays(1);
		}
		for (AnchorPaneNode pane : allDays) {
			if (pane.getChildren().size() != 0) {
				pane.getChildren().remove(0);
			}
			Text text = new Text(String.valueOf(calendarDate.getDayOfMonth()));
			pane.setDate(calendarDate);
			AnchorPane.setTopAnchor(text, 5.0);
			AnchorPane.setLeftAnchor(text, 5.0);
			pane.getChildren().add(text);
			calendarDate = calendarDate.plusDays(1);
		}
		// changes the title to the correct month
		title.setText(yearMonth.getMonth().toString() + " " + String.valueOf(yearMonth.getYear()));
	}

	public ArrayList<AnchorPaneNode> getAllCalendarDays() {
		return allDays;
	}

	public void setAllCalendarDays(ArrayList<AnchorPaneNode> allCalendarDays) {
		this.allDays = allCalendarDays;
	}

	private void previousMonth() {
		currentYearMonth = currentYearMonth.minusMonths(1);
		populateCalendar(currentYearMonth);
	}

	private void nextMonth() {
		currentYearMonth = currentYearMonth.plusMonths(1);
		populateCalendar(currentYearMonth);
	}

	public VBox getView() {
		return view;
	}

}
