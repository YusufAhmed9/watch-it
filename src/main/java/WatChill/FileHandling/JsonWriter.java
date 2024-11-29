package WatChill.FileHandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JsonWriter {
    public static <T> void writeJsonToFile(String filePath, ArrayList<T> objects) {
        // ObjectMapper instance for Json processing
        ObjectMapper objectMapper = new JsonMapper();
        // Add support for Java Date and Time to Jackson
        objectMapper.registerModule(new JavaTimeModule());
        try {
            File file = new File(filePath); // // A File object for the json file
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, objects); // Write Json to file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
