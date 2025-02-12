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
        return "Got it. I've added this task:\n" + task.getDescription() + "\nNow you have " + size + " tasks in the list.";
    }

    public String showTaskDeleted(Task task, int size) {
        return "Noted. I've removed this task:\n" + task.getDescription() + "\nNow you have " + size + " tasks in the list.";
    }

    public String showTaskMarked(Task task) {
        return "Nice! I've marked this task as done:\n" + task.getDescription();
    }

    public String showTaskUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n" + task.getDescription();
    }

    public String showUnknownCommandMessage() {
        return "Unknown command. Please try again.";
    }
}

