import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class LinkedListSolution {
    public static void main(String[] args) {
        // Get user input
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a file path: ");
        String csvFile = sc.next();
        System.out.print("Enter weight limit: ");
        long limit = sc.nextLong();

        // Read file data
        ArrayList<Item> items = new ArrayList<>();
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
            String line = "";
            csvReader.readLine();
            while ((line = csvReader.readLine()) != null) {
                String[] components = line.split(",");

                Item newItem = new Item(components[0], Integer.parseInt(components[1]),
                        Integer.parseInt(components[2]));
                items.add(newItem);
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialise measurement variables
        long startTime = System.nanoTime();
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();

        // Insert algorithm here
        Combination best = knapsackSolve(items, limit);

        // End measurement
        long stopTime = System.nanoTime();
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();

        // Results and diagnostics
        System.out.println("--------PROGRAM RESULTS--------");
        System.out.println("Best combination: " + best.getItems());
        System.out.println("Weight: " + best.getWeight());
        System.out.println("Value: " + best.getValue());

        System.out.println("\n\n--------PROGRAM ANALYSIS--------");
        System.out.println("Memory usage before algorithm (KB): " + usedMemoryBefore / 1000);
        System.out.println("Memory usage after algorithm (KB): " + usedMemoryAfter / 1000);
        System.out.println("Memory used (KB): " + (usedMemoryAfter - usedMemoryBefore) / 1000);
        System.out.println("Time taken (milliseconds): " + ((stopTime - startTime) / 1000000));
    }

    public static Combination knapsackSolve(List<Item> items, long limit) {
        // Get items within limit
        ArrayList<Item> itemsWithinLimit = new ArrayList<>();
        for (Item item : items) {
            if (item.getWeight() <= limit) {
                itemsWithinLimit.add(item);
            }
        }

        // Add items that are within limits into Linked List algorithm
        LinkedList linkedList = new LinkedList();
        for (Item item : itemsWithinLimit) {
            addCombi(linkedList, item, limit);
        }

        //
        return findBest(linkedList);
    }

    private static void addCombi(LinkedList itemList, Item newItem, long limit) {
        // Get weight of new item
        long newItemW = newItem.getWeight();

        // add new item to the list
        Combination newCombi = new Combination();
        newCombi.add(newItem);
        itemList.addLast(newCombi);

        // Walk through linked list
        Node<Combination> walk = itemList.getHead();

        while (walk.getCombi() != newCombi) {
            // Current item in the list
            Combination currCombi = walk.getCombi();

            // Get current weight of item in the list
            long currCombiW = currCombi.getWeight();

            // Check if the total weight of new Item and current item is lesser than limit,
            // add to the combination to the list
            if (limit >= currCombiW + newItemW) {
                Combination toAdd = clone(currCombi);
                toAdd.add(newItem);
                itemList.addLast(toAdd);
            }
            walk = walk.getNext();
        }
    }

    // Cloning of combination
    public static Combination clone(Combination combination) {
        Combination clone = new Combination();
        for (Item item : combination.getItems())
            clone.add(item);
        
        return clone;
    }

    // Find the best result in the Linked List
    private static Combination findBest(LinkedList linkedList) {
        if (linkedList.size() == 0) {
            return new Combination();
        }
        
        Node<Combination> walk = linkedList.getHead();
        Node<Combination> best = walk;

        while (walk != null) {
            if (walk.getCombi().getValue() > best.getCombi().getValue()) {
                best = walk;
            }
            walk = walk.getNext();
        }
        return best.getCombi();
    }
}
