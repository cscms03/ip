package minnim.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import minnim.task.Deadline;
import minnim.task.Events;
import minnim.task.Task;
import minnim.task.Todo;
import minnim.ui.Ui;

/**
 * Handles loading and saving tasks to a file.
 */
public class Storage {
    private String filePath;
    private Ui ui;

    /**
     * Constructs a Storage object with the specified file path and UI handler.
     *
     * @param filePath The file path where tasks are stored.
     * @param ui       The UI handler for displaying messages.
     */
    public Storage(String filePath, Ui ui) {
        this.filePath = filePath;
        this.ui = ui;
    }

    private ArrayList<String> readFile() {
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            ui.showError("Task file not found.");
        } catch (Exception e) {
            ui.showError("Error loading tasks: " + e.getMessage());
        }
        return lines;
    }

    private Task createTaskFromLine(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = switch (type) {
            case "Todo" -> new Todo(description);
            case "Deadline" -> new Deadline(description, parts[3]);
            case "Event" -> new Events(description, parts[3], parts[4]);
            default -> null;
        };

        if (task != null && isDone) {
            task.setMarked();
        }
        return task;
    }

   /**
     * Loads tasks from a file and returns them as an ArrayList.
     * If the file is not found or empty, an empty task list is returned.
     *
     * @return An ArrayList of Task objects loaded from the file.
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<String> lines = readFile();

        if (lines.isEmpty()) {
            ui.showMessage("Task file not found. Starting with an empty list.");
            return tasks;
        }

        for (String line : lines) {
            Task task = createTaskFromLine(line);
            if (task != null) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    /**
     * Saves the given list of tasks to the specified file.
     *
     * @param tasks The list of tasks to be saved.
     */
    public void saveTasks(ArrayList<Task> tasks) {
        assert tasks != null : "Task list cannot be null";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(task.toFileString());
                writer.newLine();
            }
            ui.showMessage("Tasks saved successfully.");
        } catch (IOException e) {
            ui.showError("Error saving tasks: " + e.getMessage());
        }
    }
}