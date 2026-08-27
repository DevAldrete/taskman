package com.dev;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class Taskman {
    private static final ZoneId ZONE = ZoneId.of("America/Mexico_City");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ---------- General tasks ----------

    public static void addTask(LinkedList<Task> tasks, Scanner scanner) {
        System.out.println("\nAdding task...");
        tasks.add(readTask(scanner));
        System.out.println("Task added successfully.");
    }

    public static void removeTask(LinkedList<Task> tasks, Scanner scanner, String title) {
        Task task = findTask(tasks, title);
        if (task == null) {
            System.out.println("No task with title \"" + title + "\" was found.");
            return;
        }

        tasks.delete(task);
        System.out.println("Task \"" + title + "\" removed successfully.");
    }

    public static void findTask(LinkedList<Task> tasks, Scanner scanner, String title) {
        Task task = findTask(tasks, title);
        if (task == null) {
            System.out.println("No task with title \"" + title + "\" was found.");
            return;
        }

        System.out.println("\nTask found:");
        printTask(task);
    }

    public static Task findTask(LinkedList<Task> tasks, String title) {
        for (Task task : tasks) {
            if (Objects.equals(task.getTitle(), title)) {
                return task;
            }
        }
        return null;
    }

    public static void markTaskDone(LinkedList<Task> tasks, Scanner scanner, String title) {
        Task task = findTask(tasks, title);
        if (task == null) {
            System.out.println("No task with title \"" + title + "\" was found.");
            return;
        }

        task.setDone(true);
        task.setUpdated_at(Instant.now());
        System.out.println("Task \"" + title + "\" marked as done.");
    }

    public static void printAllTasks(LinkedList<Task> tasks) {
        printTasks(toList(tasks), "All tasks");
    }

    public static void printPendingTasks(LinkedList<Task> tasks) {
        printTasks(pendingTasks(tasks), "Pending tasks");
    }

    public static void printPendingTasksByUrgency(LinkedList<Task> tasks) {
        printTasks(sortTasksByUrgency(pendingTasks(tasks)), "Pending tasks by urgency");
    }

    public static void printPendingTasksByDepartment(LinkedList<Task> tasks) {
        printTasks(sortTasksByDepartment(pendingTasks(tasks)), "Pending tasks by department");
    }

    // ---------- Scheduled tasks (queue, FIFO) ----------

    public static void scheduleTask(Queue<Task> scheduled, Scanner scanner) {
        System.out.println("\nScheduling task for the day...");
        scheduled.enqueue(readTask(scanner));
        System.out.println("Task scheduled successfully.");
    }

    public static void printScheduledTasks(Queue<Task> scheduled) {
        printTasks(reversed(scheduled), "Scheduled tasks (in the order you will complete them)");
    }

    public static void completeNextScheduled(Queue<Task> scheduled) {
        if (scheduled.isEmpty()) {
            System.out.println("There are no scheduled tasks to complete.");
            return;
        }

        Task task = scheduled.dequeue();
        System.out.println("Completed scheduled task:");
        printTask(task);
    }

    // ---------- Emergency tasks (stack, LIFO) ----------

    public static void storeEmergency(Stack<Task> emergencies, Scanner scanner) {
        System.out.println("\nStoring emergency task...");
        emergencies.push(readTask(scanner));
        System.out.println("Emergency stored successfully.");
    }

    public static void printEmergencies(Stack<Task> emergencies) {
        printTasks(reversed(emergencies), "Emergency tasks (the next one to handle is at the top)");
    }

    public static void handleNextEmergency(Stack<Task> emergencies) {
        if (emergencies.isEmpty()) {
            System.out.println("There are no emergencies to handle.");
            return;
        }

        Task task = emergencies.pop();
        System.out.println("Handling emergency:");
        printTask(task);
    }

    // ---------- Reading input ----------

    private static Task readTask(Scanner scanner) {
        System.out.println("What's the title of your task? ");
        String title = scanner.nextLine();

        System.out.println("What's the department? ");
        String department = scanner.nextLine();

        Instant dueDate = readDueDate(scanner);

        if (wantsUrgency(scanner)) {
            return new Task(title, department, readUrgency(scanner), dueDate);
        }
        return new Task(title, department, dueDate);
    }

    private static Instant readDueDate(Scanner scanner) {
        while (true) {
            System.out.println("What's the due date? (dd/MM/yyyy) ");
            try {
                LocalDate localDate = LocalDate.parse(scanner.nextLine(), dateFormatter);
                return localDate.atStartOfDay(ZONE).toInstant();
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date, please use the format dd/MM/yyyy.");
            }
        }
    }

    private static boolean wantsUrgency(Scanner scanner) {
        String answer;
        do {
            System.out.println("Do you wish to assign an urgency level? (y/n) ");
            answer = scanner.nextLine().toLowerCase();
        } while (!Objects.equals(answer, "y") && !Objects.equals(answer, "n"));
        return Objects.equals(answer, "y");
    }

    private static Urgency readUrgency(Scanner scanner) {
        while (true) {
            System.out.println("What's your urgency level for this task? (asap, high, medium, low) ");
            try {
                return Urgency.fromStr(scanner.nextLine().toLowerCase());
            } catch (NoSuchElementException e) {
                System.out.println("Invalid urgency level, please try again.");
            }
        }
    }

    // ---------- Printing ----------

    private static void printTasks(List<Task> tasks, String header) {
        if (tasks.isEmpty()) {
            System.out.println("\n" + header + ": none.");
            return;
        }

        System.out.println("\n" + header + ":");
        for (Task task : tasks) {
            printTask(task);
        }
    }

    private static void printTask(Task task) {
        String done = task.isDone() ? "[x]" : "[ ]";
        System.out.printf("%s %-25s | %-15s | %-6s | %s%n",
                done,
                task.getTitle(),
                task.getDepartment(),
                task.getUrgency().name(),
                dateFormatter.format(task.getDue_date().atZone(ZONE)));
    }

    // ---------- Transformation helpers ----------

    private static List<Task> pendingTasks(LinkedList<Task> tasks) {
        List<Task> pending = new ArrayList<>();
        for (Task task : tasks) {
            if (!task.isDone()) {
                pending.add(task);
            }
        }
        return pending;
    }

    private static List<Task> toList(LinkedList<Task> tasks) {
        List<Task> list = new ArrayList<>();
        for (Task task : tasks) {
            list.add(task);
        }
        return list;
    }

    private static <T> List<T> reversed(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        for (T item : iterable) {
            list.add(item);
        }
        Collections.reverse(list);
        return list;
    }

    private static List<Task> sortTasksByUrgency(List<Task> tasks) {
        List<Task> sorted = new ArrayList<>(tasks);
        for (int i = 0; i < sorted.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < sorted.size(); j++) {
                if (sorted.get(j).getUrgency().isMoreImportantThan(sorted.get(minIndex).getUrgency())) {
                    minIndex = j;
                }
            }
            swap(sorted, i, minIndex);
        }
        return sorted;
    }

    private static List<Task> sortTasksByDepartment(List<Task> tasks) {
        List<Task> sorted = new ArrayList<>(tasks);
        for (int i = 0; i < sorted.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < sorted.size(); j++) {
                if (sorted.get(j).getDepartment().compareToIgnoreCase(sorted.get(minIndex).getDepartment()) < 0) {
                    minIndex = j;
                }
            }
            swap(sorted, i, minIndex);
        }
        return sorted;
    }

    private static void swap(List<Task> tasks, int i, int j) {
        Task temp = tasks.get(i);
        tasks.set(i, tasks.get(j));
        tasks.set(j, temp);
    }
}
