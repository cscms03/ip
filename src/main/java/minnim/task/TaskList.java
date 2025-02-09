package minnim.task;

import java.util.ArrayList;

import minnim.exception.MinnimMissingDateException;
import minnim.exception.MinnimMissingTaskDetailException;
import minnim.exception.MinnimNoTaskFoundException;
import minnim.exception.MinnimTargetTaskNumNotFoundException;
import minnim.exception.MinnimException;
import minnim.ui.Ui;


/**
 * Manages a list of tasks and provides operations to add, mark, unmark, delete, and list tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private Ui ui;

    /**
     * Constructs a TaskList with the given list of tasks and user interface.
     *
     * @param tasks The list of tasks.
     * @param ui    The user interface for displaying messages.
     */
    public TaskList(ArrayList<Task> tasks, Ui ui) {
        this.tasks = tasks;
        this.ui = ui;
    }

    /**
     * Retrieves the list of tasks.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Searches for tasks that contain the specified keyword in their description.
     * The method checks if the message contains a valid keyword (i.e., after the "find" command)
     * and searches the task descriptions for any match. If matching tasks are found, they are displayed to the user.
     * If no tasks match the keyword, an appropriate message is shown.
     *
     * @param message the user's input, expected to be in the format "find <keyword>"
     * @throws MinnimMissingTaskDetailException if no keyword is provided (i.e., only "find" is entered)
     */
    public void find(String message) throws MinnimMissingTaskDetailException {
        try {
            if (message.trim().length() == 4) {
                // case where only "find" was entered without keyword
                throw new MinnimMissingTaskDetailException();
            }
            String keyword = message.substring(5).trim();  // Extract the keyword from the message
            ArrayList<Task> matchingTasks = new ArrayList<>();

            for (Task task : tasks) {
                if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                    matchingTasks.add(task);
                }
            }
            if (matchingTasks.isEmpty()) {
                ui.showMessage("No matching tasks found.");
            } else {
                ui.showMessage("Here are the matching tasks in your list:");
                for (int i = 0; i < matchingTasks.size(); i++) {
                    ui.showMessage((i + 1) + ". " + matchingTasks.get(i).getDescription());
                }
            }
        } catch (MinnimException e) {
            System.out.println(e);
        }
    }

    /**
     * Adds a new Todo task to the task list.
     *
     * @param message The input command containing the task description.
     * @throws MinnimMissingTaskDetailException If the task description is missing.
     */
    public void addTodo(String message) throws MinnimMissingTaskDetailException {
        try {
            Todo todo = new Todo(message.substring(5));
            tasks.add(todo);
            ui.showTaskAdded(todo, tasks.size());
        } catch (StringIndexOutOfBoundsException e) {
            throw new MinnimMissingTaskDetailException();
        }
    }

    /**
     * Adds a new Deadline task to the task list.
     *
     * @param message The input command containing the task description and due date.
     * @throws MinnimMissingDateException       If the due date is missing.
     * @throws MinnimMissingTaskDetailException If the task description is missing.
     */
    public void addDeadline(String message) throws MinnimMissingDateException, MinnimMissingTaskDetailException {
        try {
            if (message.length() == 8) {
                // case where only "deadline" was entered without task nor dates
                throw new MinnimMissingTaskDetailException();
            }
            int index = message.indexOf("/");
            String date = message.substring(index + 1).replaceFirst("by", "").trim();
            Deadline deadline = new Deadline(message.substring(9, index - 1), date);
            tasks.add(deadline);
            ui.showTaskAdded(deadline, tasks.size());
        } catch (StringIndexOutOfBoundsException e) {
            // case where deadline and task details were given but missing a date
            throw new MinnimMissingDateException();
        }
    }

    /**
     * Adds a new Event task to the task list.
     *
     * @param message The input command containing the task description and event dates.
     * @throws MinnimMissingDateException       If the event dates are missing.
     * @throws MinnimMissingTaskDetailException If the task description is missing.
     */
    public void addEvent(String message) throws MinnimMissingDateException, MinnimMissingTaskDetailException {
        try {
            if (message.length() == 5) {
                // case where only "event" was entered without task nor dates
                throw new MinnimMissingTaskDetailException();
            }
            int firstIndex = message.indexOf("/");
            int secondIndex = message.indexOf("/", firstIndex + 1);
            String fromDate = message.substring(firstIndex + 1, secondIndex - 1).replaceFirst("from", "").trim();
            String toDate = message.substring(secondIndex + 1).replaceFirst("to", "").trim();
            Events event = new Events(message.substring(6, firstIndex - 1), fromDate, toDate);
            tasks.add(event);
            ui.showTaskAdded(event, tasks.size());
        } catch (StringIndexOutOfBoundsException e) {
            throw new MinnimMissingDateException();
        }
    }

    /**
     * Marks a task as completed.
     *
     * @param message The input command specifying the task number to mark.
     * @throws MinnimNoTaskFoundException           If the task number does not exist.
     * @throws MinnimTargetTaskNumNotFoundException If no task number is provided.
     */
    public void markTask(String message) throws MinnimNoTaskFoundException, MinnimTargetTaskNumNotFoundException {
        if (message.trim().length() == 4) {
            // if only command is given without target task number
            throw new MinnimTargetTaskNumNotFoundException();
        }
        int taskNum = Integer.parseInt(message.substring(5).trim());
        try {
            Task task = tasks.get(taskNum - 1);
            task.setMarked();
            ui.showTaskMarked(task);
        } catch (IndexOutOfBoundsException e) {
            throw new MinnimNoTaskFoundException(taskNum);
        }
    }

    /**
     * Unmarks a completed task.
     *
     * @param message The input command specifying the task number to unmark.
     * @throws MinnimNoTaskFoundException           If the task number does not exist.
     * @throws MinnimTargetTaskNumNotFoundException If no task number is provided.
     */
    public void unmarkTask(String message) throws MinnimNoTaskFoundException, MinnimTargetTaskNumNotFoundException {
        if (message.trim().length() == 6) {
            // if only command is given without target task number
            throw new MinnimTargetTaskNumNotFoundException();
        }
        int taskNum = Integer.parseInt(message.substring(7).trim());
        try {
            Task task = tasks.get(taskNum - 1);
            task.setUnmarked();
            ui.showTaskUnmarked(task);
        } catch (IndexOutOfBoundsException e) {
            throw new MinnimNoTaskFoundException(taskNum);
        }
    }

    /**
     * Deletes a task from the list.
     *
     * @param message The input command specifying the task number to delete.
     * @throws MinnimTargetTaskNumNotFoundException If no task number is provided.
     * @throws MinnimNoTaskFoundException           If the task number does not exist.
     */
    public void deleteTask(String message) throws MinnimTargetTaskNumNotFoundException, MinnimNoTaskFoundException {
        if (message.trim().length() == 6) {
            // if only command is given without target task number
            throw new MinnimTargetTaskNumNotFoundException();
        }
        int taskNum = Integer.parseInt(message.substring(7).trim());
        try {
            Task task = tasks.remove(taskNum - 1);
            ui.showTaskDeleted(task, tasks.size());
        } catch (IndexOutOfBoundsException e) {
            throw new MinnimNoTaskFoundException(taskNum);
        }
    }

    /**
     * Lists all tasks in the task list.
     */
    public void listTasks() {
        if (tasks.isEmpty()) {
            ui.showMessage("Your task list is empty.");
        } else {
            for (int j = 0; j < tasks.size(); j++) {
                ui.showMessage((j + 1) + ". " + tasks.get(j).getDescription());
            }
        }
    }
}
