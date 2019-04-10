package model;

import java.io.Serializable;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class TodoTask implements Serializable {
	private LocalDate dueDate;
	private LocalDate completionDate;
	private String description;
	private boolean completed;

	public TodoTask(String description, LocalDate date) {
		this.description = description;
		this.dueDate = date;
		this.completionDate = null;
		this.completed = false;
	}


	
	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(LocalDate completionDate) {
		this.completionDate = completionDate;
	}



	@Override
	public String toString() {
		String[] finishedDateArray = null;
		String[] addDateArray = null;
		if (dueDate != null) {
			addDateArray = dueDate.toString().split("-");
		}
		if (completionDate != null) {
			finishedDateArray = completionDate.toString().split("-");
		}

		if (completed) {
			return finishedDateArray[2] + "/" + finishedDateArray[1] + "/" + finishedDateArray[0] + " | " + description + ((addDateArray == null) ? "" : "   (due " + addDateArray[2] + "/" + addDateArray[1] + "/" + addDateArray[0] + ")");
		} else {
			return ((addDateArray == null) ? "" : addDateArray[2] + "/" + addDateArray[1] + "/" + addDateArray[0] + " | ") + description;
		}
	}
}
