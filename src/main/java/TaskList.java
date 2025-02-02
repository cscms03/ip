import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;
    private Ui ui;

    public TaskList(ArrayList<Task> tasks, Ui ui) {
        this.tasks = tasks;
        this.ui = ui;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTodo(String message) {
        Todo todo = new Todo(message.substring(5));
        tasks.add(todo);
        ui.showTaskAdded(todo, tasks.size());
    }

    public void addDeadline(String message) {
        int index = message.indexOf("/");
        String date = message.substring(index + 1).replaceFirst("by", "").trim();
        Deadline deadline = new Deadline(message.substring(9, index - 1), date);
        tasks.add(deadline);
        ui.showTaskAdded(deadline, tasks.size());
    }

    public void addEvent(String message) {
        int firstIndex = message.indexOf("/");
        int secondIndex = message.indexOf("/", firstIndex + 1);
        String fromDate = message.substring(firstIndex + 1, secondIndex - 1).replaceFirst("from", "").trim();
        String toDate = message.substring(secondIndex + 1).replaceFirst("to", "").trim();
        Events event = new Events(message.substring(6, firstIndex - 1), fromDate, toDate);
        tasks.add(event);
        ui.showTaskAdded(event, tasks.size());
    }

    public void markTask(String message) {
        int taskNum = Integer.parseInt(message.substring(5).trim());
        Task task = tasks.get(taskNum - 1);
        task.setMarked();
        ui.showTaskMarked(task);
    }

    public void unmarkTask(String message) {
        int taskNum = Integer.parseInt(message.substring(7).trim());
        Task task = tasks.get(taskNum - 1);
        task.setUnmarked();
        ui.showTaskUnmarked(task);
    }

    public void deleteTask(String message) {
        int taskNum = Integer.parseInt(message.substring(7).trim());
        Task task = tasks.remove(taskNum - 1);
        ui.showTaskDeleted(task, tasks.size());
    }

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
