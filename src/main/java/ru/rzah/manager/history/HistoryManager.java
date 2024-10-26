package ru.rzah.manager.history;

import ru.rzah.task.Task;

import java.util.List;

public interface HistoryManager {
    void addTask(Task task);
    List<Task> getHistory();
    void remove(int id);
}
