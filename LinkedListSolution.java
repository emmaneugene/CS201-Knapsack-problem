import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LinkedListSolution {
    public static void main(String[] args) {
        String csvFile = "datasets/dataset_100.csv";

        System.out.println("--------PROGRAM ANALYSIS--------");
        long limit = 100;
        LinkedList itemList = new LinkedList();
        long startTime = System.nanoTime();

        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory usage before algorithm (bytes): " + usedMemoryBefore);
    
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
            String line = "";
            csvReader.readLine();
            while ((line = csvReader.readLine()) != null) {
                String[] split = line.split(",");

                Item newItem = new Item(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                addCombi(itemList, newItem, limit);
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Node<Item> best = findBest(itemList);

        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory usage after algorithm (bytes): " + usedMemoryAfter);
        System.out.println("Memory used (bytes): " + (usedMemoryAfter-usedMemoryBefore));

        long stopTime = System.nanoTime();
        System.out.println("Time taken (milliseconds): " + ((stopTime - startTime) / 1000000));

        System.out.println("\n\n--------PROGRAM RESULTS--------");
        long bestW = best.getItem().getWeight();
        long bestV = best.getItem().getValue();

        System.out.println("Optimal combination: " + best.getItem().getName());
        System.out.println("Weight: " + bestW);
        System.out.println("Value: " + bestV);
    }

    private static void addCombi(LinkedList itemList, Item newItem, long limit) {
        // add new item to the list
        itemList.addLast(newItem);

        // Get weight and value of new item
        // int newItemId = newItem.getId();
        long newItemW = newItem.getWeight();
        long newItemV = newItem.getValue();

        // Walk through linked list
        Node<Item> walk = itemList.getHead();
        while (walk.getItem() != newItem) {
            // Current item in the list
            Item currItem = walk.getItem();

            // Get current weight and value of item in the list
            String currItemN = currItem.getName();
            long currItemW = currItem.getWeight();
            long currItemV = currItem.getValue();

            // Check if the total weight of new Item and current item is lesser than limit,
            // add to the combination to the list
            if (limit >= currItemW + newItemW) {
                Item toAdd = new Item(currItemN + ", " + newItem.getName(), currItemW + newItemW, currItemV + newItemV);
                itemList.addLast(toAdd);
            }

            walk = walk.getNext();
        }
    }

    private static Node<Item> findBest(LinkedList itemList) {
        Node<Item> walk = itemList.getHead();
        Node<Item> best = walk;

        while (walk != null) {
            if (walk.getItem().getValue() > best.getItem().getValue()) {
                best = walk;
            }
            walk = walk.getNext();
        }

        return best;
    }
}
