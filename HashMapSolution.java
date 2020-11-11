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

    public static long getTotalValue(ArrayList<Item> data) {
        long result = 0L;
        for (Item i : data) {
            result += i.getValue();
        }
        return result;
    }

    public static HashMap<String, Long> map = new HashMap<>();

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

        ArrayList<Integer> result = new ArrayList<>();
        long weight = 7;

        long answer = solution(weights.size() - 1, weight, weights.toArray(new Long[weights.size()]),
                values.toArray(new Long[values.size()]));

        // System.out.println(answer);
        System.out.println(map);

        // Step 1: find weight that corresponds to answer
        long currWeight = 0L;
        int tempIndex = 0;
        long tempCurrWeight;
        long tempAnswer;
        for (String key : map.keySet()) {
            if (map.get(key) == answer) {
                tempIndex = (int) Long.parseLong(key.split(" --> ")[0]);
                currWeight = (long) Long.parseLong(key.split(" --> ")[1]) - weights.get(tempIndex);
                answer -= values.get(tempIndex);
                break;
            }
        }

        System.out.println(currWeight);
        System.out.println(answer);

        for (String key : map.keySet()) {
            if (map.get(key) == answer) {
                tempIndex = (int) Long.parseLong(key.split(" --> ")[0]);
                tempCurrWeight = (long) Long.parseLong(key.split(" --> ")[1]);
                tempAnswer = map.get(key);
                System.out.println(values.get(tempIndex) + " must equals " + tempAnswer);
                if (weights.get(tempIndex) == tempCurrWeight && values.get(tempIndex) == tempAnswer) {
                    answer -= tempAnswer;
                    currWeight -= tempCurrWeight;
                    result.add((int) tempIndex);
                }
            }
        }

        // Step 2: while loop to find the others
        // while (answer > 0 || currWeight > 0) {
        // for (String key : map.keySet()) {
        // long tempCurrWeight = Long.parseLong(key.split(" --> ")[1]);
        // tempIndex = (int) Integer.parseInt(key.split(" --> ")[0]);
        // if (values.get(tempIndex) == map.get(key) && weights.get(tempIndex) ==
        // tempCurrWeight) {
        // System.out.println(map.get(key) + " must equals " + values.get(tempIndex));
        // System.out.println(tempCurrWeight + " must equals " +
        // weights.get(tempIndex));
        // answer -= values.get(tempIndex);
        // currWeight -= weights.get(tempIndex);
        // result.add(tempIndex);
        // }
        // }
        // }

        // Step 3: print the answer
        System.out.println(result);
    }
}
