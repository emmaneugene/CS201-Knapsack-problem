import java.util.HashSet;

public class ItemCollection {
    private HashSet<Item> items;
    private long totalWeight;
    private long totalValue;

    /**
     * Empty constructor
     */
    public ItemCollection() {
        items = new HashSet<>();
        totalWeight = 0;
        totalValue = 0;
    }

    /**
     * Full-field constructor
     */
    public ItemCollection(HashSet<Item> items, long totalWeight, long totalValue) {
        this.items = items;
        this.totalWeight = totalWeight;
        this.totalValue = totalValue;
    }

    public HashSet<Item> getItemNames() { return items; }

    public long getTotalWeight() { return totalWeight; }

    public long getTotalValue() { return totalValue; }

    public void setItems(HashSet<Item> items) { this.items = items; }

    public void setTotalWeight(long totalWeight) { this.totalWeight = totalWeight; }

    public void setTotalValue(long totalValue) { this.totalValue = totalValue; }

    public void addItem(Item item) {
        items.add(item);
        totalWeight += item.getWeight();
        totalValue += item.getValue();
    }
}
