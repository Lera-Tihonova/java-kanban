package src.main;

import com.example.tasks.Task;
import com.example.tasks.Epic;
import com.example.tasks.Subtask;
import com.example.tasks.Status;
import com.example.manager.TaskManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        Task task1 = new Task("Переезд", "Организовать переезд");
        Task task2 = new Task("Покупка квартиры", "Найти и купить новую квартиру");

        Epic epic1 = new Epic("Семейный праздник", "Организация семейного праздника");
        Subtask subtask1 = new Subtask("Купить подарки", "Купить подарки для друзей", epic1.getId());
        Subtask subtask2 = new Subtask("Приготовить еду", "Приготовить еду для праздника", epic1.getId());

        manager.createTask(task1);
        manager.createTask(task2);
        manager.createEpic(epic1);
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);

        System.out.println("Все задачи:");
        List<Task> allTasks = manager.getAllTasks();
        allTasks.forEach(System.out::println);

        System.out.println("\nВсе эпики:");
        List<Epic> allEpics = manager.getAllEpics();
        allEpics.forEach(System.out::println);

        System.out.println("\nВсе подзадачи:");
        List<Subtask> allSubtasks = manager.getAllSubtasks();
        allSubtasks.forEach(System.out::println);

        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setStatus(Status.DONE);

        System.out.println("\nОбновленные данные:");
        System.out.println("Эпик: " + epic1);
        System.out.println("Подзадача 1: " + subtask1);
        System.out.println("Подзадача 2: " + subtask2);

        manager.deleteTask(task1.getId());
        manager.deleteEpic(epic1.getId());

        System.out.println("\nОставшиеся задачи:");
        List<Task> remainingTasks = manager.getAllTasks();
        remainingTasks.forEach(System.out::println);
    }
}