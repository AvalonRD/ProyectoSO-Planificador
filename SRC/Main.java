import java.util.Scanner;
import java.util.*;
//import org.fusesource.jansi.AnsiConsole;

// Clase principal
public class Main {
    // Declaración de dos listas dinámicas para manejar los procesos en cola y en memoria
    static ArrayList<Process> sort_array = new ArrayList<>();
    static Dinamic_List ready_queue = new Dinamic_List();
    static Dinamic_List memory = new Dinamic_List();
    static int execution_counter = 0;
    static Process process_arrives_aux = null;
    static float average_wait_time = 0;
    static float average_response_time = 0;
    static float average_execution_time = 0;


    // Método principal
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Inicia la etapa de recolección de datos
        System.out.println("Gestor de Procesos");
        System.out.println("Simulador de Planificador de Procesos");
        System.out.println("Bienvenido");
        System.out.println("Indica el tamaño de la memoria: ");
        int memory_size = scanner.nextInt(); // Tamaño de la memoria del sistema
        System.out.println("Indica el Quantum para los procesos: ");
        int quantum = scanner.nextInt(); // Tamaño del quantum para el planificador
        System.out.println("Cuantos procesos deseas planificar?: ");
        int total_processes = scanner.nextInt(); // Número total de procesos a planificar

        System.out.println("Captura de los Datos de Cada Proceso.");
        System.out.println("Ingrese los siguientes datos para cada proceso.");

        // Se recolectan los datos para cada proceso y se agregan a la cola de procesos listos
        for(int i = 0; i < total_processes; i++ ) {
            Process process = new Process(); // Crear un nuevo proceso
            System.out.println("Proceso " + i + ".");
            System.out.println("Id: ");
            process.setId(scanner.nextInt()); // Asignar ID al proceso
            scanner.nextLine();
            System.out.println("Nombre: ");
            process.setName(scanner.nextLine()); // Asignar nombre al proceso
            System.out.println("Tamaño: ");
            process.setSize(scanner.nextInt()); // Asignar tamaño al proceso
            System.out.println("Tiempo de ejecución: ");
            process.setExecution_time(scanner.nextInt()); // Asignar tiempo de ejecución al proceso
            System.out.println("Prioridad: ");
            process.setPriority(scanner.nextInt()); // Asignar prioridad al proceso
            System.out.println("Tiempo de llegada: ");
            process.setArrive_time(scanner.nextInt()); // Asignar tiempo de llegada al proceso
            sort_array.add(process);
            //ready_queue.add(process); // Agregar proceso a la cola de procesos listos
            System.out.println("Imprimiendo Lista de Procesos.");
            printArray();
            //ready_queue.printList(); // Imprimir la cola de procesos listos
            System.out.println("");
        }
        scanner.close(); // Cerrar el scanner después de haber recolectado todos los datos
        Collections.sort(sort_array, new Comparator<Process>(){
            @Override
            public int compare(Process p1, Process p2){
                return Integer.compare(p1.getArrive_time(),p2.getArrive_time());
            }
        }); // Se ordenan los procesos *creo*
        System.out.println("Imprimiendo Lista de Procesos Ordenados.");
        printArray();

        // Se encolan los procesos ordenados de acuerdo a su tiempo de llegada
        Process first_process = sort_array.get(0);
        //System.out.println("s{" + sort_array.get(0).getId() + "}");
        sort_array.remove(0);
        execution_counter += first_process.getArrive_time();
        System.out.println("Subio el proceso " + first_process.getId() + " a la cola de procesos listos en el tiempo " + execution_counter);
        ready_queue.add(first_process);
        //System.out.println("s{" + sort_array.get(0).getId() + "}");

        System.out.println("Preparando Procesos... ");
        wait(1500); // Esperar un tiempo para simular la preparación de procesos
        System.out.println("Cargando procesos a la memoria.\n");
        wait(1500); // Esperar un tiempo para simular la carga de procesos en memoria

        // Inicia la etapa para cargar los procesos en cola a la memoria
        int last_process_id;

