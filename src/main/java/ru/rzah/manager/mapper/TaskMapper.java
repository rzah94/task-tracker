package ru.rzah.manager.mapper;

import ru.rzah.task.Status;
import ru.rzah.task.Task;

public class TaskMapper {
    public String entityToString(Task task) {
        String[] data = {String.valueOf(task.getId()), "TASK", task.getTitle(), task.getDescription(),
                String.valueOf(task.getStatus())};
        return String.join(";", data);
    }

    public Task stringToEntity(String taskString) {
        String[] data = taskString.split(";");

        var task = new Task(data[2], data[3]);
        if (! data[0].equals("null")) {
            task.setId(Integer.parseInt(data[0]));
        }
        task.setStatus(Status.valueOf(data[4]));
        return task;
    }
}
