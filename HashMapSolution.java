import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import javax.sql.rowset.spi.SyncFactory;

public class HashMapSolution {
    public static ArrayList<Item> readcsv() {
        ArrayList<Item> result = new ArrayList<>();
        BufferedReader csvReader;
        String row;
        try {
            csvReader = new BufferedReader(new FileReader("./datasets/dataset_6.csv"));
            String headerString = csvReader.readLine();
            while ((row = csvReader.readLine()) != null) {
                Item temp = new Item(row.split(",")[0], Long.parseLong(row.split(",")[1]),
                        Long.parseLong(row.split(",")[2]));
                result.add(temp);
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException ioe) {
            System.out.println("ia ia ia");
        }
        return result;
    }

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

    public static long getValue(ArrayList<Item> data) {
        long result = 0L;
        for (Item i : data) {
            result += i.getValue();
        }
        return result;
    }

    public static long getWeight(ArrayList<Item> data) {
        long result = 0L;
        for (Item i : data) {
            result += i.getWeight();
        }
        return result;
    }

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

    public static void main(String[] args) {
        ArrayList<Item> data = readcsv();
        ArrayList<Long> weights = new ArrayList<>();
        ArrayList<Long> values = new ArrayList<>();
        HashMap<Integer, Item> dataMap = new HashMap<>();
        int count = 0;
        for (Item i : data) {
            weights.add(i.getWeight());
            values.add(i.getValue());
            dataMap.put(count, i);
        }

        // ArrayList<Integer> result = new ArrayList<>();
        long weight = 7;

        long answer = solution(weights.size() - 1, weight, weights.toArray(new Long[weights.size()]),
                values.toArray(new Long[values.size()]));

        HashMap<Long, ArrayList<Item>> hm = new HashMap<>();
        HashMap<Long, ArrayList<Item>> hm1 = new HashMap<>();
        ArrayList<Item> newCollection;

        data.sort(new ReverseWeightSorter());
        for (Item item : data) {
            if (hm.get(item.getWeight()) == null && item.getWeight() < weight) {
                hm.put(item.getWeight(), new ArrayList<Item>(List.of(item)));
            }
            hm1 = new HashMap<Long, ArrayList<Item>>(hm);
            for (long key : hm.keySet()) {
                if (!hm.get(key).contains(item)) {
                    // creating new collection by adding in the new item
                    newCollection = hm.get(key);
                    newCollection.add(item);

                    // calculating the new weight and value
                    long newWeight = getWeight(newCollection);
                    long newValue = getValue(newCollection);
                    System.out.println(newWeight);
                    System.out.println(newValue);
                    // checking if the new weight fits the max weight
                    if (newWeight <= weight) {
                        // check if the new weight already exists
                        // if not then just add
                        // else add only if the value is greater than the prev one
                        if (hm.get(newWeight) == null) {
                            hm1.put(newWeight, newCollection);
                        } else if (newValue > getValue(hm.get(newWeight))) {
                            hm1.put(newWeight, newCollection);
                        }
                    }
                }
            }
            hm = hm1;
        }
        System.out.println(hm.keySet());
        for (Item i : hm.get(7L)) {
            System.out.println(i.getName());
        }
    }
}
