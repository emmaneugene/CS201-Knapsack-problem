public class Node<Combination> {
    private Combination combi;
    private Node<Combination> next;

    public Node(Combination combi, Node<Combination> next) {
        this.combi = combi;
        this.next = next;
    }

    public Combination getCombi() {
        return this.combi;
    }

    public Node<Combination> getNext() {
        return this.next;
    }

    public void setNext(Node<Combination> next) {
        this.next = next;
    }
}
