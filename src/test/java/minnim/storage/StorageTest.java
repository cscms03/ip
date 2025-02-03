package minnim.storage;

import minnim.task.Deadline;
import minnim.task.Events;
import minnim.task.Todo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import minnim.task.Task;
import minnim.ui.Ui;

public class StorageTest {
    @Test
    void testLoadTasks() {
        Storage storage = new Storage("test_tasks.txt", new Ui());
        ArrayList<Task> tasks = storage.loadTasks();
        assertNotNull(tasks, "Loaded tasks should not be null");

    }

    @Test
    void testSaveTasks() {
        Storage storage = new Storage("test_tasks.txt", new Ui());
        Task deadlineTask = new Deadline("Test deadline task", "2025-02-03");
        Task eventTask = new Events("Test event task", "2025-12-12", "2025-12-13");
        Task todoTask = new Todo("Test todo task");
        ArrayList<Task> tasks = new ArrayList<>();

        tasks.add(deadlineTask);
        tasks.add(eventTask);
        tasks.add(todoTask);

        storage.saveTasks(tasks);

        ArrayList<Task> loadedTasks = storage.loadTasks();
        assertEquals(3, loadedTasks.size(), "There should be three tasks loaded");
        assertEquals("[D][ ] Test deadline task (by: Feb 03 2025)",
                loadedTasks.get(0).getDescription(), "Task description should match");
        assertEquals("[E][ ] Test event task (from: Dec 12 2025 to: Dec 13 2025)",
                loadedTasks.get(1).getDescription(), "Task description should match");
        assertEquals("[T][ ] Test todo task",
                loadedTasks.get(2).getDescription(), "Task description should match");
    }


}
