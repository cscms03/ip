package minnim.storage;

import java.util.Stack;

import minnim.task.Task;

public class UndoStorage {
    private Stack<String> recentTasks = new Stack<>();
    private Stack<Task> deletedTasks = new Stack<>();
    private static final int MAX_UNDO = 1;

    public void addRecentTask(String message) {
        if (recentTasks.size() == MAX_UNDO) {
            recentTasks.remove(0); // Maintain size limit
        }
        recentTasks.push(message);
    }

    public void storeDeletedTask(Task task) {
        if (deletedTasks.size() == MAX_UNDO) {
            deletedTasks.remove(0); // Maintain size limit
        }
        deletedTasks.push(task);
    }

    public Task getDeletedTask() {
        return deletedTasks.isEmpty() ? null : deletedTasks.pop();
    }

    public String getRecentTask() {
        return recentTasks.isEmpty() ? null : recentTasks.pop();
    }
}
