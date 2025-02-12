package minnim;

import minnim.exception.MinnimMissingDateException;
import minnim.exception.MinnimMissingTaskDetailException;
import minnim.exception.MinnimNoTaskFoundException;
import minnim.exception.MinnimTargetTaskNumNotFoundException;
import minnim.parser.Parser;
import minnim.storage.Storage;
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

    /**
     * Initializes the Minnim application with the specified file path for storing tasks.
     * This constructor sets up the user interface, loads tasks from storage, and sets up
     * the parser to interpret user commands.
     *
     * @param filePath the file path where task data is stored
     */
    public Minnim(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath, ui);
        this.tasks = new TaskList(storage.loadTasks(), ui);
        this.parser = new Parser(tasks, ui, storage);

        ui.showMessage("Below is the current task list:");
        tasks.listTasks();
    }

    /**
     * Runs the Minnim application, processing user input and executing commands.
     * The method continuously accepts user input from the console, passes the input to the parser,
     * and executes corresponding actions until the program is terminated.
     *
     *
     * @throws MinnimMissingTaskDetailException if a task detail is missing during command parsing
     * @throws MinnimMissingDateException if a date is missing during command parsing
     * @throws MinnimTargetTaskNumNotFoundException if the task number is not found during marking or deleting
     * @throws MinnimNoTaskFoundException if no task is found with the specified task number
     */
    public void run() throws MinnimMissingTaskDetailException, MinnimMissingDateException,
            MinnimTargetTaskNumNotFoundException, MinnimNoTaskFoundException {
        ui.showWelcomeMessage();
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            System.out.println(parser.parseCommand(input));
        }
    }

    public String getResponse(String message) throws MinnimMissingTaskDetailException, MinnimMissingDateException,
            MinnimTargetTaskNumNotFoundException, MinnimNoTaskFoundException {
        return parser.parseCommand(message);
    }

    public static void main(String[] args) throws MinnimMissingTaskDetailException, MinnimMissingDateException,
            MinnimTargetTaskNumNotFoundException, MinnimNoTaskFoundException {
        new Minnim("data/minnim.Minnim.txt").run();
    }
}