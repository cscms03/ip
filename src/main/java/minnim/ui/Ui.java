package minnim.ui;

import minnim.task.Task;

public class Ui {
    public String showWelcomeMessage() {
        return "Hello! I'm Minnim.\nWhat can I do for you?";
    }

    public String showGoodbyeMessage() {
        return "Bye. Hope to see you again soon!";
    }
    public String showMessage(String message) {
        return message;
    }

    public String showError(String errorMessage) {
        return "Error: " + errorMessage;
    }

    public String showTaskAdded(Task task, int size) {
        return String.format("Got it. I've added this task:\n%s\nNow you have %d tasks in the list.",
                task.getDescription(), size);
    }

    public String showTaskDeleted(Task task, int size) {
        return String.format("Noted. I've removed this task:\n%s\nNow you have %d tasks in the list.",
                task.getDescription(), size);
    }

    public String showTaskMarked(Task task) {
        return String.format("Nice! I've marked this task as done:\n%s", task.getDescription());
    }

    public String showTaskUnmarked(Task task) {
        return String.format("OK, I've marked this task as not done yet:\n%s", task.getDescription());
    }

    public String showUnknownCommandMessage() {
        return "Unknown command. Please try again.";
    }
}

