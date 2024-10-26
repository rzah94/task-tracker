package ru.rzah.manager.mapper;

import ru.rzah.task.Status;
import ru.rzah.task.Subtask;

public class SubtaskMapper {

    public String entityToString(Subtask subtask) {
        String[] data = {String.valueOf(subtask.getId()), "SUBTASK", subtask.getTitle(), subtask.getDescription(),
                String.valueOf(subtask.getStatus()), String.valueOf(subtask.getEpicId())};
        return String.join(";", data);
    }

    public Subtask stringToEntity(String subtaskString) {
        String[] data = subtaskString.split(";");

        var subtask = new Subtask(data[2], data[3]);
        if (! data[0].equals("null")) {
            subtask.setId(Integer.parseInt(data[0]));
        }
        subtask.setStatus(Status.valueOf(data[4]));
        if (! data[5].equals("null")) {
            subtask.setEpicId(Integer.parseInt(data[5]));
        }

        return subtask;
    }
}
