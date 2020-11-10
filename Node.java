public class Node<Item> {
    private Item item;
    private Node<Item> next;

    public Node(Item item, Node<Item> next) {
        this.item = item;
        this.next = next;
    }

    public Item getItem() {
        return this.item;
    }

    public Node<Item> getNext() {
        return this.next;
    }

    public void setNext(Node<Item> next) {
        this.next = next;
    }
}
