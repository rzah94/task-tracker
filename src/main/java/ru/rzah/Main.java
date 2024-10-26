package ru.rzah;

import ru.rzah.manager.FileBackedTaskManager;
import ru.rzah.manager.Managers;
import ru.rzah.manager.mapper.EpicMapper;
import ru.rzah.manager.mapper.SubtaskMapper;
import ru.rzah.manager.mapper.TaskMapper;
import ru.rzah.task.Epic;
import ru.rzah.task.Status;
import ru.rzah.task.Subtask;
import ru.rzah.task.Task;

public class Main {
    public static void main(String[] args) {

        var historyManager = Managers.getDefaultHistory();
        FileBackedTaskManager taskManager = new FileBackedTaskManager(historyManager);

        taskManager.loadFromFile();

        System.out.println(taskManager.getAllTasks());
        System.out.println(taskManager.getAllEpics());

        System.exit(0);

        var testTask1 = taskManager.createTask(new Task("Task-1", "d Task-1"));
        var testTask2 = taskManager.createTask(new Task("Task-2", "d Task-2"));






        System.exit(0);

        var manager = Managers.getDefault();

        var task1 = manager.createTask(new Task("Task-1", "d Task-1"));
        var task2 = manager.createTask(new Task("Task-2", "d Task-2"));

        task2.setStatus(Status.IN_PROGRESS);
        task2 = manager.updateTask(task2);

        System.out.println(manager.getTaskById(task1.getId()));
        System.out.println(manager.getTaskById(task2.getId()));

        manager.getTaskById(task2.getId());
        manager.getTaskById(task2.getId());
        manager.getTaskById(task2.getId());
        manager.getTaskById(task2.getId());
        manager.getTaskById(task2.getId());
        manager.getTaskById(task2.getId());
        manager.getTaskById(task2.getId());
        manager.getTaskById(task2.getId());
        manager.getTaskById(task2.getId());
        manager.getTaskById(task2.getId());
        manager.getTaskById(task2.getId());

        System.out.println(manager.getHistoryManager().getHistory());

        System.exit(1);


        var epic1 = manager.createEpic(new Epic("Epic-1", "d Epic-1"));
        var epic2 = manager.createEpic(new Epic("Epic-2", "d Epic-2"));

        var subtask1 = manager.createSubtask(epic1.getId(),
                new Subtask("Subtask-1", "d Subtask-1"));

        var subtask2 = manager.createSubtask(epic1.getId(),
                new Subtask("Subtask-2", "d Subtask-2"));

        var subtask3 = manager.createSubtask(epic1.getId(),
                new Subtask("Subtask-3", "d Subtask-3"));

        var subtask4 = manager.createSubtask(epic2.getId(),
                new Subtask("Subtask-4", "d Subtask-4"));

        subtask1.setStatus(Status.DONE);
        manager.updateSubtask(subtask1);

        subtask2.setStatus(Status.DONE);
        manager.updateSubtask(subtask2);

        subtask3.setStatus(Status.DONE);
        manager.updateSubtask(subtask3);

        System.out.println(epic1);
        System.out.println(epic2);
        System.out.println(subtask1);
        System.out.println(subtask2);
        System.out.println(subtask3);
        System.out.println(subtask4);
        System.out.println(task1);
        System.out.println(task2);

    }
}
