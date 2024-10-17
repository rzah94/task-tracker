package ru.rzah.task;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Subtask extends Task {
    private Integer epicId;

    public Subtask(String title, String description) {
        super(title, description);
    }

    @Override
    public String toString() {
        return "Subtask{" +
               "id=" + this.getId() +
               ", title='" + this.getTitle() + '\'' +
               ", description='" + this.getDescription() + '\'' +
               ", status=" + this.getStatus() +
               ", epic=" + epicId +
               '}';
    }
}
