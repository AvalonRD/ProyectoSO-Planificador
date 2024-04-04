// Clase para implementar una lista dinámica
public class Dinamic_List {
    // Nodo inicial de la lista
    private Node node_head;
    // Tamaño de la lista
    private int size = 0;

    // Clase interna para representar un nodo de la lista
    private static class Node {
        // Elemento almacenado en el nodo
        private Process item;
        // Referencia al siguiente nodo en la lista
        private Node next;
        // Referencia al nodo anterior en la lista
        private Node prev;

        // Constructor de la clase Node
        public Node(Process process) {
            this.item = process;
            this.next = null;
            this.prev = null;
        }

        // Métodos getter y setter para el siguiente nodo
        public void setNext(Node next) {
            this.next = next;
        }

        public Node getNext() {
            return next;
        }

        // Métodos getter y setter para el nodo anterior
        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public Node getPrev() {
            return prev;
        }
    }

    // Constructor que inicializa la lista con un nodo inicial dado
    public Dinamic_List(Process process_head) {
        Node node = new Node(process_head);
        this.node_head = node;
    }

    // Constructor que inicializa una lista vacía
    public Dinamic_List() {
        this.node_head = null;
        this.size = 0;
    }

    // Método para agregar un proceso al final de la lista
    public void add(Process process) {
        // Crear un nuevo nodo con el proceso dado
        Node new_node = new Node(process);

        // Verificar si la lista está vacía
        if (this.size <= 0) {
            // Si la lista está vacía, el nuevo nodo se convierte en el nodo inicial
            this.node_head = new_node;
        } else {
            // Si la lista no está vacía, se recorre la lista hasta llegar al último nodo
            Node current_node = this.node_head;
            while (current_node.getNext() != null) {
                current_node = current_node.getNext();
            }
            // Se enlaza el nuevo nodo al último nodo de la lista
            current_node.setNext(new_node);
            new_node.setPrev(current_node);
        }
        // Se incrementa el tamaño de la lista
        this.size++;
    }

    // Método para eliminar y devolver el primer proceso de la lista
    public Process delete() {
        // Obtener una referencia al nodo inicial de la lista
        Node node = node_head;

        // Verificar si el nodo inicial tiene un siguiente nodo
        if (node_head.getNext() == null) {
            // Si el nodo inicial no tiene un siguiente nodo, la lista se vacía
            node_head = null;
        } else {
            // Si el nodo inicial tiene un siguiente nodo, se actualiza el nodo inicial
            node_head = node_head.getNext();
            node_head.setPrev(null);
        }
        // Se decrementa el tamaño de la lista
        this.size--;
        // Se devuelve el proceso almacenado en el nodo eliminado
        return node.item;
    }

    // Método para imprimir los elementos de la lista
    public void printList() {
        // Inicializar un nodo actual para recorrer la lista desde el nodo inicial
        Node current_node = this.node_head;

        // Recorrer la lista e imprimir los ID de los procesos almacenados en cada nodo
        while (current_node != null) {
            System.out.print("[" + current_node.item.getId() + "]");
            current_node = current_node.getNext();
        }
        // Imprimir una nueva línea para formatear la salida
        System.out.println("");
    }

    // Método para verificar si la lista está vacía
    public boolean isEmpty() {
        return size == 0 && node_head == null;
    }

    // Método para obtener el tamaño del primer proceso en la lista
    public int getFirstNodeProcessSize() {
        // Verificar si la lista no está vacía
        if (node_head != null) {
            // Si la lista no está vacía, devolver el tamaño del proceso almacenado en el nodo inicial
            return node_head.item.getSize();
        }
        // Si la lista está vacía, devolver -1 para indicar que no hay procesos
        return -1;
    }

    // Método para obtener el ID del primer proceso en la lista
    public int getFirstNodeProcessId() {
        // Verificar si la lista no está vacía
        if (node_head != null) {
            // Si la lista no está vacía, devolver el ID del proceso almacenado en el nodo inicial
            return node_head.item.getId();
        }
        // Si la lista está vacía, devolver -1 para indicar que no hay procesos
        return -1;
    }

    // Método getter para obtener el nodo inicial de la lista
    public Node getNode_head() {
        return this.node_head;
    }

    // Método setter para establecer el nodo inicial de la lista
    public void setNode_head(Node node_head) {
        this.node_head = node_head;
    }

    // Método getter para obtener el tamaño de la lista
    public int getSize() {
        return size;
    }

    // Método setter para establecer el tamaño de la lista
    public void setSize(int size) {
        this.size = size;
    }
}
