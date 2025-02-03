package minnim.storage;

import minnim.task.Deadline;
import minnim.task.Events;
import minnim.task.Task;
import minnim.task.Todo;
import minnim.ui.Ui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filePath;
    private Ui ui;

    public Storage(String filePath, Ui ui) {
        this.filePath = filePath;
        this.ui = ui;
    }

    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            ui.showMessage("Task file not found. Starting with an empty list.");
            return tasks;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" \\| ");
                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                Task task = null;
                if (type.equals("Todo")) {
                    task = new Todo(description);
                } else if (type.equals("Deadline")) {
                    task = new Deadline(description, parts[3]);
                } else if (type.equals("Event")) {
                    task = new Events(description, parts[3], parts[4]);
                }

                if (task != null) {
                    if (isDone) {
                        task.setMarked();
                    }
                    tasks.add(task);
                }

            }
        } catch (FileNotFoundException e) {
            ui.showError("Task file not found.");
        } catch (Exception e) {
            ui.showError("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    public void saveTasks(ArrayList<Task> tasks) {
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