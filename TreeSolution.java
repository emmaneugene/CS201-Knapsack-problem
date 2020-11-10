import java.util.*;
import java.io.*;

public class TreeSolution {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a file path: ");
        String csvFile = sc.next();
        System.out.print("Enter weight limit: ");
        long limit = sc.nextLong();

        System.out.println("--------PROGRAM ANALYSIS--------");
        long startTime = System.nanoTime();

        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory usage before algorithm (bytes): " + usedMemoryBefore);

        // Read file data and generate a list of items
        ArrayList<Item> items = new ArrayList<>();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
            String line = "";
            csvReader.readLine();
            while ((line = csvReader.readLine()) != null) {
                String[] components = line.split(",");

                Item newItem = new Item(components[0], Integer.parseInt(components[1]), Integer.parseInt(components[2]));
                items.add(newItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert algorithm here
        ItemCollection best = knapsackSolve(items, limit);
        System.out.println("Best combination: " + best.getItems());
        System.out.println("Weight: " + best.getTotalWeight());
        System.out.println("Value: " + best.getTotalValue());

        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory usage after algorithm (bytes): " + usedMemoryAfter);
        System.out.println("Memory used (bytes): " + (usedMemoryAfter-usedMemoryBefore));

        long stopTime = System.nanoTime();
        System.out.println("Time taken (milliseconds): " + ((stopTime - startTime) / 1000000));
    }

    public static ItemCollection knapsackSolve(List<Item> items, long limit) {
        ArrayList<Item> itemsWithinLimit = new ArrayList<>();
        for (Item item : items) {
            if (item.getWeight() <= limit) {
                itemsWithinLimit.add(item);
            }
        }

        TreeMap<Long, ItemCollection> tree = new TreeMap<>();

        for (Item item : itemsWithinLimit) {
            Collection<ItemCollection> validCollections = tree.subMap(Long.valueOf(0), limit - item.getWeight()).values();

            ArrayList<ItemCollection> newCollections = new ArrayList<>();

            for (ItemCollection collection : validCollections) {
                ItemCollection newCollection = clone(collection);
                newCollection.addItem(item);
                newCollections.add(newCollection);
            }  

            // Add the item by itself  
            ItemCollection newCollection = new ItemCollection();
            newCollection.addItem(item);
            newCollections.add(newCollection);

            for (ItemCollection toAdd : newCollections) {
                addCollectionToTree(toAdd, tree);
            }

            
            addCollectionToTree(newCollection, tree);
        }

        return findBestCollection(tree);
    }

    public static void addCollectionToTree(ItemCollection collection, TreeMap<Long, ItemCollection> tree) {
        Long key = collection.getTotalWeight();
        if (tree.containsKey(key) && tree.get(key).getTotalValue() < collection.getTotalValue()) {
            tree.put(key, collection);
        } else {
            tree.put(key, collection);
        }
    }

    public static ItemCollection findBestCollection(TreeMap<Long, ItemCollection> tree) {
        if (tree.isEmpty()) return null;

        ItemCollection best = tree.firstEntry().getValue();
        Iterable<ItemCollection> collections = tree.values();
        for (ItemCollection collection : collections) {
            if (collection.getTotalValue() > best.getTotalValue())
                best = collection;
        }

        return best;
    }

    public static ItemCollection clone(ItemCollection collection) {
        ItemCollection clone = new ItemCollection();
        
        for (Item item : collection.getItems())
            clone.addItem(item);

        return clone;
    }
}