        // Ciclo principal que ejecuta la planificación de procesos mientras haya procesos en cola o en memoria
        while(ready_queue.getNode_head() != null || memory.getNode_head() != null) {
            // Ciclo para cargar procesos en memoria mientras haya espacio y procesos en cola
            // ---------------------------------> CARGA DE PROCESOS EN MEMORIA <----------------------------------------------
            while((memory_size >= ready_queue.getFirstNodeProcessSize()) && ready_queue.getNode_head() != null ) {
                System.out.println("\nSubió el proceso "+ready_queue.getFirstNodeProcessId()+" y restan "+(memory_size-ready_queue.getFirstNodeProcessSize())+" unidades de memoria");
                wait(1500);
                printQueues(); // Imprimir el estado de las colas de procesos
                Process head_process = ready_queue.delete(); // Eliminar el primer proceso de la cola de procesos listos
                memory.add(head_process); // Agregar el proceso a la memoria
                memory_size -= head_process.getSize(); // Restar el tamaño del proceso al tamaño de la memoria disponible
                wait(1500); // Esperar un tiempo para simular la carga del proceso en memoria
                System.out.print("\033[" + 1 + "A"); // Mueve el cursor hacia arriba
                System.out.print("\033[2K"); // Borra la línea
                wait(1500); // Esperar un tiempo para simular la actualización de la pantalla
                ready_queue.printList(); // Imprimir la cola de procesos listos
                wait(1500); // Esperar un tiempo para simular la actualización de la pantalla
                System.out.print("\033[" + 3 + "A"); // Mueve el cursor hacia arriba
                System.out.print("\033[2K"); // Borra la línea
                wait(1500); // Esperar un tiempo para simular la actualización de la pantalla
                memory.printList(); // Imprimir la memoria
                wait(1500); // Esperar un tiempo para simular la actualización de la pantalla
                System.out.print("\033[" + (3) + "B"); // Mueve el cursor hacia abajo
                wait(1500); // Esperar un tiempo para simular la actualización de la pantalla
            }
            // ---------------------------------------------------------------------------------------------------------------
            // ---------------------------------> PLANIFICACION DE PROCESOS <-------------------------------------------------
            // Inicia la planificación de los procesos
            int aux = quantum; // Inicializa el contador de quantum
            System.out.println("\nInicia la planificación de los procesos ...");
            wait(1500); // Esperar un tiempo para simular el inicio de la planificación
            printQueues(); // Imprimir el estado de las colas de procesos
            Process running_process = memory.delete(); // Eliminar el proceso de memoria para su ejecución
            System.out.println("Se planifica P"+running_process.getId()); // Mostrar el proceso que se planificó para ejecución
            wait(1500); // Esperar un tiempo para simular la planificación del proceso
            System.out.println("Cola de procesos listos para su ejecución:");
            memory.printList(); // Imprimir la memoria después de la planificación

            // Simulación de la ejecución del proceso
            int running_process_exec_time = running_process.getExecution_time_remaining();

            // TESP
            if (running_process_exec_time - execution_counter <= 0){
                running_process.setMax_wait_time(execution_counter);
            }
            //

            // TRES
            if (running_process_exec_time == running_process.getExecution_time()){
                running_process.setFirst_top(execution_counter);
            }
            //


            Process process_ready = null;
            while(aux > 0 && running_process_exec_time > 0) {
                System.out.println("Tiempo -> " + execution_counter + " | " + "P"+running_process.getId()+" en ejecución "+running_process_exec_time+" msg");
                aux--; // Decrementar el contador de quantum
                running_process_exec_time--; // Decrementar el tiempo de ejecución restante del proceso
                process_ready = checkIfProcessArrives();
                execution_counter++;
                //System.out.println("*"+execution_counter+"*");
                wait(1400); // Esperar un tiempo para simular la ejecución del proceso
            }
            // ---------------------------------------------------------------------------------------------------------------

            // ---------------------------------> COMPROBACIONES FINALES <---------------------------------------------------
            // Verificar si el proceso terminó su ejecución o si aún le queda tiempo
            if(running_process_exec_time > 0) {
                running_process.setExecution_time_remaining(running_process_exec_time); // Actualizar el tiempo de ejecución restante del proceso
                ready_queue.add(running_process); // Agregar el proceso a la cola de procesos listos
                System.out.println("Terminó el quantum del proceso "+running_process.getId()+" restan "+running_process.getExecution_time_remaining()+"ms de ejecución");
                wait(1400); // Esperar un tiempo para simular la actualización de la pantalla
                System.out.println("Proceso "+running_process.getId()+" agregado a la cola de procesos listos.");
            } else {
                System.out.println("Finalizó la ejecución del proceso "+running_process.getId()+".");
                wait(1400); // Esperar un tiempo para simular la actualización de la pantalla

                // TESP
                average_wait_time += running_process.getMax_wait_time() - running_process.getArrive_time() - running_process.getExecution_time();
                // 

                // TRES
                average_response_time += running_process.getFirst_top() - running_process.getArrive_time();
                // 

                // TEJEC
                average_execution_time += execution_counter - running_process.getArrive_time();
                //

                if((ready_queue.getNode_head() == null && memory.getNode_head() == null) && !sort_array.isEmpty()){
                    System.out.println("sl{" + sort_array.get(0).getId() + "}");
                    execution_counter = sort_array.get(0).getArrive_time();
                    process_ready = sort_array.get(0);
                    sort_array.remove(0);
                    ready_queue.add(process_ready);
                    //System.out.println("sl{" + sort_array.get(0).getId() + "}");
                }
            }
            memory_size += running_process.getSize(); // Aumentar el tamaño de la memoria disponible después de liberar el proceso
            System.out.println("Proceso "+running_process.getId()+" liberado de la memoria, quedan "+memory_size+" unidades de memoria");
            //System.out.println("^["+process_ready+"]");
            wait(1400); // Esperar un tiempo para simular la actualización de la pantalla

            if(process_arrives_aux != null){
                process_ready = process_arrives_aux;
            }

            if(process_ready != null){
                //System.out.println("s{" + sort_array.get(0).getId() + "}");
                System.out.println("Subio el proceso " + process_ready.getId() + " a la cola de procesos listos en el tiempo " + process_ready.getArrive_time());
                //System.out.println("s{" + sort_array.get(0).getId() + "}");
                //process_ready = null;
                wait(1400);
            }



            // ---------------------------------------------------------------------------------------------------------------

            printQueues(); // Imprimir el estado de las colas de procesos después de la ejecución del proceso
        }

