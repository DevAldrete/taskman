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
                    System.out.println("\nGracias por utilizar Taskman!");
                    running = false;
                    break;
            }
        }

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n--- Taskman ---");
        System.out.println("Bienvenido a Taskman, donde el manejo de tus tareas es tan simple como seleccionando algunas opciones.");
        System.out.println("Que deseas que haga?");
        System.out.println("1. Trabajar con mis tareas generales");
        System.out.println("2. Trabajar con mis tareas rutinarias o programadas");
        System.out.println("3. Trabajar con mis tareas de emergencia");
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
                    System.out.println("Cual es el id de la tarea que deseas remover? ");
                    Taskman.removeTask(tasks, scanner, readId(scanner));
                    break;

                case 3:
                    System.out.println("Cual es el id de la tarea que deseas encontrar? ");
                    Taskman.findTask(tasks, scanner, readId(scanner));
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
                    System.out.println("Cual es el id de la tarea que completaste? ");
                    Taskman.markTaskDone(tasks, scanner, readId(scanner));
                    break;

                case 9:
                    inMenu = false;
                    break;
            }
        }
    }

    private static void printGeneralMenu() {
        System.out.println("\n-- Tareas Generales --");
        System.out.println("1. Agregar una tarea");
        System.out.println("2. Remover una tarea");
        System.out.println("3. Encontrar una tarea");
        System.out.println("4. Ve todas tus tareas");
        System.out.println("5. Ve todas tus tareas pendientes");
        System.out.println("6. Ve todas tus tareas pendientes ordenadas por urgencia");
        System.out.println("7. Ve todas tus tareas pendientes ordenadas por departamento");
        System.out.println("8. Marca una tarea como terminada");
        System.out.println("9. Regresar al menu principal");
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
        System.out.println("\n-- Tareas Rutinarias o Programadas --");
        System.out.println("1. Programar una tarea");
        System.out.println("2. Ver tus tareas programadas");
        System.out.println("3. Completar la siguiente tarea programada");
        System.out.println("4. Regresar al menu principal");
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
        System.out.println("\n-- Tareas de Emergencia --");
        System.out.println("1. Guardar una tarea de emergencia");
        System.out.println("2. Ver mis tareas de emergencia");
        System.out.println("3. Atender la tarea de emergencia mas reciente");
        System.out.println("4. Regresar al menu principal");
    }

    // Leemos el id de una tarea desde la entrada estandar
    // Se lee la linea completa (a diferencia de scanner.nextInt()) para no dejar el salto de linea en el buffer
    // lo cual hacia que el menu se mostrara dos veces pidiendo la opcion al usuario
    private static int readId(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Id invalido, por favor ingresa un numero entero.");
            }
        }
    }

    private static int readInt(Scanner scanner, int min, int max) {
        int option = -1;

        do {
            System.out.println("Selecciona una opcion entre " + min + " y " + max);
            try {
                option = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                option = -1;
            }
        } while (option < min || option > max);

        return option;
    }
}
