import java.util.Scanner;

class Node {
    int data;
    Node next, prev;

    public Node(int x) {
        this.data = x;
        this.prev = this.next = null;
    }
}

class DoublyLinkedList {
    Node head;

    public DoublyLinkedList() {
        this.head = null;
    }

    public void add(int data) {
        if (head == null) {
            head = new Node(data);
        } else {
            Node v = head;
            while (v.next != null)
                v = v.next;
            v.next = new Node(data);
            v.next.prev = v;
        }
    }

    public void remove(int data) {
        if (head == null)
            return;

        if (head.data == data) {
            head = head.next;
            if (head != null)
                head.prev = null;
            return;
        }

        Node v = head;
        while (v.next != null) {
            if (v.next.data == data) {
                v.next = v.next.next;
                if (v.next != null)
                    v.next.prev = v;
                return;
            }
            v = v.next;
        }
    }

    public void display() {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) {
        DoublyLinkedList list = new DoublyLinkedList();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Menu:
            // 1. Add
            // 2. Remove
            // 3. Display
            // 4. Exit
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    int valueToAdd = scanner.nextInt();
                    list.add(valueToAdd);
                    break;
                case 2:
                    int valueToRemove = scanner.nextInt();
                    list.remove(valueToRemove);
                    break;
                case 3:
                    list.display();
                    break;
                case 4:
                    scanner.close();
                    System.exit(0);
            }
        }
    }
}