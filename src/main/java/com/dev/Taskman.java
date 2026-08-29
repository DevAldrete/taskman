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

// Taskman: Modulo de funciones para la app
public class Taskman {
    private static final ZoneId ZONE = ZoneId.of("America/Mexico_City");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Seccion de Tareas Generales

    // Anadimos tarea dada desed la funcion estatica readTask la cual nos da directamente la tarea que
    // el usuario desea agregar
    public static void addTask(LinkedList<Task> tasks, Scanner scanner) {
        System.out.println("\nAgregando tarea...");
        tasks.add(readTask(scanner));
        System.out.println("Tarea agregada exitosamente.");
    }

    // Removemos tarea mediante el id y utilizamos la funcion findTask para encontrar la tarea en la lista
    // y posteriormente removerla con el metodo delete
    public static void removeTask(LinkedList<Task> tasks, Scanner scanner, int id) {
        Task task = findTask(tasks, id);
        if (task == null) {
            System.out.println("No se encontro ninguna tarea con el id \"" + id + "\".");
            return;
        }

        tasks.delete(task);
        System.out.println("Se removio correctamente la tarea con id \"" + id + "\".");
    }

    // Esta es una funcion de apoyo para simplemente encontrar la tarea de cierto id y despues mostrarla en
    // consola
    public static void findTask(LinkedList<Task> tasks, Scanner scanner, int id) {
        Task task = findTask(tasks, id);
        if (task == null) {
            System.out.println("No se encontro ninguna tarea con \"" + id + "\".");
            return;
        }

        System.out.println("\nTarea encontrada:");
        printTask(task);
    }

    // Encontramos la tarea en la lista dada con cierto id proveido por el usuario como argumento en esta funcion
    // hacemos un loop simple en nuestra lista para encontrar el id y regresarlo como el resultado de esta funcion
    // si se encuentra, de lo contrario se regresa null
    public static Task findTask(LinkedList<Task> tasks, int id) {
        for (Task task : tasks) {
            if (Objects.equals(task.getId(), id)) {
                return task;
            }
        }
        return null;
    }

    // Marcamos la tarea como terminada mediante su id
    public static void markTaskDone(LinkedList<Task> tasks, Scanner scanner, int id) {
        Task task = findTask(tasks, id);
        if (task == null) {
            System.out.println("No se encontro ninguna tarea con el id \"" + id + "\".");
            return;
        }

        task.setDone(true); // Aqui se marca como terminada
        task.setUpdated_at(Instant.now()); // Y se actualiza este campo de la tarea ya que actualizamos la informacion/metadata
                                           // de la tarea
        System.out.println("Tarea con \"" + id + "\" marcada como terminada correctamente.");
    }

    // Funcion de apoyo para mostrar en consola cada una de las tareas sin importar si estan terminadas o pendientes
    public static void printAllTasks(LinkedList<Task> tasks) {
        printTasks(toList(tasks), "Tus Tareas");
    }

    // Funcion de apoyo para mostrar en consola cada una de las tareas que siguen pendientes (sin terminar) utilizando la
    // funcion pendingTasks
    public static void printPendingTasks(LinkedList<Task> tasks) {
        printTasks(pendingTasks(tasks), "Tareas pendientes");
    }

    // Funcion de apoyo para encontrar las tareas pendientes y ordernarlas por urgencia de cada tarea
    public static void printPendingTasksByUrgency(LinkedList<Task> tasks) {
        printTasks(sortTasksByUrgency(pendingTasks(tasks)), "Tareas pendientes ordenadas por urgencia");
    }

    // Funcion de apoyo para encontrar las tareas pendientes y ordernarlas por departamento de la tarea
    public static void printPendingTasksByDepartment(LinkedList<Task> tasks) {
        printTasks(sortTasksByDepartment(pendingTasks(tasks)), "Tareas pendientes ordenadas por departamento");
    }

    // Tareas programadas utilizando Colas/Queue

