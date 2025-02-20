package minnim.task;

import java.util.ArrayList;

import minnim.exception.MinnimMissingDateException;
import minnim.exception.MinnimMissingTaskDetailException;
import minnim.exception.MinnimNoTaskFoundException;
import minnim.exception.MinnimTargetTaskNumNotFoundException;
import minnim.exception.MinnimException;
import minnim.ui.Ui;

//TODO: Update JavaDoc as return values changed from void to String

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
    public String find(String message) throws MinnimMissingTaskDetailException {
        try {
            if (message.trim().length() == 4) {
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
                return ui.showMessage("No matching tasks found.");
            } else {
                StringBuilder response = new StringBuilder("Here are the matching tasks in your list:\n");
                for (int i = 0; i < matchingTasks.size(); i++) {
                    response.append((i + 1)).append(". ").append(matchingTasks.get(i).getDescription()).append("\n");
                }
                return response.toString();
            }
        } catch (MinnimException e) {
            return ui.showError(e.getMessage());
        }
    }

    /**
     * Adds a new Todo task to the task list.
     *
     * @param message The input command containing the task description.
     * @throws MinnimMissingTaskDetailException If the task description is missing.
     */
    public String addTodo(String message) throws MinnimMissingTaskDetailException {
        try {
            Todo todo = new Todo(message.substring(5));
            tasks.add(todo);
            return ui.showTaskAdded(todo, tasks.size());
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
    public String addDeadline(String message) throws MinnimMissingDateException, MinnimMissingTaskDetailException {
        try {
            if (message.length() == 8) {
                throw new MinnimMissingTaskDetailException();
            }
            int index = message.indexOf("/");
            String date = message.substring(index + 1).replaceFirst("by", "").trim();
            Deadline deadline = new Deadline(message.substring(9, index - 1), date);
            tasks.add(deadline);
            return ui.showTaskAdded(deadline, tasks.size());
        } catch (StringIndexOutOfBoundsException e) {
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
    public String addEvent(String message) throws MinnimMissingDateException, MinnimMissingTaskDetailException {
        try {
            if (message.length() == 5) {
                throw new MinnimMissingTaskDetailException();
            }
            assert message.contains("/") : "date format: /from ... /to ...";

            int firstIndex = message.indexOf("/");
            int secondIndex = message.indexOf("/", firstIndex + 1);
            String fromDate = message.substring(firstIndex + 1, secondIndex - 1).replaceFirst("from", "").trim();
            String toDate = message.substring(secondIndex + 1).replaceFirst("to", "").trim();
            Events event = new Events(message.substring(6, firstIndex - 1), fromDate, toDate);
            tasks.add(event);
            return ui.showTaskAdded(event, tasks.size());
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
    public String markTask(String message) throws MinnimNoTaskFoundException, MinnimTargetTaskNumNotFoundException {
        if (message.trim().length() == 4) {
            throw new MinnimTargetTaskNumNotFoundException();
        }
        int taskNum = Integer.parseInt(message.substring(5).trim());
        try {
            Task task = tasks.get(taskNum - 1);
            task.setMarked();
            return ui.showTaskMarked(task);
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
    public String unmarkTask(String message) throws MinnimNoTaskFoundException, MinnimTargetTaskNumNotFoundException {
        if (message.trim().length() == 6) {
            throw new MinnimTargetTaskNumNotFoundException();
        }
        int taskNum = Integer.parseInt(message.substring(7).trim());
        try {
            Task task = tasks.get(taskNum - 1);
            task.setUnmarked();
            return ui.showTaskUnmarked(task);
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
    public String deleteTask(String message) throws MinnimTargetTaskNumNotFoundException, MinnimNoTaskFoundException {
        if (message.trim().length() == 6) {
            throw new MinnimTargetTaskNumNotFoundException();
        }
        int taskNum = Integer.parseInt(message.substring(7).trim());
        try {
            Task task = tasks.remove(taskNum - 1);
            return ui.showTaskDeleted(task, tasks.size());
        } catch (IndexOutOfBoundsException e) {
            throw new MinnimNoTaskFoundException(taskNum);
        }
    }

    /**
     * Lists all tasks in the task list.
     */
    public String listTasks() {
        if (tasks.isEmpty()) {
            return ui.showMessage("Your task list is empty.");
        } else {
            StringBuilder taskListString = new StringBuilder();
            for (int j = 0; j < tasks.size(); j++) {
                taskListString.append((j + 1)).append(". ").append(tasks.get(j).getDescription()).append("\n");
            }
            return taskListString.toString();
        }
    }
}
