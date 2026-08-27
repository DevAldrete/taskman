package com.dev;

import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        LinkedList<Task> tasks = new LinkedList<>();
        Queue<Task> scheduledTasks = new Queue<>();
        Stack<Task> emergencyTasks = new Stack<>();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMainMenu();

            switch (readInt(scanner, 1, 4)) {
                case 1:
                    generalTasksMenu(tasks, scanner);
                    break;

                case 2:
                    scheduledTasksMenu(scheduledTasks, scanner);
                    break;

                case 3:
                    emergencyTasksMenu(emergencyTasks, scanner);
                    break;

                case 4:
                    System.out.println("\nThank you for using Taskman!");
                    running = false;
                    break;
            }
        }

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n--- Taskman ---");
        System.out.println("Welcome to Taskman, where the management of your tasks is as simple as selecting a few options.");
        System.out.println("What do you wish me to do?");
        System.out.println("1. Work with my general tasks");
        System.out.println("2. Work with my scheduled tasks (daily routine)");
        System.out.println("3. Work with my emergency tasks");
        System.out.println("4. Exit");
    }

    private static void generalTasksMenu(LinkedList<Task> tasks, Scanner scanner) {
        boolean inMenu = true;

        while (inMenu) {
            printGeneralMenu();

            switch (readInt(scanner, 1, 9)) {
                case 1:
                    Taskman.addTask(tasks, scanner);
                    break;

                case 2:
                    System.out.println("What's the title of the task you want to remove? ");
                    Taskman.removeTask(tasks, scanner, scanner.nextLine());
                    break;

                case 3:
                    System.out.println("What's the title of the task you want to find? ");
                    Taskman.findTask(tasks, scanner, scanner.nextLine());
                    break;

                case 4:
                    Taskman.printAllTasks(tasks);
                    break;

                case 5:
                    Taskman.printPendingTasks(tasks);
                    break;

                case 6:
                    Taskman.printPendingTasksByUrgency(tasks);
                    break;

                case 7:
                    Taskman.printPendingTasksByDepartment(tasks);
                    break;

                case 8:
                    System.out.println("What's the title of the task you completed? ");
                    Taskman.markTaskDone(tasks, scanner, scanner.nextLine());
                    break;

                case 9:
                    inMenu = false;
                    break;
            }
        }
    }

    private static void printGeneralMenu() {
        System.out.println("\n-- General tasks --");
        System.out.println("1. Add your task");
        System.out.println("2. Remove a task");
        System.out.println("3. Find a task");
        System.out.println("4. See all your tasks");
        System.out.println("5. See all your pending tasks");
        System.out.println("6. See your pending tasks ordered by urgency");
        System.out.println("7. See your pending tasks ordered by department");
        System.out.println("8. Mark a task as done");
        System.out.println("9. Back to the main menu");
    }

    private static void scheduledTasksMenu(Queue<Task> scheduledTasks, Scanner scanner) {
        boolean inMenu = true;

        while (inMenu) {
            printScheduledMenu();

            switch (readInt(scanner, 1, 4)) {
                case 1:
                    Taskman.scheduleTask(scheduledTasks, scanner);
                    break;

                case 2:
                    Taskman.printScheduledTasks(scheduledTasks);
                    break;

                case 3:
                    Taskman.completeNextScheduled(scheduledTasks);
                    break;

                case 4:
                    inMenu = false;
                    break;
            }
        }
    }

    private static void printScheduledMenu() {
        System.out.println("\n-- Scheduled tasks (your daily routine) --");
        System.out.println("1. Schedule a task for the day");
        System.out.println("2. See my scheduled tasks");
        System.out.println("3. Complete the next scheduled task");
        System.out.println("4. Back to the main menu");
    }

    private static void emergencyTasksMenu(Stack<Task> emergencyTasks, Scanner scanner) {
        boolean inMenu = true;

        while (inMenu) {
            printEmergencyMenu();

            switch (readInt(scanner, 1, 4)) {
                case 1:
                    Taskman.storeEmergency(emergencyTasks, scanner);
                    break;

                case 2:
                    Taskman.printEmergencies(emergencyTasks);
                    break;

                case 3:
                    Taskman.handleNextEmergency(emergencyTasks);
                    break;

                case 4:
                    inMenu = false;
                    break;
            }
        }
    }

    private static void printEmergencyMenu() {
        System.out.println("\n-- Emergency tasks --");
        System.out.println("1. Store an emergency task");
        System.out.println("2. See my emergency tasks");
        System.out.println("3. Handle the most recent emergency");
        System.out.println("4. Back to the main menu");
    }

    private static int readInt(Scanner scanner, int min, int max) {
        int option = -1;

        do {
            System.out.println("Select your option between " + min + " and " + max);
            try {
                option = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                option = -1;
            }
        } while (option < min || option > max);

        return option;
    }
}
