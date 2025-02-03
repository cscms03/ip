package minnim.parser;

import minnim.exception.MinnimMissingDateException;
import minnim.exception.MinnimMissingTaskDetailException;
import minnim.exception.MinnimNoTaskFoundException;
import minnim.exception.MinnimTargetTaskNumNotFoundException;
import minnim.task.TaskList;
import minnim.ui.Ui;
import minnim.storage.Storage;

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
     * Parses and executes the given command.
     *
     * @param message The user input command.
     * @throws MinnimMissingTaskDetailException     If a task detail is missing.
     * @throws MinnimMissingDateException          If a date is required but missing.
     * @throws MinnimTargetTaskNumNotFoundException If a task number is missing.
     * @throws MinnimNoTaskFoundException          If the specified task number does not exist.
     */
    public void parseCommand(String message) throws
            MinnimMissingTaskDetailException, MinnimMissingDateException,
            MinnimTargetTaskNumNotFoundException, MinnimNoTaskFoundException {
        String[] words = message.split(" ", 2);
        String command = words[0];

        switch (command.toLowerCase()) {
            case "todo":
                taskList.addTodo(message);
                break;
            case "deadline":
                taskList.addDeadline(message);
                break;
            case "event":
                taskList.addEvent(message);
                break;
            case "mark":
                taskList.markTask(message);
                break;
            case "unmark":
                taskList.unmarkTask(message);
                break;
            case "delete":
                taskList.deleteTask(message);
                break;
            case "list":
                taskList.listTasks();
                break;
            case "find":
                taskList.find(message);
                break;
            case "bye":
                storage.saveTasks(taskList.getTasks());
                ui.showGoodbyeMessage();
                System.exit(0);
            default:
                ui.showUnknownCommandMessage();
        }
    }
}