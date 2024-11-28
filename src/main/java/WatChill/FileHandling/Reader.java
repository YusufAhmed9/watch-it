package WatChill.FileHandling;

import java.io.*;
import java.util.ArrayList;

public class Reader {
    public static ArrayList<String> readFromFile(String fileName) throws FileNotFoundException {
        try {
            //A string that stores the content of line in file
            String line;
            //ArrayList to store all lines inside the file
            ArrayList<String> fileLines = new ArrayList<>();
            //Instance of a class that reads from files
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            //Loop iterates until there is no lines
            while ((line = reader.readLine()) != null){
                //Adds each line inside file to the ArrayList
                fileLines.add(line);
            }
            return fileLines;
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
