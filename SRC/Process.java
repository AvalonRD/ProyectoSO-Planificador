// Clase para representar un proceso
public class Process {
    // Atributos de un proceso
    private int id;
    private String name;
    private int size;
    private int execution_time;
    private int execution_time_remaining;
    private int priority;
    private int arrive_time;
    private int first_top;
    private int last_execution_time;
    private int waitingTime; // Tiempo de espera
    private int responseTime; // Tiempo de respuesta
    private int turnaroundTime; // Tiempo de ejecución

    // Constructor que inicializa todos los atributos de un proceso
    public Process(int id, String name, int size, int execution_time, int priority, int arrive_time) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.execution_time = execution_time;
        this.execution_time_remaining = execution_time;
        this.priority = priority;
        this.arrive_time = arrive_time;
        this.first_top = 0;
        this.last_execution_time = 0;
        this.waitingTime = 0;
        this.responseTime = 0;
        this.turnaroundTime = 0;
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
    public void setFirst_top(int first_top){
        this.first_top = first_top;
    }
    public int getFirst_top(){
        return first_top;
    }
    public void setLast_execution_time(int last_execution_time){
        this.last_execution_time = last_execution_time;
    }
    public int getLast_execution_time(){
        return last_execution_time;
    }
    public int getWaitingTime() {
        return waitingTime;
    }
    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
    public int getResponseTime() {
        return responseTime;
    }
    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }
    public int getTurnaroundTime() {
        return turnaroundTime;
    }
    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }
}
