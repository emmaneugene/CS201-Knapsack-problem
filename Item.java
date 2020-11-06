public class Item {
    //private int id;
    private long weight;
    private long value;

    public Item(long weight, long value) {
        //this.id = id;
        this.weight = weight;
        this.value = value;
    }

    // public int getId() {
    //     return id;
    // }

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
