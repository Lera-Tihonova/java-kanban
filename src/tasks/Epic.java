package com.example.tasks;
import com.example.manager.TaskManager;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtasksIds;

    public Epic(String title, String description) {
        super(title, description);
        this.subtasksIds = new ArrayList<>();
    }

    public List<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    public void addSubtaskId(int subtaskId) {
        subtasksIds.add(subtaskId);
    }

    public void removeSubtaskId(int subtaskId) {
        subtasksIds.remove((Integer) subtaskId);
    }

    public Status calculateStatus(List<Subtask> subtasks) {
        if (subtasksIds.isEmpty()) {
            return Status.NEW;
        }

        boolean allDone = true;
        boolean anyInProgress = false;

        for (int subtaskId : subtasksIds) {
            Subtask subtask = subtasks.stream()
                    .filter(st -> st.getId() == subtaskId)
                    .findFirst()
                    .orElse(null);

            if (subtask != null && subtask.getStatus() == Status.IN_PROGRESS) {
                anyInProgress = true;
            }

            if (subtask != null && subtask.getStatus() != Status.DONE) {
                allDone = false;
            }
        }

        if (allDone) {
            return Status.DONE;
        } else if (anyInProgress) {
            return Status.IN_PROGRESS;
        } else {
            return Status.NEW;
        }
    }

    public void updateStatus(TaskManager manager) {
        List<Subtask> subtasks = manager.getSubtasksOfEpic(getId());
        setStatus(calculateStatus(subtasks));
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + getStatus() +
                ", subtasksIds=" + subtasksIds +
                '}';
    }
}