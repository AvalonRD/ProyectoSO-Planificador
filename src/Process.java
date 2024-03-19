public class Process {
	private int id;
	private String name;
	private int size;
	private int execution_time;
	private int execution_time_remaining;
	private int priority;
	private int arrive_time;
	
	
	public Process(int id, String name, int size, int execution_time, int priority, int arrive_time) {
		this.id = id;
		this.name = name;
		this.size = size;
		this.execution_time = execution_time;
		this.execution_time_remaining = execution_time;
		this.priority = priority;
		this.arrive_time = arrive_time;
	}
	
	public Process() {}
	
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
