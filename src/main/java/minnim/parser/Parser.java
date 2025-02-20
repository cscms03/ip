package minnim.parser;

import minnim.exception.MinnimMissingDateException;
import minnim.exception.MinnimMissingTaskDetailException;
import minnim.exception.MinnimNoTaskFoundException;
import minnim.exception.MinnimTargetTaskNumNotFoundException;
import minnim.task.TaskList;
import minnim.ui.Ui;
import minnim.storage.Storage;

//TODO: Update JavaDoc as return value changed from void to String

/**
 * Parses and processes user commands.
 */
public class Parser {
    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    /**
     * Constructs a Parser object.
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
     * Parses the user input command and executes the corresponding action.
     *
     * @param message The full command input by the user.
     * @return A response message as a String indicating the result of the command execution.
     * @throws MinnimMissingTaskDetailException If a task is missing necessary details.
     * @throws MinnimMissingDateException If a deadline or event is missing a date.
     * @throws MinnimTargetTaskNumNotFoundException If the specified task number is invalid.
     * @throws MinnimNoTaskFoundException If the task list is empty when an operation requires tasks.
     */
    public String parseCommand(String message) throws
            MinnimMissingTaskDetailException, MinnimMissingDateException,
            MinnimTargetTaskNumNotFoundException, MinnimNoTaskFoundException {

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
        return response;
    }
}