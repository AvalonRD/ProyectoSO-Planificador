// Clase para implementar una lista dinámica
public class Dynamic_List {
    private Node node_head;
    private int size;

    private static class Node {
        private Process process;
        private Node next;
        private Node prev;

        public Node(Process process) {
            this.process = process;
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

    public Dynamic_List() {
        this.node_head = null;
        this.size = 0;
    }

    public void add(Process process) {
        Node new_node = new Node(process);

        if (this.size <= 0) {
            this.node_head = new_node;
        } else {
            Node current_node = this.node_head;
            while (current_node.next != null) {
                current_node = current_node.next;
            }
            current_node.next = new_node;
            new_node.prev = current_node;
        }
        this.size++;
    }

    public Process delete() {
        Node node = node_head;

        if (node_head.next == null) {
            node_head = null;
        } else {
            node_head = node_head.next;
            node_head.prev = null;
        }
        this.size--;
        return node.process;
    }

    public void printList() {
        Node current_node = this.node_head;

        while (current_node != null) {
            System.out.print("[" + current_node.process.getId() + "]");
            current_node = current_node.next;
        }
        System.out.println("");
    }

    public int getFirstNodeProcessSize() {
        if (node_head != null) {
            return node_head.process.getSize();
        }
        return -1;
    }

    public int getFirstNodeProcessId() {
        if (node_head != null) {
            return node_head.process.getId();
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

    public boolean isEmpty() {
        return size == 0;
    }
}