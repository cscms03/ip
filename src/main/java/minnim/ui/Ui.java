package minnim.ui;

import minnim.task.Task;

public class Ui {
    public void showWelcomeMessage() {
        System.out.println("Hello! I'm Minnim.");
        System.out.println("What can I do for you?");
    }

    public void showGoodbyeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String errorMessage) {
        System.out.println("Error: " + errorMessage);
    }

    public void showTaskAdded(Task task, int size) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task.getDescription());
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    public void showTaskDeleted(Task task, int size) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(task.getDescription());
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task.getDescription());
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task.getDescription());
    }

    public void showUnknownCommandMessage() {
        System.out.println("Unknown command. Please try again.");
    }
}