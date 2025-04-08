package com.example.tasks;

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
        subtasksIds.removeIf(id -> id == subtaskId);
    }

    @Override
    public void setStatus(Status status) {
    }

    public Status calculateStatus() {
        if (subtasksIds.isEmpty()) {
            return Status.NEW;
        }

        boolean allDone = true;
        boolean anyInProgress = false;

        for (int subtaskId : subtasksIds) {
            Task subtask = TaskManager.getInstance().findTaskById(subtaskId);
            if (subtask.getStatus() == Status.IN_PROGRESS) {
                anyInProgress = true;
            }
            if (subtask.getStatus() != Status.DONE) {
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

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + calculateStatus() +
                ", subtasksIds=" + subtasksIds +
                '}';
    }
}