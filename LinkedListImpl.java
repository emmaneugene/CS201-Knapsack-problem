import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LinkedListImpl {
    public static void main(String[] args) {
        String csvFile = "datasets/dataset_6.csv";

        long limit = 100;
        LinkedList itemList = new LinkedList();

        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
            String line = "";
            csvReader.readLine();
            while ((line = csvReader.readLine()) != null) {
                String[] split = line.split(",");
                int[] data = Arrays.asList(split).stream().mapToInt(Integer::parseInt).toArray();

                Item newItem = new Item(data[1], data[2]);
                System.out.println(data[1] + " " + data[2]);
                addCombi(itemList, newItem, limit);
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Node<Item> walk = itemList.getHead();
        Item best = walk.getItem();
        while (walk.getNext() != null) {
            Item i = walk.getItem();
            if (best.getValue() < i.getValue()) {
                best = walk.getItem();
            }
            walk = walk.getNext();
        }
        
        Item i = walk.getItem();
        if (best.getValue() < i.getValue()) {
            best = i;
        }

        System.out.println("Weight: " + best.getWeight());
        System.out.println("Value: " + best.getValue());
    }

    private static void addCombi(LinkedList itemList, Item newItem, long limit) {
        // add item to the list
        itemList.addLast(newItem);

        // int newItemId = newItem.getId();
        long newItemW = newItem.getWeight();
        long newItemV = newItem.getValue();

        Node<Item> walk = itemList.getHead();

        while (walk.getItem() != newItem) {
            Item currItem = walk.getItem();

            // int currItemId = currItem.getId();
            long currItemW = currItem.getWeight();
            long currItemV = currItem.getValue();

            if (limit >= currItemW + newItemW) {
                Item toAdd = new Item(currItemW + newItemW, currItemV + newItemV);
                itemList.addLast(toAdd);
            }

            walk = walk.getNext();
        }
    }
}
