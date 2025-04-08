package com.example.tasks;

import java.util.*;

public class TaskManager {
    private static TaskManager instance;
    private Map<Integer, Task> tasks;
    private Map<Integer, Epic> epics;
    private Map<Integer, Subtask> subtasks;
    private int lastId;

    private TaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        lastId = 0;
    }

    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public int getNextId() {
        return ++lastId;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public Task findTaskById(int id) {
        return tasks.get(id);
    }

    public Epic findEpicById(int id) {
        return epics.get(id);
    }

    public Subtask findSubtaskById(int id) {
        return subtasks.get(id);
    }

    public void createTask(Task task) {
        task.setId(getNextId());
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epic.setId(getNextId());
        epics.put(epic.getId(), epic);
    }

    public void createSubtask(Subtask subtask) {
        subtask.setId(getNextId());
        subtasks.put(subtask.getId(), subtask);
    }

    public void deleteTask(int id) {
        Task task = tasks.remove(id);
        if (task instanceof Epic) {
            ((Epic) task).getSubtasksIds().forEach(this::deleteSubtask);
        }
        if (task instanceof Subtask) {
            Epic epic = findEpicById(((Subtask) task).getEpicId());
            if (epic != null) {
                epic.removeSubtaskId(id);
            }
        }
    }

    public void deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            epic.getSubtasksIds().forEach(this::deleteSubtask);
        }
    }

    public void deleteSubtask(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = findEpicById(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtaskId(id);
            }
        }
    }

    public void updateTask(Task updatedTask) {
        tasks.replace(updatedTask.getId(), updatedTask);
    }

    public void updateEpic(Epic updatedEpic) {
        epics.replace(updatedEpic.getId(), updatedEpic);
    }

    public void updateSubtask(Subtask updatedSubtask) {
        subtasks.replace(updatedSubtask.getId(), updatedSubtask);
    }

    public List<Subtask> getSubtasksOfEpic(int epicId) {
        List<Subtask> result = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epicId) {
                result.add(subtask);
            }
        }
        return result;
    }
}