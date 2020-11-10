import java.io.*;
import java.util.*;

public class HashMapKnapsack {
    public static ArrayList<String> readcsv() {
        ArrayList<String> result = new ArrayList<>();
        BufferedReader csvReader;
        String row;
        try {
            csvReader = new BufferedReader(new FileReader("./datasets/dataset_real_world.csv"));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                String id = data[0];
                String weight = data[1];
                String value = data[2];
                System.out.println(id + weight + value);
            }
            csvReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException ioe) {
            System.out.println("ia ia ia");
        }
        return result;
    }
    
    public static void main(String[] args) {
        readcsv();
    }
}