    // Agregar la tarea a programar utilizando la misma funcion de apoyo readTask para obtener la tarea del usuario de manera limpia
    public static void scheduleTask(Queue<Task> scheduled, Scanner scanner) {
        System.out.println("\nProgramando tarea para el dia...");
        scheduled.enqueue(readTask(scanner));
        System.out.println("Tarea programada exitosamente.");
    }

    // Utilizamos la funcion de printTasks para que de manera similar mostremos las tareas programadas en consola
    // y ademas, utilizamos la funcion de apoyo reversed debido a que al recorrer la cola, empezamos desde el ultimo lugar
    // pero en realidad no vamos a completar las tareas al reves, sino las que esten programadas primero ya que esa es la logica
    // de una Cola o Queue en este caso por lo que hacemos ponemos al reves el resultado para mostrar correctamente las tareas
    public static void printScheduledTasks(Queue<Task> scheduled) {
        printTasks(reversed(scheduled), "Tareas Programadas");
    }

    // Esta funcion solo sirve para "marcar" como terminada la tarea que en teoria ya realizamos, por lo tanto la quitamos de la cola
    // tambien nos permite saber si hay mas tareas por completar o si ya no hay ninguna
    public static void completeNextScheduled(Queue<Task> scheduled) {
        if (scheduled.isEmpty()) {
            System.out.println("No hay tareas programadas por el momento.");
            return;
        }

        Task task = scheduled.dequeue();
        System.out.println("Tarea programada ");
        printTask(task);
    }

    // Tareas de emergencia con Pilas o Stacks

    // Nos permite guardar una emergencia dada como tarea por parte del usuario con la funcion de apoyo readTask
    // cabe recalcar que la emergencia se guarda como tarea pero la logica de la aplicacion no las tratara como tareas normales
    public static void storeEmergency(Stack<Task> emergencies, Scanner scanner) {
        System.out.println("\nGuardando tarea de emergencia..");
        emergencies.push(readTask(scanner));
        System.out.println("Emergencia guardada exitosamente.");
    }

    // Mostramos las emergencias mediante la misma logica que anteriormente con colas al utilizar reversed
    public static void printEmergencies(Stack<Task> emergencies) {
        printTasks(reversed(emergencies), "Tareas de Emergencia");
    }

    // Manera de observar cual es la siguiente emergencia que debemos atender aprovechandonos de la logica de una pila
    // es decir, la emergencia que llega al ultimo se atiende primero (LIFO), y tambien checamos si hay emergencias que atender
    // de lo contrario se avisa al usuario
    public static void handleNextEmergency(Stack<Task> emergencies) {
        if (emergencies.isEmpty()) {
            System.out.println("No hay mas emergencias que atender.");
            return;
        }

        Task task = emergencies.pop();
        System.out.println("Handling emergency:");
        printTask(task);
    }

    // Funciones de apoyo para la logica de la app

    // Leemos la tarea del usuario utilizando el Scanner y ademas de utilizar la funcion readDueDate para leer correctamente la
    // fecha proveida por el usuario y no haya errores o crasheos en el programa
    // Adeemas, se le pregunta al usuario si desea agregar un grado de urgencia por ellos mismos (el grado o nivel por default es MEDIUM
    // o una tarea con urgencia medianamente importante), si asi lo desea, entonces utilizamos el otro constructor de la tarea que nos
    // permite ingresar el nivel de urgencia del usuario, ademas, utilizamos una funcion mas readUrgency para leer correctamente el
    // nivel o grado de urgencia del usuario, asi, si el usuario no desea agregar urgencia se utiliza el constructor que no ocupa que se
    // le pase un nivel de urgencia y se asigna el por default
    private static Task readTask(Scanner scanner) {
        System.out.println("Cual es el titulo de tu tarea? ");
        String title = scanner.nextLine();

        System.out.println("Cual es el departamento a asignar? ");
        String department = scanner.nextLine();

        Instant dueDate = readDueDate(scanner);

        if (wantsUrgency(scanner)) {
            return new Task(title, department, readUrgency(scanner), dueDate);
        }
        return new Task(title, department, dueDate);
    }

