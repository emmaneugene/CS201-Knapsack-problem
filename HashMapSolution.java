import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class HashMapSolution {
    static class WeightSorter implements Comparator<Item> {
        @Override
        public int compare(Item i1, Item i2) {
            return Long.compare(i1.getWeight(), i2.getWeight());
        }
    }

    static class ReverseWeightSorter implements Comparator<Item> {
        @Override
        public int compare(Item i1, Item i2) {
            return Long.compare(i2.getWeight(), i1.getWeight());
        }
    }

    // public static long getValue(Combination data) {
    // long result = 0L;
    // for (Item i : data) {
    // result += i.getValue();
    // }
    // return result;
    // }

    // public static long getWeight(Combination data) {
    // long result = 0L;
    // for (Item i : data) {
    // result += i.getWeight();
    // }
    // return result;
    // }

    public static HashMap<String, Long> map = new HashMap<>();

    public static ArrayList<Integer> result = new ArrayList<>();

    public static long solution(int index, long weight, Long[] weights, Long[] values) {
        if (index < 0 || weight == 0) {
            return 0L;
        }

        String key = index + " --> " + weight;
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            if (weights[index] > weight) {
                map.put(key, solution(index - 1, weight, weights, values));
            } else {
                long include = solution(index - 1, weight - weights[index], weights, values) + values[index];
                long exclude = solution(index - 1, weight, weights, values);
                if (include > exclude) {
                    map.put(key, include);
                    result.add(index);
                } else {
                    map.put(key, exclude);
                }
            }
            return map.get(key);
        }
    }

    public static Combination knapsackSolve(List<Item> items, long limit) {
        HashMap<Long, Combination> hm = new HashMap<>();
        items.sort(new ReverseWeightSorter());

        for (Item item : items) {
            if (item.getValue() < limit) {
                HashMap<Long, Combination> tempHM = new HashMap<>();
                System.out.println(hm.keySet().toString());
                for (Long key : hm.keySet()) {
                    if (hm.get(key).getWeight() + item.getWeight() < limit) {
                        Combination tempCombination = hm.get(key);
                        tempCombination.add(item);
                        tempHM.put(key + item.getWeight(), tempCombination);
                    }
                }
                for (Long key : tempHM.keySet()) {
                    if ((!hm.containsKey(key))
                            || (hm.containsKey(key) && hm.get(key).getValue() < tempHM.get(key).getValue())) {
                        hm.put(key, tempHM.get(key));
                    }
    
                }
                Combination tempCombination = new Combination();
                tempCombination.add(item);
                Long newKey = tempCombination.getWeight();
                Long newValue = tempCombination.getValue();
                if ((!hm.containsKey(newKey)) || (hm.containsKey(newKey) && hm.get(newKey).getValue() < newValue)) {
                    hm.put(newKey, tempCombination);
                }
            }
        }

        Combination result = new Combination();
        Long highestValue = 0L;
        for (Combination combi : hm.values()) {
            if (combi.getValue() > highestValue) {
                highestValue = combi.getValue();
                result = combi;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        // Get user input
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a file path: ");
        String csvFile = sc.next();
        // String csvFile = "/Users/shrmnl/Github/CS201-G1T2/datasets/dataset_real_world.csv";
        System.out.print("Enter weight limit: ");
        long limit = sc.nextLong();
        // long limit = 200L;

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
}
