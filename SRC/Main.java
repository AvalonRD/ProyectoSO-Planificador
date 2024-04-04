import java.util.Scanner;
import java.util.*;

public class Main {
    static ArrayList<Process> sort_array = new ArrayList<>();
    static Dinamic_List ready_queue = new Dinamic_List();
    static Dinamic_List memory = new Dinamic_List();
    static int execution_counter = 0;
    static Process process_arrives_aux = null;
    static int total_processes = 0; // Variable para almacenar el número total de procesos

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
        total_processes = scanner.nextInt(); // Almacenar el número total de procesos

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
            System.out.println("Prioridad: ");
            process.setPriority(scanner.nextInt());
            System.out.println("Tiempo de llegada: ");
            process.setArrive_time(scanner.nextInt());
            sort_array.add(process);
            System.out.println("Imprimiendo Lista de Procesos.");
            printArray();
            ready_queue.printList();
            System.out.println("");
        }
        scanner.close();

        Collections.sort(sort_array, new Comparator<Process>(){
            @Override
            public int compare(Process p1, Process p2){
                return Integer.compare(p1.getArrive_time(),p2.getArrive_time());
            }
        });

        System.out.println("Imprimiendo Lista de Procesos Ordenados.");
        printArray();

        Process first_process = sort_array.get(0);
        sort_array.remove(0);
        execution_counter += first_process.getArrive_time();
        System.out.println("\nSubió el proceso " + first_process.getId() + " a la cola de procesos listos en el tiempo " + execution_counter);
        ready_queue.add(first_process);

        System.out.println("Preparando Procesos... ");

        while (ready_queue.getNode_head() != null || memory.getNode_head() != null) {
            Process process_ready = checkIfProcessArrives();
            if (process_ready != null) {
                System.out.println("Subió el proceso " + process_ready.getId() + " a la cola de procesos listos en el tiempo " + process_ready.getArrive_time());
                ready_queue.add(process_ready);
                printQueues();
            }

            while ((memory_size >= ready_queue.getFirstNodeProcessSize()) && ready_queue.getNode_head() != null) {
                System.out.println("\nSubió el proceso " + ready_queue.getFirstNodeProcessId() + " y restan " + (memory_size - ready_queue.getFirstNodeProcessSize()) + " unidades de memoria");
                Process head_process = ready_queue.delete();
                memory.add(head_process);
                memory_size -= head_process.getSize();
                System.out.print("\033[" + 1 + "A");
                System.out.print("\033[2K");
                System.out.println("Cola de procesos listos para su ejecución:");
                memory.printList();
                System.out.print("\033[" + 3 + "A");
                System.out.print("\033[2K");
                ready_queue.printList();
                System.out.print("\033[" + (3) + "B");
            }

            int aux = quantum;
            System.out.println("\nInicia la planificación de los procesos ...");
            printQueues();
            Process running_process = memory.delete();
            System.out.println("Se planifica P" + running_process.getId());
            memory.printList();

            int running_process_exec_time = running_process.getExecution_time_remaining();
            while (aux > 0 && running_process_exec_time > 0) {
                System.out.println("Tiempo -> " + execution_counter + " | " + "P" + running_process.getId() + " en ejecución " + running_process_exec_time + " msg");
                aux--;
                running_process_exec_time--;
                process_ready = checkIfProcessArrives();
                execution_counter++;
            }

            if (running_process_exec_time > 0) {
                running_process.setExecution_time_remaining(running_process_exec_time);
                ready_queue.add(running_process);
                System.out.println("Terminó el quantum del proceso " + running_process.getId() + " restan " + running_process.getExecution_time_remaining() + "ms de ejecución");
                System.out.println("Proceso " + running_process.getId() + " agregado a la cola de procesos listos.");
            } else {
                running_process.setLast_execution_time(execution_counter); // Actualiza el tiempo de finalización del proceso
                running_process.setWaitingTime(execution_counter - running_process.getArrive_time() - running_process.getExecution_time()); // Calcula el tiempo de espera
                running_process.setTurnaroundTime(execution_counter - running_process.getArrive_time()); // Calcula el tiempo de ejecución
                System.out.println("Finalizó la ejecución del proceso " + running_process.getId() + " en el tiempo " + execution_counter);
                if ((ready_queue.getNode_head() == null && memory.getNode_head() == null) && !sort_array.isEmpty()) {
                    execution_counter = sort_array.get(0).getArrive_time();
                    process_ready = sort_array.get(0);
                    sort_array.remove(0);
                    ready_queue.add(process_ready);
                }
            }
            memory_size += running_process.getSize();
            if (process_arrives_aux != null) {
                process_ready = process_arrives_aux;
            }
            if (process_ready != null) {
                System.out.println("Subió el proceso " + process_ready.getId() + " a la cola de procesos listos en el tiempo " + process_ready.getArrive_time());
            }
            printQueues();
        }

        // Calcular tiempos promedio
        double totalWaitingTime = 0;
        double totalResponseTime = 0;
        double totalTurnaroundTime = 0;

        for (Process process : sort_array) {
            totalWaitingTime += process.getWaitingTime();
            totalResponseTime += process.getResponseTime();
            totalTurnaroundTime += process.getTurnaroundTime();
        }

        double avgWaitingTime = totalWaitingTime / total_processes;
        double avgResponseTime = totalResponseTime / total_processes;
        double avgTurnaroundTime = totalTurnaroundTime / total_processes;

        // Imprimir resultados de los tiempos promedio
        System.out.println("Tiempo de espera promedio: " + avgWaitingTime);
        System.out.println("Tiempo promedio de respuesta: " + avgResponseTime);
        System.out.println("Tiempo promedio de ejecución: " + avgTurnaroundTime);
        System.out.println("NO HAY MAS PROCESOS.");
    }

    private static Process checkIfProcessArrives() {
        if (sort_array.isEmpty()) {
            return null;
        }
        Process process_aux = sort_array.get(0);
        if (execution_counter == process_aux.getArrive_time()) {
            if (sort_array.size() == 1) {
                process_arrives_aux = process_aux;
            }
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
        System.out.println("Cola de procesos Listos:");
        ready_queue.printList();
    }
}
