package ru.rzah.task;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.rzah.Status;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Task {
    private Integer id;
    private String title;
    private String description;
    private Status status;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = Status.NEW;
    }

    @Override
    public String toString() {
        return "Task{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", description='" + description + '\'' +
               ", status=" + status +
               '}';
    }
}
