package ru.rzah.manager;

import lombok.Getter;
import ru.rzah.manager.history.HistoryManager;
import ru.rzah.manager.history.InMemoryHistoryManager;
import ru.rzah.task.Epic;
import ru.rzah.task.Status;
import ru.rzah.task.Subtask;
import ru.rzah.task.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private static Integer currentTaskId = 0;
    private static Integer currentSubtaskId = 0;
    private static Integer currentEpicId = 0;

    protected final Map<Integer, Task> taskMap = new HashMap<>();
    protected final Map<Integer, Subtask> subtaskMap = new HashMap<>();
    protected final Map<Integer, Epic> epicMap = new HashMap<>();

    @Getter
    private final HistoryManager historyManager;

    public InMemoryTaskManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    // ---- Task (begin) ---
    // Создание Task
    @Override
    public Task createTask(Task task) {
        if (task == null) {
            throw new NullPointerException("task is null");
        }
        task.setId(++currentTaskId);
        taskMap.put(task.getId(), task);
        return task;
    }

    // Получение всех Task
    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(taskMap.values());
    }

    // Удаление всех Task
    @Override
    public void deleteAllTasks() {
        taskMap.clear();
    }

    // Получить Task по Id
    @Override
    public Task getTaskById(int id) {
        var task = taskMap.get(id);
        if (task == null) {
            return null;
        }
        historyManager.addTask(task);
        return task;
    }

    // Обновление Task
    @Override
    public Task updateTask(Task task) {
        if (task == null || task.getId() == null) {
            throw new NullPointerException("task is null");
        }

        var findedTask = taskMap.get(task.getId());

        if (findedTask == null) {
            throw new NullPointerException("task not found");
        }

        taskMap.put(task.getId(), task);
        return task;
    }

    // ---- Task (end) ---
    // ---- Epic (begin) ---

    // Создание Epic
    @Override
    public Epic createEpic(Epic epic) {
        if (epic == null) {
            throw new NullPointerException("Epic is null");
        }

        epic.setId(++currentEpicId);
        epicMap.put(epic.getId(), epic);
        this.updateEpicStatus(epic);
        return epic;
    }

    //Получение всех
    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epicMap.values());
    }

    //Удаление всех
    @Override
    public void deleteAllEpic() {
        epicMap.clear();
    }

    //Получить Epic по Id
    @Override
    public Epic getEpicById(int id) {
        var epic = epicMap.get(id);
        if (epic == null) {
            return null;
        }
        historyManager.addTask(epic);
        return epic;
    }

    // Обновление Epic
    @Override
    public Epic updateEpic(Epic epic) {
        if (epic == null || epic.getId() == null) {
            throw new NullPointerException("epic is null");
        }

        epicMap.put(epic.getId(), epic);
        this.updateEpicStatus(epic);
        return epic;
    }

    // ---- Epic (end) ---
    // ---- subtask (begin) ---

    // Создать subtask
    @Override
    public Subtask createSubtask(Integer epicId, Subtask subtask) {
        if (epicId == null || subtask == null) {
            throw new NullPointerException("epicId / subtask are null");
        }

        var epic = epicMap.get(epicId);

        if (epic == null) {
            throw new IllegalArgumentException("epicId " + epicId + " not found");
        }

        subtask.setId(++currentSubtaskId);
        subtask.setEpicId(epicId);
        subtaskMap.put(subtask.getId(), subtask);

        epic.getSubtaskIds().add(subtask.getId());

        this.updateEpicStatus(epic);

        return subtask;
    }

    // Получение всех subtask
    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtaskMap.values());
    }

    // Удаление всех subtask
    @Override
    public void deleteAllSubtasks() {
        subtaskMap.clear();
    }

    // Получить subtask по Id
    @Override
    public Subtask getSubtaskById(int id) {
        var subtask = subtaskMap.get(id);
        if (subtask == null) {
            return null;
        }
        historyManager.addTask(subtask);
        return subtask;
    }

    // Обновление subtask
    @Override
    public Subtask updateSubtask(Subtask subtask) {
        if (subtask == null || subtask.getId() == null) {
            throw new NullPointerException("epic is null");
        }

        var currrentSubtask = subtaskMap.get(subtask.getId());

        if (currrentSubtask == null) {
            throw new IllegalArgumentException("subtaskId " + subtask.getId() + " not found");
        }

        var currentEpic = epicMap.get(subtask.getEpicId());

        // В subtask не передали Epic - берем от текущего
        if (subtask.getEpicId() == null) {
            subtask.setEpicId(currrentSubtask.getEpicId());
        }

        // Epic переданного subtask отличается от текущего
        if (!Objects.equals(subtask.getEpicId(), currentEpic.getId())) {
            throw new RuntimeException("Нельзя изменять epic у subtasks");
        }

        subtaskMap.put(subtask.getId(), subtask);

        this.updateEpicStatus(currentEpic);
        return subtask;
    }

    // ---- subtask (end) ---

    // Управление статусом epic
    private Epic updateEpicStatus(Epic epic) {
        if (epic == null || epic.getId() == null) {
            throw new NullPointerException("epic is null");
        }

        if (epic.getSubtaskIds().isEmpty()) {
            epic.setStatus(Status.NEW);
            return epic;
        }

        // Собираем статусы subtasks
        Set<Status> subtaskStatuses = this.getSubtaskStatuses(epic.getSubtaskIds());

        // У subtasks несколько разных статусов - ставим для epic статус IN_PROGRESS
        if (subtaskStatuses.size() > 1) {
            epic.setStatus(Status.IN_PROGRESS);
        } else if (subtaskStatuses.contains(Status.NEW)) {
            epic.setStatus(Status.NEW);
        } else if (subtaskStatuses.contains(Status.DONE)) {
            epic.setStatus(Status.DONE);
        }

        return epic;
    }

    private Set<Status> getSubtaskStatuses(List<Integer> subtaskIds) {
        Set<Status> subtaskStatuses = new HashSet<>();

        for (Integer subtaskId : subtaskIds) {
            var subtask = subtaskMap.get(subtaskId);

            if (subtask == null) {
                throw new IllegalArgumentException("subtaskId " + subtaskId + " not found");
            }
            subtaskStatuses.add(subtask.getStatus());
        }

        return subtaskStatuses;
    }
}
