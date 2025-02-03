package minnim.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskTest {
    @Test
    void testTaskDescription() {
        Task task = new Task("Test task");
        assertEquals("[ ] Test task", task.getDescription(), "Task description should match");
    }

    @Test
    void testMarkAsDone() {
        Task task = new Task("Test task");
        task.setMarked();
        assertTrue(task.isDone, "Task should be marked as done");
    }


}
