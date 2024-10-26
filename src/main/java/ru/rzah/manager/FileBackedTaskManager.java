package ru.rzah.manager;

import ru.rzah.manager.history.HistoryManager;
import ru.rzah.manager.mapper.EpicMapper;
import ru.rzah.manager.mapper.SubtaskMapper;
import ru.rzah.manager.mapper.TaskMapper;
import ru.rzah.task.Epic;
import ru.rzah.task.Subtask;
import ru.rzah.task.Task;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {

    private final Path taskFile = Paths.get("tasks.csv");
    private final Path epicFile = Paths.get("epics.csv");
    private final Path subtaskFile = Paths.get("subtasks.csv");

    private final TaskMapper taskMapper = new TaskMapper();
    private final EpicMapper epicMapper = new EpicMapper();
    private final SubtaskMapper subtaskMapper = new SubtaskMapper();

    public FileBackedTaskManager(HistoryManager historyManager) {
        super(historyManager);
    }

    @Override
    public Task createTask(Task task) {
        var createdTask = super.createTask(task);
        save();
        return createdTask;
    }

    public void loadFromFile() {
        try (
                var reader = new FileReader(String.valueOf(taskFile));
                var br = new BufferedReader(reader);) {
            int lineCounter = 0;

            while (br.ready()) {
                lineCounter++;
                String line = br.readLine();

                // Пропускаем первую строку
                if (lineCounter == 1) {
                    continue;
                }

                var task = taskMapper.stringToEntity(line);
                this.taskMap.put(task.getId(), task);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (
                var reader = new FileReader(String.valueOf(epicFile));
                var br = new BufferedReader(reader);) {
            int lineCounter = 0;

            while (br.ready()) {
                lineCounter++;
                String line = br.readLine();

                // Пропускаем первую строку
                if (lineCounter == 1) {
                    continue;
                }

                var epic = epicMapper.stringToEntity(line);
                this.epicMap.put(epic.getId(), epic);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (
                var reader = new FileReader(String.valueOf(subtaskFile));
                var br = new BufferedReader(reader);) {
            int lineCounter = 0;

            while (br.ready()) {
                lineCounter++;
                String line = br.readLine();

                // Пропускаем первую строку
                if (lineCounter == 1) {
                    continue;
                }

                var subtask = subtaskMapper.stringToEntity(line);
                this.subtaskMap.put(subtask.getId(), subtask);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void save() {
        this.saveTasks();
        this.saveEpics();
        this.saveSubtasks();
    }

    private void saveTasks() {
        try (var writer = new FileWriter(String.valueOf(taskFile));
             var br = new BufferedWriter(writer);) {

            br.write("id;type;title;description;status");

            for (Task task : super.getAllTasks()) {
                br.write(System.lineSeparator());
                br.write(taskMapper.entityToString(task));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveEpics() {
        try (var writer = new FileWriter(String.valueOf(epicFile));
             var br = new BufferedWriter(writer);) {

            br.write("id;type;title;description;status;subtaskId");

            for (Epic epic : super.getAllEpics()) {
                br.write(System.lineSeparator());
                br.write(epicMapper.entityToString(epic));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveSubtasks() {
        try (var writer = new FileWriter(String.valueOf(subtaskFile));
             var br = new BufferedWriter(writer);) {

            br.write("id;type;title;description;status;epicId");

            for (Subtask subtask : super.getAllSubtasks()) {
                br.write(System.lineSeparator());
                br.write(subtaskMapper.entityToString(subtask));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Task fromString(String task) {
        return null;
    }


    @Override
    public Task updateTask(Task task) {
        return null;
    }

    @Override
    public void deleteAllTasks() {

    }

    @Override
    public Epic createEpic(Epic epic) {
        return null;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        return null;
    }

    @Override
    public void deleteAllEpic() {

    }

    @Override
    public Subtask createSubtask(Integer epicId, Subtask subtask) {
        return null;
    }


    @Override
    public Subtask updateSubtask(Subtask subtask) {
        return null;
    }

    @Override
    public void deleteAllSubtasks() {

    }
}
