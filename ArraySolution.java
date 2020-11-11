import java.util.*;
import java.io.*;

public class ArraySolution {
    public static void main(String[] args) {
        // Get user input
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a file path: ");
        String csvFile = sc.next();
        System.out.print("Enter weight limit: ");
        long limit = sc.nextLong();

        // Initialise measurement variables
        long startTime = System.nanoTime();
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
    
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

        // Insert algorithm here
        Combination best = knapsackSolve(items, limit);

        // End measurements
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

    private static Combination knapsackSolve(List<Item> items, long limit) {
        ArrayList<Item> itemsWithinLimit = new ArrayList<>();
        for (Item item : items) {
            if (item.getWeight() <= limit) {
                itemsWithinLimit.add(item);
            }
        }
        
        Combination[] combinations = new Combination[(int)limit + 1];
        
        for(Item item : itemsWithinLimit) {        
            Combination[] newCombinations = cloneArray(combinations);
            for(int w = 0; w <= limit - item.getWeight(); w++) {
                if (combinations[w] != null) {
                    Combination newCombination = clone(combinations[w]);
                    newCombination.add(item);
                    addCombination(newCombination, newCombinations);
                }
            }
            Combination newCombination = new Combination();
            addCombination(newCombination, newCombinations);
            combinations = newCombinations;
        }
        return findBestCombination(combinations);
    }

    private static void addCombination(Combination combination, Combination[] combinations) {
        int idx = (int)combination.getWeight();
        if (combinations[idx] == null
            || combinations[idx].getValue() < combination.getValue());
            combinations[idx] = combination;
    }

    private static Combination[] cloneArray(Combination[] combinations) {
        int len = combinations.length;
        Combination[] clone = new Combination[len];

        for (int i = 0; i < len; i++) {
            if (combinations[i] != null)
                clone[i] = clone(combinations[i]);
        }

        return clone;
    }

    public static Combination clone(Combination combination) {
        Combination clone = new Combination();
        for (Item item : combination.getItems())
            clone.add(item);
        
        return clone;
    }

    private static Combination findBestCombination(Combination[] combinations) {
        int len = combinations.length;       
        Combination best = new Combination();

        for (int i = 0; i < len; i++) {
            if (combinations[i] != null && combinations[i].getValue() > best.getValue())
                best = combinations[i];
        }
        return best;
    }

    
}
