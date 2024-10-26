package ru.rzah.manager.mapper;

import ru.rzah.task.Epic;
import ru.rzah.task.Status;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EpicMapper {
    public String entityToString(Epic epic) {
        String[] data = {String.valueOf(epic.getId()), "EPIC", epic.getTitle(), epic.getDescription(),
                String.valueOf(epic.getStatus()), String.valueOf(epic.getSubtaskIds())};
        return String.join(";", data);
    }

    public Epic stringToEntity(String epicString) {
        String[] data = epicString.split(";");

        Epic epic = new Epic(data[2], data[3]);
        if (! data[0].equals("null")) {
            epic.setId(Integer.parseInt(data[0]));
        }
        epic.setStatus(Status.valueOf(data[4]));
        if (! data[5].equals("[]"))
        epic.setSubtaskIds(Arrays.stream(data[5].split(","))
                .map(Integer::parseInt)
                .toList());

        return epic;
    }
}
