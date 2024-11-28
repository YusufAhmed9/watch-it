package WatChill.FileHandling;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    public static void writeToFile(String fileName, String content) throws IOException {
        try {
            //Creating an instance of a class that writes to file
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            //Writes string to file
            writer.write(content);
            //So that next time a file is written into it goes for next line
            writer.write('\n');
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
