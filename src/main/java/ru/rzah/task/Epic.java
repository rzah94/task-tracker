package ru.rzah.task;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Epic extends Task {
    private List<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description);
    }

    @Override
    public String toString() {
        return "Epic{" +
               "id=" + this.getId() +
               ", title='" + this.getTitle() + '\'' +
               ", description='" + this.getDescription() + '\'' +
               ", status=" + this.getStatus() +
               ", subtasks=" + subtaskIds +
               '}';
    }
}
