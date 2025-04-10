package com.example.tasks;

import com.example.manager.TaskManager;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private final List<Integer> subtasksIds;
    private final TaskManager manager;

    public Epic(String title, String description, TaskManager manager) {
        super(title, description);
        this.subtasksIds = new ArrayList<>();
        this.manager = manager;
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

    public Status calculateStatus() {
        if (subtasksIds.isEmpty()) {
            return Status.NEW;
        }

        boolean allDone = true;
        boolean anyInProgress = false;

        for (Integer subtaskId : subtasksIds) {
            Task subtask = manager.findTaskById(subtaskId);
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
