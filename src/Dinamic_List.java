
//import proy_s.o.Process.*;

public class Dinamic_List {
	private Node node_head;
	private int size = 0;
	
	private static class Node{
		private Process item;
		private Node next;
		private Node prev;
		
		public Node(Process process) {
			this.item = process;
			this.next = null;
			this.prev = null;
			
		}
		
		public void setNext(Node next) {
			this.next = next;
		}
		
		public void setPrev(Node prev) {
			this.prev = prev;
		}
	}
	
	public Dinamic_List(Process process_head) {
		Node node = new Node(process_head);
		this.node_head = node;
	}
	
	public Dinamic_List() {
		this.node_head = null;
		this.size = 0;
	}

	public void add(Process process) {
		Node new_node = new Node(process);
		
		if(this.size <= 0) {
			this.node_head = new_node;
		} else {
			Node current_node = this.node_head;
			while(current_node.next != null) {
				current_node = current_node.next;
			}
			current_node.next = new_node;
			new_node.prev = current_node;
		}
		this.size++;
	}
	
	public Process delete() {
		Node node = node_head;
		
		if(node_head.next == null) {
			node_head = null;
		} else {
			node_head = node_head.next;
			node_head.prev = null;
		}
		this.size--;
		return node.item;
	}
	
	public void printList() {
		Node current_node = this.node_head;
		
		while(current_node != null) {
			System.out.print("["+current_node.item.getId()+"]");
			current_node = current_node.next;
		}
		System.out.println("");
	}
	
	public int getFirstNodeProcessSize() {
		if(node_head != null) {
			return node_head.item.getSize();
		}
		return -1;
	}
	
	public int getFirstNodeProcessId() {
		if(node_head != null) {
			return node_head.item.getId();
		}
		return -1;
	}
	
	public Node getNode_head() {
		return this.node_head;
	}

	public void setNode_head(Node node_head) {
		this.node_head = node_head;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}
