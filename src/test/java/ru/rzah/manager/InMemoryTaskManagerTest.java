package ru.rzah.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.rzah.manager.history.InMemoryHistoryManager;
import ru.rzah.task.Task;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    InMemoryHistoryManager historyManager;
    InMemoryTaskManager taskManager;
    Task task1;
    Task task2;

    @BeforeEach
    void beforeEach() {
        this.historyManager = new InMemoryHistoryManager();
        this.taskManager = new InMemoryTaskManager(this.historyManager);
        this.task1 = new Task("Task-1", "d Task-1");
        this.task2 = new Task("Task-2", "d Task-2");
    }

    @Test
    void createTask() {
        var savedTask = taskManager.createTask(this.task1);

        assertNotNull(savedTask, "Задача не найдена");

        var findedTask = taskManager.getTaskById(savedTask.getId());

        var allTasks = taskManager.getAllTasks();

        assertEquals(savedTask, findedTask, "Задача не совпадает с сохраненной");
        assertEquals(1, allTasks.size(), "Количество задач не совпадает");
    }

    @Test
    void getAllTasks() {
        var savedTask1 = taskManager.createTask(this.task1);
        var savedTask2 = taskManager.createTask(this.task2);
        var allTasks = taskManager.getAllTasks();

        assertEquals(2, allTasks.size(), "Количество задач не совпадает");
        assertEquals(savedTask1, allTasks.get(allTasks.indexOf(savedTask1)), "Сохраненной задачи нет в общем списке задач");
        assertEquals(savedTask2, allTasks.get(allTasks.indexOf(savedTask2)), "Сохраненной задачи нет в общем списке задач");
    }

    @Test
    void deleteAllTasks() {
        var savedTask1 = taskManager.createTask(this.task1);
        var savedTask2 = taskManager.createTask(this.task2);
        taskManager.deleteAllTasks();
        var allTasks = taskManager.getAllTasks();

        assertEquals(0, allTasks.size(), "Количество задач не совпадает");
    }

    @Test
    void updateTask() {
        var savedTask = taskManager.createTask(this.task1);
        task1.setTitle("updated title");
        task1.setDescription("updated description");
        task1.setId(savedTask.getId());

        var savedTask2 = taskManager.updateTask(savedTask);

        assertEquals(task1, savedTask2, "Задачи не совпадают");

        var exceptionTaskIsNull1 = assertThrows(NullPointerException.class, () -> {
            taskManager.updateTask(this.task2);
        });
        assertEquals("task is null", exceptionTaskIsNull1.getMessage(), "Исключение не совпадает");

        var exceptionTaskIsNull2 = assertThrows(NullPointerException.class, () -> {
            taskManager.updateTask(null);
        });
        assertEquals("task is null", exceptionTaskIsNull2.getMessage(), "Исключение не совпадает");

        this.task2.setId(10);
        var exceptionTaskNotFound = assertThrows(NullPointerException.class, () -> {
            taskManager.updateTask(this.task2);
        });

        assertEquals("task not found", exceptionTaskNotFound.getMessage(), "Исключение не совпадает");
    }
}