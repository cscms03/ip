package minnim.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    private Ui uiMock;
    private UndoStorage undoStorageMock;

    @BeforeEach
    void setUp() {
        uiMock = mock(Ui.class);
        undoStorageMock = mock(UndoStorage.class);
        taskList = new TaskList(new ArrayList<>(), uiMock, undoStorageMock);
    }

    @Test
    void addTodo_validInput_taskAdded() throws MinnimMissingTaskDetailException {
        String result = taskList.addTodo("todo Buy milk");
        assertEquals(1, taskList.getTasks().size());
        assertInstanceOf(Todo.class, taskList.getTasks().get(0));
        assertEquals("[T][ ] Buy milk", taskList.getTasks().get(0).getDescription());
    }

    @Test
    void addTodo_missingDescription_throwsException() {
        String result = taskList.addTodo("todo");
        verify(uiMock).showError(anyString()); // Ensure the UI shows an error message
    }

    @Test
    void find_existingTask_returnsMatchingTask() throws MinnimMissingTaskDetailException {
        taskList.addTodo("todo Read book");
        String result = taskList.find("find book");
        assertTrue(result.contains("Read book"));
    }

    @Test
    void markTask_validTask_marksSuccessfully() throws MinnimNoTaskFoundException, MinnimTargetTaskNumNotFoundException, MinnimMissingTaskDetailException {
        taskList.addTodo("todo Finish assignment");
        String result = taskList.markTask("mark 1");
        assertTrue(taskList.getTasks().get(0).isDone);
    }

    @Test
    void unmarkTask_validTask_unmarksSuccessfully() throws MinnimNoTaskFoundException, MinnimTargetTaskNumNotFoundException, MinnimMissingTaskDetailException {
        taskList.addTodo("todo Finish assignment");
        taskList.markTask("mark 1"); // First mark the task
        String result = taskList.unmarkTask("unmark 1");
        assertFalse(taskList.getTasks().get(0).isDone);
    }

    @Test
    void deleteTask_validTask_deletesSuccessfully() throws MinnimTargetTaskNumNotFoundException, MinnimNoTaskFoundException, MinnimMissingTaskDetailException {
        taskList.addTodo("todo Write report");
        assertEquals(1, taskList.getTasks().size());

        String result = taskList.deleteTask("delete 1");
        assertEquals(0, taskList.getTasks().size());
        verify(undoStorageMock).storeDeletedTask(any(Task.class)); // Ensure the deleted task is stored for undo
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