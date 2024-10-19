package ru.rzah.manager;

import ru.rzah.manager.history.HistoryManager;
import ru.rzah.manager.history.InMemoryHistoryManager;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager(Managers.getDefaultHistory());
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
