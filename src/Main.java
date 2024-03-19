
import java.util.Scanner;
//import org.fusesource.jansi.AnsiConsole;


public class Main {
	static Dinamic_List ready_queue = new Dinamic_List();
	static Dinamic_List memory = new Dinamic_List();
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
	
		// Inicia la etapa de recoleccion de datos
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
		
		// Se recolectan los datos para cada proceso y se agregan a la cola de procesos listos
		for(int i =0 ; i < total_processes; i++ ) {
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
			ready_queue.add(process);
			System.out.println("Imprimiendo Cola de procesos Listos.");
			//ready_queue.getSize();
			ready_queue.printList();
			System.out.println("");
		}
		scanner.close();
		
		System.out.println("Preparando Procesos... ");
		wait(1500);
		System.out.println("Cargando procesos a la memoria.\n");
		wait(1500);

		// Inicia la etapa para cargar los procesos en cola a la memoria
		int last_process_id;
		
		while(ready_queue.getNode_head() != null || memory.getNode_head() != null) {
			while((memory_size >= ready_queue.getFirstNodeProcessSize()) && ready_queue.getNode_head() != null ) {
				System.out.println("\nSubió el proceso "+ready_queue.getFirstNodeProcessId()+" y restan "+(memory_size-ready_queue.getFirstNodeProcessSize())+" unidades de memoria");
				wait(1500);
				printQueues();
				Process head_process = ready_queue.delete();
				memory.add(head_process);
				memory_size -= head_process.getSize();
				//System.out.println("Subió el proceso "+head_process.getId()+" y restan "+memory_size+" unidades de memoria");
				last_process_id = head_process.getId();
				
				wait(1500);
				System.out.print("\033[" + 1 + "A"); // Mueve el cursor hacia arriba
			    System.out.print("\033[2K"); // Borra la línea
			    wait(1500);
			    ready_queue.printList();
			    wait(1500);
			    System.out.print("\033[" + 3 + "A"); // Mueve el cursor hacia arriba
			    System.out.print("\033[2K"); // Borra la línea
			    wait(1500);
			    memory.printList();
			    wait(1500);
			    System.out.print("\033[" + (3) + "B"); // Mueve el cursor hacia abajo
			    wait(1500);
			}
			
			// Inicia la planificación de los procesos
			int aux = quantum;
			System.out.println("\nInicia la planificación de los procesos ...");
			wait(1500);
			printQueues();
			Process running_process = memory.delete();
			System.out.println("Se planifica P"+running_process.getId());
			wait(1500);
			System.out.println("Cola de procesos listos para su ejecución:");
			memory.printList();
			
			int running_process_exec_time = running_process.getExecution_time_remaining();
			while(aux > 0 && running_process_exec_time > 0) {
				System.out.println("P"+running_process.getId()+" en ejecución "+running_process_exec_time+" msg");
				aux--;
				running_process_exec_time--;
				wait(1400);
			}
			
			if(running_process_exec_time > 0) {
				running_process.setExecution_time_remaining(running_process_exec_time);
				ready_queue.add(running_process);
				System.out.println("Terminó el quantum del proceso "+running_process.getId()+" restan "+running_process.getExecution_time_remaining()+"ms de ejecución");
				wait(1400);
				System.out.println("Proceso "+running_process.getId()+" agregado a la cola de procesos listos.");
			}else {
				System.out.println("Finalizó la ejecución del proceso "+running_process.getId()+".");
				wait(1400);
			}
			memory_size += running_process.getSize();
			System.out.println("Proceso "+running_process.getId()+" liberado de la memoria, quedan "+memory_size+" unidades de memoria");
			wait(1400);
			printQueues();
		}
		System.out.println("NO HAY MAS PROCESOS.");
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
	
	public static void deleteLine(int line) {
		 System.out.print("\033[" + line + "A"); // Mueve el cursor hacia arriba
	     System.out.print("\033[2K"); // Borra la línea
	     wait(1000);
	     System.out.println("Hola");
	     System.out.print("\033[" + (line-1) + "B"); // Mueve el cursor hacia abajo
	     System.out.println("Hola");
	     System.out.flush();
	}
	
}
