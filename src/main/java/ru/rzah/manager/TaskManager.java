package ru.rzah.manager;

import ru.rzah.manager.history.HistoryManager;
import ru.rzah.task.Epic;
import ru.rzah.task.Subtask;
import ru.rzah.task.Task;

import java.util.List;

public interface TaskManager {
    Task createTask(Task task);
    Task getTaskById(int id);
    List<Task> getAllTasks();
    Task updateTask(Task task);
    void deleteAllTasks();

    Epic createEpic(Epic epic);
    Epic getEpicById(int id);
    List<Epic> getAllEpics();
    Epic updateEpic(Epic epic);
    void deleteAllEpic();

    Subtask createSubtask(Integer epicId, Subtask subtask);
    Subtask getSubtaskById(int id);
    List<Subtask> getAllSubtasks();
    Subtask updateSubtask(Subtask subtask);
    void deleteAllSubtasks();

    HistoryManager getHistoryManager();
}
