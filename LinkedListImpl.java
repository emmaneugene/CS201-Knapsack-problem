import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LinkedListImpl {
    public static void main(String[] args) {
        String csvFile = "datasets/dataset_100.csv";

        long limit = 100;
        LinkedList itemList = new LinkedList();
        long startTime = System.nanoTime();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
            String line = "";
            csvReader.readLine();
            while ((line = csvReader.readLine()) != null) {
                String[] split = line.split(",");
                // int[] data =
                // Arrays.asList(split).stream().mapToInt(Integer::parseInt).toArray();

                // if (Integer.parseInt(split[5]) != 0) {
                // Item newItem = new Item(split[0], Integer.parseInt(split[1]),
                // Integer.parseInt(split[5]));
                // addCombi(itemList, newItem, limit);
                // }

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
        long stopTime = System.nanoTime();
        System.out.println("Time taken (mms): " + ((stopTime - startTime) / 1000000));

        long bestW = best.getItem().getWeight();
        long bestV = best.getItem().getValue();

        System.out.println(best.getItem().getName());
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

        while (walk.getNext() != null) {
            if (walk.getItem().getValue() > best.getItem().getValue()) {
                best = walk;
            }
            walk = walk.getNext();
        }

        Node<Item> lastItem = walk;
        if (lastItem.getItem().getValue() > best.getItem().getValue()) {
            best = lastItem;
        }

        return best;
    }
}
