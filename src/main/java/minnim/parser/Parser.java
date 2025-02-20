package minnim.parser;

import minnim.exception.MinnimMissingDateException;
import minnim.exception.MinnimMissingTaskDetailException;
import minnim.exception.MinnimNoTaskFoundException;
import minnim.exception.MinnimTargetTaskNumNotFoundException;
import minnim.storage.UndoStorage;
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
    private UndoStorage undoStorage;


    /**
     * Constructs a Parser object.
     *
     * @param taskList    The task list to manage tasks.
     * @param ui          The user interface for displaying messages.
     * @param storage     The storage handler for saving and loading tasks.
     * @param undoStorage
     */
    public Parser(TaskList taskList, Ui ui, Storage storage, UndoStorage undoStorage) {
        this.taskList = taskList;
        this.ui = ui;
        this.storage = storage;
        this.undoStorage = undoStorage;
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
    public String parseCommand(String message) throws
            MinnimMissingTaskDetailException, MinnimMissingDateException,
            MinnimTargetTaskNumNotFoundException, MinnimNoTaskFoundException {

        String[] words = message.split(" ", 2);
        String command = words[0];
        String response = "";

        switch (command.toLowerCase()) {
        case "todo":
            this.undoStorage.addRecentTask(message);
            response = taskList.addTodo(message);
            break;
        case "deadline":
            this.undoStorage.addRecentTask(message);
            response = taskList.addDeadline(message);
            break;
        case "event":
            this.undoStorage.addRecentTask(message);
            response = taskList.addEvent(message);
            break;
        case "mark":
            this.undoStorage.addRecentTask(message);
            response = taskList.markTask(message);
            break;
        case "unmark":
            this.undoStorage.addRecentTask(message);
            response = taskList.unmarkTask(message);
            break;
        case "delete":
            this.undoStorage.addRecentTask(message);
            response = taskList.deleteTask(message);
            break;
        case "list":
            response = taskList.listTasks();
            break;
        case "find":
            response = taskList.find(message);
            break;
        case "undo":
            response = taskList.handleUndo();
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