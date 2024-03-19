// Clase para representar un proceso
public class Process {
    // Atributos de un proceso
    private int id; // Identificador único del proceso
    private String name; // Nombre del proceso
    private int size; // Tamaño del proceso
    private int execution_time; // Tiempo total de ejecución del proceso
    private int execution_time_remaining; // Tiempo restante de ejecución del proceso
    private int priority; // Prioridad del proceso
    private int arrive_time; // Tiempo de llegada del proceso

    // Constructor que inicializa todos los atributos de un proceso
    public Process(int id, String name, int size, int execution_time, int priority, int arrive_time) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.execution_time = execution_time;
        this.execution_time_remaining = execution_time;
        this.priority = priority;
        this.arrive_time = arrive_time;
    }

    // Constructor por defecto
    public Process() {}

    // Métodos getter y setter para cada atributo del proceso
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getExecution_time() {
        return execution_time;
    }
    public void setExecution_time(int execution_time) {
        this.execution_time = execution_time;
        setExecution_time_remaining(execution_time);
    }
    public int getExecution_time_remaining() {
        return execution_time_remaining;
    }
    public void setExecution_time_remaining(int execution_time_remaining) {
        this.execution_time_remaining = execution_time_remaining;
    }
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public int getArrive_time() {
        return arrive_time;
    }
    public void setArrive_time(int arrive_time) {
        this.arrive_time = arrive_time;
    }
}
