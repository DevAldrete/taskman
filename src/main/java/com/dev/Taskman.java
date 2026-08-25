package com.dev;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Taskman {
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void addTask(LinkedList<Task> tasks, Scanner scanner) {
        System.out.println("\nAdding Task...");
        System.out.println("What's the title of your task? ");
        String title = scanner.nextLine();

        System.out.println("What's the department? ");
        String department = scanner.nextLine();

        System.out.println("What's the due date? ");
        String due_date = scanner.nextLine();
        LocalDate localDate = LocalDate.parse(due_date, dateFormatter);
        ZoneId zone = ZoneId.of("America/Mexico_City");
        Instant utcInstant = localDate.atStartOfDay(zone).toInstant();
        String needUrgencyStr = "";
        do {
            System.out.println("Do you wish to assign an urgency level? (y/n) ");
            needUrgencyStr = scanner.nextLine().toLowerCase();
        } while (!Objects.equals(needUrgencyStr, "y") && !Objects.equals(needUrgencyStr, "n"));

        Task newTask;

        if (Objects.equals(needUrgencyStr, "n")) {
            System.out.println("Task added succesfully");
            newTask = new Task(title, department, utcInstant);
            tasks.add(newTask);
            return;
        }

        System.out.println("What's your urgency level for this task? (asap, high, medium, low) ");
        String urgencyStr = scanner.nextLine().toLowerCase();
        Urgency urgency = Urgency.fromStr(urgencyStr);

        newTask = new Task(title, department, urgency, utcInstant);

        tasks.add(newTask);
        System.out.println("Task added succesfully");
    }

    public static void removeTask(LinkedList<Task> tasks, Scanner scanner, String title) {}

    public static void findTask(LinkedList<Task> tasks, Scanner scanner, String title) {}

    public static void printAllTasks(LinkedList<Task> tasks) {}

    public static void printPendingTasks(LinkedList<Task> tasks) {}

    public static void printPendingTasksByDepartment(LinkedList<Task> tasks) {}

    public static void printPendingTasksByUrgency(LinkedList<Task> tasks) {}

    private static List<Task> sortTasksByUrgency(LinkedList<Task> tasks) {}

    private static List<Task> sortTasksByDepartment(LinkedList<Task> tasks) {}
}
