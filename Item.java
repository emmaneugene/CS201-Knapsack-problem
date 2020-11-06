public class Item {
    private String name;
    private long weight;
    private long value;

    public Item(String name, long weight, long value) {
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public long getWeight() {
        return weight;
    }

    public long getValue() {
        return value;
    }

    public boolean equals(Item b) {
        if (this.weight != b.getWeight() || this.getValue() != b.getValue()) {
            return false;
        }
        return true;
    }
}
