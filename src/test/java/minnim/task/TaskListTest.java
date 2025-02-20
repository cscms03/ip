package minnim.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import minnim.exception.MinnimMissingDateException;
import minnim.exception.MinnimMissingTaskDetailException;
import minnim.exception.MinnimNoTaskFoundException;
import minnim.exception.MinnimTargetTaskNumNotFoundException;

import minnim.storage.UndoStorage;
import minnim.ui.Ui;

public class TaskListTest {

    private TaskList taskList;
    private Ui ui;
    private ArrayList<Task> tasks;

    @BeforeEach
    void setUp() {
        // Create mock Ui and initialize the TaskList
        ui = mock(Ui.class);
        tasks = new ArrayList<>();
        taskList = new TaskList(tasks, ui, new UndoStorage());
    }

    @Test
    void testAddTodo() throws MinnimMissingTaskDetailException {
        String message = "todo buy groceries";
        taskList.addTodo(message);
        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0) instanceof Todo);
        verify(ui).showTaskAdded(tasks.get(0), tasks.size());
    }

    @Test
    void testAddTodoMissingDetails() {
        String message = "todo";
        assertThrows(MinnimMissingTaskDetailException.class, () -> taskList.addTodo(message));
    }

    @Test
    void testAddDeadline() throws MinnimMissingTaskDetailException, MinnimMissingDateException {
        String message = "deadline complete project /by 2025-02-10";
        taskList.addDeadline(message);
        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0) instanceof Deadline);
        verify(ui).showTaskAdded(tasks.get(0), tasks.size());
    }

    @Test
    void testAddDeadlineMissingDate() {
        String message = "deadline complete project";
        assertThrows(MinnimMissingDateException.class, () -> taskList.addDeadline(message));
    }

    @Test
    void testAddEvent() throws MinnimMissingTaskDetailException, MinnimMissingDateException {
        String message = "event meeting /from 2025-02-01 /to 2025-02-02";
        taskList.addEvent(message);
        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0) instanceof Events);
        verify(ui).showTaskAdded(tasks.get(0), tasks.size());
    }

    @Test
    void testAddEventMissingDate() {
        String message = "event meeting";
        assertThrows(MinnimMissingDateException.class, () -> taskList.addEvent(message));
    }

    @Test
    void testMarkTask() throws MinnimNoTaskFoundException, MinnimTargetTaskNumNotFoundException {
        String message = "mark 1";
        Todo todo = new Todo("buy groceries");
        tasks.add(todo);
        taskList.markTask(message);
        assertTrue(todo.isDone);
        verify(ui).showTaskMarked(todo);
    }

    @Test
    void testMarkTaskNotFound() {
        String message = "mark 1";
        assertThrows(MinnimNoTaskFoundException.class, () -> taskList.markTask(message));
    }

    @Test
    void testUnmarkTask() throws MinnimNoTaskFoundException, MinnimTargetTaskNumNotFoundException {
        String message = "unmark 1";
        Todo todo = new Todo("buy groceries");
        todo.setMarked();
        tasks.add(todo);
        taskList.unmarkTask(message);
        assertFalse(todo.isDone);
        verify(ui).showTaskUnmarked(todo);
    }

    @Test
    void testUnmarkTaskNotFound() {
        String message = "unmark 1";
        assertThrows(MinnimNoTaskFoundException.class, () -> taskList.unmarkTask(message));
    }

    @Test
    void testDeleteTask() throws MinnimTargetTaskNumNotFoundException, MinnimNoTaskFoundException {
        String message = "delete 1";
        Todo todo = new Todo("buy groceries");
        tasks.add(todo);
        taskList.deleteTask(message);
        assertTrue(tasks.isEmpty());
        verify(ui).showTaskDeleted(todo, tasks.size());
    }

    @Test
    void testDeleteTaskNotFound() {
        String message = "delete 1";
        assertThrows(MinnimNoTaskFoundException.class, () -> taskList.deleteTask(message));
    }

    @Test
    void testListTasks() {
        Todo todo = new Todo("buy groceries");
        tasks.add(todo);
        taskList.listTasks();
        verify(ui).showMessage("1. [T][ ] buy groceries");
    }

    @Test
    void testListTasksEmpty() {
        taskList.listTasks();
        verify(ui).showMessage("Your task list is empty.");
    }

    @Test
    public void testTaskOperations_interactions() throws MinnimMissingTaskDetailException, MinnimNoTaskFoundException,
            MinnimTargetTaskNumNotFoundException, MinnimMissingDateException {
        taskList.addTodo("todo test task");
        taskList.addDeadline("deadline test task /by 2025-12-31");

        assertEquals(2, taskList.getTasks().size());

        taskList.markTask("mark 1");
        Task markedTask = taskList.getTasks().get(0);
        assertTrue(markedTask.isDone);

        taskList.deleteTask("delete 1");
        assertEquals(1, taskList.getTasks().size());  // One task should be deleted
    }
}