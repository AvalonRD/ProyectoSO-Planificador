import java.util.Scanner;
import java.util.*;

public class Main {
    static double totalWaitingTime = 0;
    static double totalResponseTime = 0;
    static double totalExecutionTime = 0;
    static ArrayList<Process> sort_array = new ArrayList<>();
    static Dinamic_List ready_queue = new Dinamic_List();
    static Dinamic_List memory = new Dinamic_List();
    static int execution_counter = 0;
    static Process process_arrives_aux = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Gestor de Procesos");
        System.out.println("Simulador de Planificador de Procesos");
        System.out.println("Bienvenido");
        System.out.println("Indica el tamaño de la memoria: ");
        int memory_size = scanner.nextInt();
        System.out.println("Indica el Quantum para los procesos: ");
        int quantum = scanner.nextInt();
        System.out.println("Cuantos procesos deseas planificar?: ");
        int total_processes = scanner.nextInt();

        System.out.println("Captura de los Datos de Cada Proceso.");
        System.out.println("Ingrese los siguientes datos para cada proceso.");

        for (int i = 0; i < total_processes; i++) {
            Process process = new Process();

            System.out.println("Proceso " + i + ".");
            System.out.println("Id: ");
            process.setId(scanner.nextInt());
            scanner.nextLine();

            System.out.println("Nombre: ");
            process.setName(scanner.nextLine());

            System.out.println("Tamaño: ");
            process.setSize(scanner.nextInt());

            System.out.println("Tiempo de ejecución: ");
            process.setExecution_time(scanner.nextInt());

            System.out.println("Tiempo de llegada: ");
            process.setArrive_time(scanner.nextInt());

            sort_array.add(process);
            System.out.println("Imprimiendo Lista de Procesos.");
            printArray();
            System.out.println("");
        }

        scanner.close();
        Collections.sort(sort_array, Comparator.comparingInt(Process::getArrive_time));

        while (!ready_queue.isEmpty() || !memory.isEmpty() || !sort_array.isEmpty()) {
            if (!memory.isEmpty()) {
                Process running_process = memory.delete();
                executeProcess(running_process, quantum);
                memory_size += running_process.getSize();
                System.out.println("Proceso " + running_process.getId() + " liberado de la memoria, quedan " + memory_size + " unidades de memoria");
                wait(1400);
            }

            if (!ready_queue.isEmpty() && memory_size >= ready_queue.getFirstNodeProcessSize()) {
                Process process_ready = ready_queue.delete();
                memory.add(process_ready);
                memory_size -= process_ready.getSize();
                System.out.println("\nSubió el proceso " + process_ready.getId() + " y restan " + (memory_size) + " unidades de memoria");
                wait(1500);
                printQueues();
                wait(1500);
            }

            while (!ready_queue.isEmpty() && memory_size >= ready_queue.getFirstNodeProcessSize()) {
                Process head_process = ready_queue.delete();
                memory.add(head_process);
                memory_size -= head_process.getSize();
                System.out.println("\nSubió el proceso " + head_process.getId() + " y restan " + (memory_size) + " unidades de memoria");
                wait(1500);
                printQueues();
                wait(1500);
            }

            Process process_ready = checkIfProcessArrives();
            if (process_ready != null) {
                ready_queue.add(process_ready);
                System.out.println("Subió el proceso " + process_ready.getId() + " a la cola de procesos listos en el tiempo " + process_ready.getArrive_time());
                wait(1400);
            }

            printQueues();
        }

        System.out.println("NO HAY MAS PROCESOS.");

        double avgWaitingTime = totalWaitingTime / total_processes;
        double avgResponseTime = totalResponseTime / total_processes;
        double avgExecutionTime = totalExecutionTime / total_processes;

        System.out.println("\nPromedio de Tiempo de Espera: " + avgWaitingTime);
        System.out.println("Promedio de Tiempo de Respuesta: " + avgResponseTime);
        System.out.println("Promedio de Tiempo de Ejecución: " + avgExecutionTime);
    }

    private static void executeProcess(Process running_process, int quantum) {
        int running_process_exec_time = running_process.getExecution_time_remaining();
        Process process_ready = null;
        int aux = quantum;
        while (aux > 0 && running_process_exec_time > 0) {
            System.out.println("Tiempo -> " + execution_counter + " | " + "P" + running_process.getId() + " en ejecución " + running_process_exec_time + " msg");
            aux--;
            running_process_exec_time--;
            process_ready = checkIfProcessArrives();
            execution_counter++;
            wait(1400);
        }

        int waitingTime = execution_counter - running_process.getArrive_time();
        totalWaitingTime += waitingTime;

        int responseTime = execution_counter - running_process.getArrive_time();
        totalResponseTime += responseTime;

        int executionTime = running_process.getExecution_time() - running_process.getExecution_time_remaining();
        totalExecutionTime += executionTime;

        if (running_process_exec_time > 0) {
            running_process.setExecution_time_remaining(running_process_exec_time);
            ready_queue.add(running_process);
            System.out.println("Terminó el quantum del proceso " + running_process.getId() + " restan " + running_process.getExecution_time_remaining() + "ms de ejecución");
            wait(1400);
            System.out.println("Proceso " + running_process.getId() + " agregado a la cola de procesos listos.");
        } else {
            System.out.println("Finalizó la ejecución del proceso " + running_process.getId() + ".");
            wait(1400);
            if ((ready_queue.isEmpty() && memory.isEmpty()) && !sort_array.isEmpty()) {
                execution_counter = sort_array.get(0).getArrive_time();
                process_ready = sort_array.get(0);
                sort_array.remove(0);
                ready_queue.add(process_ready);
            }
        }
    }

    private static Process checkIfProcessArrives() {
        if (sort_array.isEmpty()) {
            return null;
        }
        Process process_aux = sort_array.get(0);
        if (execution_counter == sort_array.get(0).getArrive_time()) {
            if (sort_array.size() == 1) {
                process_arrives_aux = process_aux;
            }
            ready_queue.add(process_aux);
            sort_array.remove(0);
            return process_aux;
        }
        return null;
    }

    private static void printArray() {
        for (int i = 0; i < sort_array.size(); i++) {
            System.out.print("[" + sort_array.get(i).getId() + "]");
        }
    }

    private static void wait(int miliseconds) {
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void printQueues() {
        System.out.println("\nCola de procesos listos para su ejecución:");
        memory.printList();
        wait(1500);
        System.out.println("Cola de procesos Listos:");
        ready_queue.printList();
        wait(1500);
    }
}
