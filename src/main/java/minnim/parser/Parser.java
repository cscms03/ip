package minnim.parser;

import minnim.exception.MinnimMissingDateException;
import minnim.exception.MinnimMissingTaskDetailException;
import minnim.exception.MinnimNoTaskFoundException;
import minnim.exception.MinnimTargetTaskNumNotFoundException;
import minnim.task.TaskList;
import minnim.ui.Ui;
import minnim.storage.Storage;

/**
 * Parses and processes user commands by delegating tasks to the appropriate methods
 * in the TaskList, Storage, and Ui components.
 */
public class Parser {
    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    /**
     * Constructs a Parser object that processes user commands and interacts with
     * the task list, user interface, and storage.
     *
     * @param taskList The task list to manage tasks.
     * @param ui       The user interface for displaying messages.
     * @param storage  The storage handler for saving and loading tasks.
     */
    public Parser(TaskList taskList, Ui ui, Storage storage) {
        this.taskList = taskList;
        this.ui = ui;
        this.storage = storage;
    }

    /**
     * Parses and executes the given user command, delegating the appropriate operations
     * to the TaskList methods and handling responses.
     *
     * Based on the command, this method may add tasks, mark/unmark tasks, delete tasks,
     * list all tasks, search for tasks, or exit the program.
     *
     * @param message The user input command.
     * @return The response message generated after executing the command.
     * @throws MinnimMissingTaskDetailException     If a task detail is missing.
     * @throws MinnimMissingDateException          If a date is required but missing.
     * @throws MinnimTargetTaskNumNotFoundException If a task number is missing.
     * @throws MinnimNoTaskFoundException          If the specified task number does not exist.
     */
    public String parseCommand(String message) throws
            MinnimMissingTaskDetailException, MinnimMissingDateException,
            MinnimTargetTaskNumNotFoundException, MinnimNoTaskFoundException {

        assert message != null && !message.trim().isEmpty() : "Command message cannot be null or empty";

        String[] words = message.split(" ", 2);
        String command = words[0];
        String response = "";

        switch (command.toLowerCase()) {
        case "todo":
            response = taskList.addTodo(message);
            break;
        case "deadline":
            response = taskList.addDeadline(message);
            break;
        case "event":
            response = taskList.addEvent(message);
            break;
        case "mark":
            response = taskList.markTask(message);
            break;
        case "unmark":
            response = taskList.unmarkTask(message);
            break;
        case "delete":
            response = taskList.deleteTask(message);
            break;
        case "list":
            response = taskList.listTasks();
            break;
        case "find":
            response = taskList.find(message);
            break;
        case "bye":
            storage.saveTasks(taskList.getTasks());
            response = ui.showGoodbyeMessage();
            System.exit(0);
            break;
        default:
            response = ui.showUnknownCommandMessage();
        }
        assert response != null : "Response cannot be null";
        return response;
    }
}