        System.out.println("NO HAY MAS PROCESOS."); // Indicar que no hay más procesos para ejecutar
        System.out.println("\n=======> CALCULOS <=======");
        System.out.println("Tiempo promedio de espera: "+(average_wait_time/total_processes));
        System.out.println("Tiempo promedio de respuesta: "+(average_response_time/total_processes));
        System.out.println("Tiempo promedio de ejecucion: "+(average_execution_time/total_processes));

    }

    private static Process checkIfProcessArrives(){
        if(sort_array.isEmpty()){
            return null;
        }
        //System.out.println("sm{" + sort_array.get(0).getId() + "}");
        Process process_aux = sort_array.get(0);
        if(execution_counter == sort_array.get(0).getArrive_time()){
            if(sort_array.size() == 1){
                 process_arrives_aux = process_aux;
            }
            ready_queue.add(process_aux);
            sort_array.remove(0);
            return process_aux;
        }
        return null;
    }

    private static void printArray(){
        for(int i=0; i<sort_array.size(); i++){
            System.out.print("[" + sort_array.get(i).getId() + "]");
        }
    }

    // Método para pausar la ejecución por un número dado de milisegundos
    private static void wait(int miliseconds) {
        try {
            Thread.sleep(miliseconds); // Pausar la ejecución por el número dado de milisegundos
        } catch (InterruptedException e) {
            e.printStackTrace(); // Manejar la excepción en caso de interrupción
        }
    }

    // Método para imprimir las colas de procesos listos
    private static void printQueues() {
        System.out.println("\nCola de procesos listos para su ejecución:");
        memory.printList(); // Imprimir la memoria
        wait(1500); // Esperar un tiempo para simular la actualización de la pantalla
        System.out.println("Cola de procesos Listos:");
        ready_queue.printList(); // Imprimir la cola de procesos listos
        wait(1500); // Esperar un tiempo para simular la actualización de la pantalla
    }

    // Método para borrar una línea de la consola (no está siendo utilizado en el código principal)
    public static void deleteLine(int line) {
        System.out.print("\033[" + line + "A"); // Mueve el cursor hacia arriba
        System.out.print("\033[2K"); // Borra la línea
        wait(1000); // Esperar un tiempo para simular la actualización de la pantalla
        System.out.println("Hola");
        System.out.print("\033[" + (line-1) + "B"); // Mueve el cursor hacia abajo
        System.out.println("Hola");
        System.out.flush();
    }
}
