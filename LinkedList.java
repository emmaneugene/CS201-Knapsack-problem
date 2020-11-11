public class LinkedList {
    Node<Combination> head = null;
    Node<Combination> tail = null;
    int size = 0;

    public LinkedList() {
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Node<Combination> getHead() {
        return head;
    }

    public Combination first() {
        if (isEmpty()) {
            return null;
        }
        return head.getCombi();
    }

    public Combination last() {
        if (isEmpty()) {
            return null;
        }
        return tail.getCombi();
    }

    public void addFirst(Combination combi) {
        head = new Node<Combination>(combi, null);
        if (isEmpty()) {
            tail = head;
        }
        size++;
    }

    public void addLast(Combination combi) {
        Node<Combination> newest = new Node<Combination>(combi, null);
        if (isEmpty()) {
            head = newest;
        } else {
            tail.setNext(newest);
        }
        tail = newest;
        size++;
    }

    public Combination removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Combination answer = head.getCombi();
        head = head.getNext();
        size--;
        if (size == 0) {
            tail = null;
        }
        return answer;
    }

    public Combination removeLast() {
        if (isEmpty()) {
            return null;
        }
        Combination answer = tail.getCombi();
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            Node<Combination> walk = head;
            while (walk.getNext() != tail) {
                walk.getNext();
            }
            walk.setNext(null);
            tail = walk;
        }
        size--;
        return answer;
    }
}
