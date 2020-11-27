import java.util.HashSet;

public class Combination {
    private HashSet<Item> items;
    private long weight;
    private long value;

    /**
     * Empty constructor
     */
    public Combination() {
        items = new HashSet<>();
        weight = 0;
        value = 0;
    }

    /**
     * Full-field constructor
     */
    public Combination(HashSet<Item> items, long weight, long value) {
        this.items = items;
        this.weight = weight;
        this.value = value;
    }

    public HashSet<Item> getItems() { return items; }

    public long getWeight() { return weight; }

    public long getValue() { return value; }

    public void setItems(HashSet<Item> items) { this.items = items; }

    public void setWeight(long totalWeight) { this.weight = totalWeight; }

    public void setValue(long totalValue) { this.value = totalValue; }

    public void add(Item item) {
        items.add(item);
        weight += item.getWeight();
        value += item.getValue();
    }
}
