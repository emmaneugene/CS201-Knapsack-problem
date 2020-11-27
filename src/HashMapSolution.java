import java.util.*;
import java.io.*;

public class HashMapSolution {
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

                Item newItem = new Item(components[0], Integer.parseInt(components[1]), Integer.parseInt(components[2]));
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
        System.out.println("Memory used (KB): " + (usedMemoryAfter-usedMemoryBefore) / 1000);
        System.out.println("Time taken (milliseconds): " + ((stopTime - startTime) / 1000000));
    }

    public static Combination knapsackSolve(List<Item> items, long limit) {
        ArrayList<Item> itemsWithinLimit = new ArrayList<>();
        for (Item item : items) {
            if (item.getWeight() <= limit) {
                itemsWithinLimit.add(item);
            }
        }

        HashMap<Long, Combination> hm = new HashMap<>();

        for (Item item : itemsWithinLimit) {
            Collection<Combination> validCombinations = hm.values();
            
            ArrayList<Combination> newCombinations = new ArrayList<>();

            for (Combination combination : validCombinations) {
                if (combination.getWeight() + item.getWeight() <= limit) {
                    Combination newCombination = clone(combination);
                    newCombination.add(item);
                    newCombinations.add(newCombination);
                }
            }  

            // Add the item by itself  
            Combination newCollection = new Combination();
            newCollection.add(item);
            newCombinations.add(newCollection);

            for (Combination toAdd : newCombinations) {
                addCombinationToMap(toAdd, hm);
            }
        }

        return findBestCombination(hm);
    }

    public static void addCombinationToMap(Combination combination, HashMap<Long, Combination> hm) {
        Long key = combination.getWeight();
        if (!hm.containsKey(key)
            || (hm.containsKey(key) && hm.get(key).getValue() < combination.getValue())) 
            hm.put(key, combination); 
    }

    public static Combination findBestCombination(HashMap<Long, Combination> hm) {
        Combination best = new Combination();

        Collection<Combination> combinations = hm.values();
        for (Combination combination : combinations) {
            if (combination.getValue() > best.getValue()
                || (combination.getValue() == best.getValue() && combination.getWeight() < best.getWeight()))
                best = combination;
            
        }

        return best;
    }

    public static Combination clone(Combination combination) {
        Combination clone = new Combination();
        for (Item item : combination.getItems())
            clone.add(item);
        
        return clone;
    }
}