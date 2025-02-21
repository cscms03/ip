package minnim;

import minnim.exception.MinnimMissingDateException;
import minnim.exception.MinnimMissingTaskDetailException;
import minnim.exception.MinnimNoTaskFoundException;
import minnim.exception.MinnimTargetTaskNumNotFoundException;
import minnim.parser.Parser;
import minnim.storage.Storage;
import minnim.storage.UndoStorage;
import minnim.task.TaskList;
import minnim.ui.Ui;

/**
 * Represents the main application for managing tasks as Minnim chatbot.
 * The Minnim class is responsible for initializing the task list, UI, and storage,
 * and for handling the main application loop that processes user commands.
 */
public class Minnim {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;
    private UndoStorage undoStorage;

    /**
     * Initializes the Minnim application with the specified file path for storing tasks.
     * This constructor sets up the user interface, loads tasks from storage, and sets up
     * the parser to interpret user commands.
     *
     * @param filePath the file path where task data is stored
     */
    public Minnim(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty() : "File path cannot be null or empty";

        this.ui = new Ui();
        this.storage = new Storage(filePath, ui);
        this.undoStorage = new UndoStorage();
        this.tasks = new TaskList(storage.loadTasks(), ui, undoStorage);
        this.parser = new Parser(tasks, ui, storage, undoStorage);
    }

    public String getResponse(String message) throws MinnimMissingTaskDetailException, MinnimMissingDateException,
            MinnimTargetTaskNumNotFoundException, MinnimNoTaskFoundException {
        return parser.parseCommand(message);
    }
}