package com.dev;

import java.util.Scanner;

public class App
{


    public static void main( String[] args )
    {
        LinkedList<Task> tasks = new LinkedList<>();
        Queue<Task> scheduledTaks = new Queue<>();
        Stack<Task> urgentTaks = new Stack<>();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println( "\n--e Taskman ---\n" );
            System.out.println("Welcome to Taskman where the management of your tasks are as simple as selecting a few options.");
            System.out.println("What do you wish me to do?");
            System.out.println("1. Add your task");
            System.out.println("2. Remove a task");
            System.out.println("3. Find a task");
            System.out.println("4. See all your tasks");
            System.out.println("5. See all your pending tasks");
            System.out.println("6. See your pending tasks ordered by urgency");
            System.out.println("7. See your pending tasks ordered by department");
            System.out.println("8. Exit");

            int option = readInt(scanner, 1, 8);

            switch (option) {
                case 1:
                    Taskman.addTask(tasks, scanner);
                    break;

                case 8:
                    System.out.println("Thank you for using Taskman!");
                    running = false;
                    break;
            }
        }

        scanner.close();
    }

    private static int readInt(Scanner scanner, int min, int max) {
        int option = -1;

        do {
            System.out.println("Select your option between " + min + " and " + max);
            option = scanner.nextInt();
        } while (option < min || option > max);

        return option;
    }
}
