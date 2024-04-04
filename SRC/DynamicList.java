// Clase para implementar una lista dinámica
public class DynamicList {
    private Node head;
    private int size;

    private static class Node {
        private Process process;
        private Node next;

        public Node(Process process) {
            this.process = process;
            this.next = null;
        }
    }

    public DynamicList() {
        this.head = null;
        this.size = 0;
    }

    public void enqueue(Process process) {
        Node newNode = new Node(process);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public Process dequeue() {
        if (head == null) {
            return null;
        }
        Process process = head.process;
        head = head.next;
        size--;
        return process;
    }

    public Process peek() {
        if (head == null) {
            return null;
        }
        return head.process;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}