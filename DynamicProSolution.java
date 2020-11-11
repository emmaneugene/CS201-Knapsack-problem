import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DynamicProSolution {
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
        // ArrayList<Item> items = new ArrayList<>();    
        //store all the items read from the file to itemList
        HashMap<Integer, Item> itemList = new HashMap<Integer, Item>();    
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
            String line = "";
            csvReader.readLine();
            int id = 1;
            while ((line = csvReader.readLine()) != null) {
                String[] split = line.split(",");
                int weight = Integer.parseInt(split[1]);
                Item item = new Item(split[0], weight, Integer.parseInt(split[2]));     // create Item (item name, weight, value)
                itemList.put(id, item);     //store in HashMap(id, item)
                id++;
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //get the most optimal combination and print it
        HashMap<Integer, Item> bestCombin = Matrix(itemList, itemList.size(), limit);
        
        // End measurements
        long stopTime = System.nanoTime();
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();

        // Results and diagnostics
        System.out.println("--------PROGRAM RESULTS--------");
        printBestCombin(bestCombin);
    
        System.out.println("--------PROGRAM ANALYSIS--------");
        System.out.println("Memory usage before algorithm (KB): " + usedMemoryBefore / 1000);
        System.out.println("Memory usage after algorithm (KB): " + usedMemoryAfter / 1000);
        System.out.println("Memory used (KB): " + (usedMemoryAfter-usedMemoryBefore) / 1000);
        System.out.println("Time taken (milliseconds): " + ((stopTime - startTime) / 1000000));
        
    }

    // assign combination of items and values in 2D arrays and compare it to get the most optimal combinaiton
    private static HashMap<Integer, Item> Matrix(HashMap<Integer, Item> itemList, int itemSize, long limit) {
        long[][] itemMatrix = new long[itemSize + 1][(int)limit + 1];         //2D array store the selection of items for each combination
        long[][] valueMatrix = new long[itemSize + 1][(int)limit + 1];       //matrix store the total value of each combination
        for(int i = 0; i <= limit; i++){     //to ensure first row is 0
            valueMatrix[0][i] = 0;
        }
        for(int i = 0; i <= itemSize; i++){     //to ensure first col is 0
            valueMatrix[i][0] = 0;
        }
        for(int i = 1; i <= itemSize; i++){        //from the 1st item row
            Item item = itemList.get(i);
            long weight = item.getWeight();
            long value = item.getValue();
            for(int w = 0; w <= limit; w++){
                //if the weight is <= target weight (w) && new combiValue is > (previous combinValue which at target weight) 
                // -> select the new combiValue and current item, else just keep previous combiValue
                if( (weight <= w) && ( (value + valueMatrix[i - 1][(int) (w - weight)]) > valueMatrix[i - 1][w])) {
                    valueMatrix[i][w] = value + valueMatrix[i - 1][(int) (w - weight)];
                    itemMatrix[i][w] = 1;
                } else{
                    valueMatrix[i][w] = valueMatrix[i - 1][w];          // keep previous combiValue
                    itemMatrix[i][w] = 0;               // means not select this item
                }
            }
        }
        return getBestCombin(itemList, itemSize, itemMatrix, limit);
    }

    // get best combination of items
    private static HashMap<Integer, Item> getBestCombin(HashMap<Integer, Item> itemList, int itemSize, 
        long[][] itemMatrix, long limit) {
        
        long W = limit;
        HashMap<Integer, Item> bestCombin = new HashMap<Integer, Item>();
        for(int i = itemSize; i >= 1; i--) {
            if(itemMatrix[i][(int) W] == 1) {
                bestCombin.put(i, itemList.get(i));
                W = W - itemList.get(i).getWeight();
            }
        }
        return bestCombin;
    }

    // print best combination info (items, total weight, total value)
    private static void printBestCombin(HashMap<Integer, Item> bestCombin) {
        long bestW = 0;
        long bestV = 0;
        System.out.print("\nBest Combination: ");
        int i = 1;
        for(Integer item : bestCombin.keySet()){
            if(i < bestCombin.size())
                System.out.print(item + ", ");
            else
                System.out.print(item);
            i++;
            bestW += bestCombin.get(item).getWeight();
            bestV += bestCombin.get(item).getValue();
        }
        System.out.print("\nWeight: " + bestW);
        System.out.println("\nValue: " + bestV + "\n");
    }
}