    // Se le pide al usuario que ingrese una fecha con el formato dado como ejemplo en el prompt, y se intenta convertir de cadena de texto
    // dada por el usuario a una fecha, si se logra hacer (ya que el usuario ingreso correctamente la fecha) entonces se regresa un objeto
    // Instant el cual simplemente es una manera de registrar precisa la fecha del usuario para su tarea.
    // Si el usuario se equivoca al ingresar el formato de fecha, no lo hacemos valido y le pedimos que ingrese de nuevo la fecha con el
    // formato acordado
    private static Instant readDueDate(Scanner scanner) {
        while (true) {
            System.out.println("Cual es la deadline para la tarea? (dd/MM/yyyy) ");
            try {
                LocalDate localDate = LocalDate.parse(scanner.nextLine(), dateFormatter);
                return localDate.atStartOfDay(ZONE).toInstant();
            } catch (DateTimeParseException e) {
                System.out.println("Fecha invalida, por favor usar el formato dd/MM/yyyy.");
            }
        }
    }

    // Se le pregunta al usuario si desea agregar un nivel de urgencia y regresa un booleano, en este caso, true si asi lo desea de lo
    // contrario se regresa un false
    private static boolean wantsUrgency(Scanner scanner) {
        String answer;
        do {
            System.out.println("Desea asignar un nivel de urgencia? (s/n) ");
            answer = scanner.nextLine().toLowerCase();
        } while (!Objects.equals(answer, "s") && !Objects.equals(answer, "n"));
        return Objects.equals(answer, "s");
    }

    // Leemos el nivel de urgencia del usuario dado el input del usuario, en este caso, utilizamos un try catch para saber si el usuario
    // ingreso un nivel de urgencia correcto de lo contrario se le hace saber y volver a intentar alguno de los valores mostrados
    private static Urgency readUrgency(Scanner scanner) {
        while (true) {
            System.out.println("Cual es su nivel de urgencia para esta tarea? (asap, high, medium, low) ");
            try {
                return Urgency.fromStr(scanner.nextLine().toLowerCase());
            } catch (NoSuchElementException e) {
                System.out.println("Nivel de urgencia invalido, por favor intentelo de nuevo.");
            }
        }
    }

    // Se muestra cada tarea de la lista a consola, en este caso, se avisa al usuario si no hay ninguna tarea, y si si la hay entonces
    // utilizamos un loop para utilizar la funcion printTask en cada tarea
    private static void printTasks(List<Task> tasks, String header) {
        if (tasks.isEmpty()) {
            System.out.println("\n" + header + ": vacio.");
            return;
        }

        System.out.println("\n" + header + ":");
        for (Task task : tasks) {
            printTask(task);
        }
    }

    // Se muestra la tarea de manera formateada y limpia a la consola, con un truco que en este caso seria si la tarea esta terminada
    // entonces se muestra como marcada [x] de lo contrario se muestra como pendiente [ ]
    private static void printTask(Task task) {
        String done = task.isDone() ? "[x]" : "[ ]";
        System.out.printf("%i %s %-25s | %-15s | %-6s | %s%n",
                            task.getId(),
                            done,
                            task.getTitle(),
                            task.getDepartment(),
                            task.getUrgency().name(),
                            dateFormatter.format(task.getDue_date().atZone(ZONE)));
    }

    // Se filtran las tareas que siguen pendientes en la lista de tareas que se pasan como argumento
    private static List<Task> pendingTasks(LinkedList<Task> tasks) {
        List<Task> pending = new ArrayList<>();
        for (Task task : tasks) {
            if (!task.isDone()) {
                pending.add(task);
            }
        }
        return pending;
    }

    // Se convierte de la lista enlazada a una lista propia de Java con fin de un uso generico en algunas funciones de apoyo
    // aprovechando la interfaz List<>
    private static List<Task> toList(LinkedList<Task> tasks) {
        List<Task> list = new ArrayList<>();
        for (Task task : tasks) {
            list.add(task);
        }
        return list;
    }

    // Se acepta un iterable que en este caso puede ser una cola, pila o lista enlazada para invertir la estructura y se retorna como
    // una lista propia de Java List<>
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
