package ru.rzah.manager.history;

import ru.rzah.task.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> taskHistory = new LinkedList<>();

    @Override
    public void addTask(Task task) {
        if (this.taskHistory.size() == 10) {
            this.taskHistory.removeFirst();
        }
        this.taskHistory.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(this.taskHistory);
    }
}
