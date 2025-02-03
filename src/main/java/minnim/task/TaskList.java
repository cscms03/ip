package minnim.task;

import java.util.ArrayList;

import minnim.exception.MinnimMissingDateException;
import minnim.exception.MinnimMissingTaskDetailException;
import minnim.exception.MinnimNoTaskFoundException;
import minnim.exception.MinnimTargetTaskNumNotFoundException;
import minnim.ui.Ui;

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

    public void addTodo(String message) throws MinnimMissingTaskDetailException {
        try {
            Todo todo = new Todo(message.substring(5));
            tasks.add(todo);
            ui.showTaskAdded(todo, tasks.size());
        } catch (StringIndexOutOfBoundsException e) {
            throw new MinnimMissingTaskDetailException();
        }
    }

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
