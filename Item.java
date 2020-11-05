public class Item {
    private long weight;
    private long value;

    Item (long weight, long value) {
        this.weight = weight;
        this.value = value;
    }

    public long getWeight() {
        return weight;
    }

    public long getValue() {
        return value;
    }
}
