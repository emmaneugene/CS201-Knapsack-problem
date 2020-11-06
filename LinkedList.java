public class LinkedList {
    Node<Item> head = null;
    Node<Item> tail = null;
    int size = 0;

    public LinkedList() {
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Node<Item> getHead() {
        return head;
    }

    public Item first() {
        if (isEmpty()) {
            return null;
        }
        return head.getItem();
    }

    public Item last() {
        if (isEmpty()) {
            return null;
        }
        return tail.getItem();
    }

    public void addFirst(Item item) {
        head = new Node<Item>(item, null);
        if (isEmpty()) {
            tail = head;
        }
        size++;
    }

    public void addLast(Item item) {
        Node<Item> newest = new Node<Item>(item, null);
        if (isEmpty()) {
            head = newest;
        } else {
            tail.setNext(newest);
        }
        tail = newest;
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Item answer = head.getItem();
        head = head.getNext();
        size--;
        if (size == 0) {
            tail = null;
        }
        return answer;
    }

    public Item removeLast() {
        if (isEmpty()) {
            return null;
        }
        Item answer = tail.getItem();
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            Node<Item> walk = head;
            while (walk.getNext() != tail) {
                walk.getNext();
            }
            walk.setNext(null);
            tail = walk;
        }
        size--;
        return answer;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        Node<Item> walk = head;
        while (walk != null) {
            sb.append(walk.getItem().getWeight());
            sb.append(" ");
            sb.append(walk.getItem().getValue());
            if (walk != tail)
                sb.append(", ");
            walk = walk.getNext();
        }
        sb.append(")");
        return sb.toString();
    }
}
