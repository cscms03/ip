public class Minnim {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    public Minnim(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath, ui);
        this.tasks = new TaskList(storage.loadTasks(), ui);
        this.parser = new Parser(tasks, ui, storage);

        ui.showMessage("Below is the current task list:");
        tasks.listTasks();
    }

    public void run() {
        ui.showWelcomeMessage();
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            parser.parseCommand(input);
        }
    }

    public static void main(String[] args) {
        new Minnim("data/Minnim.txt").run();
    }
}