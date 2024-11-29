package WatChill.FileHandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JsonReader {
    // type: The class type of objects in the Json file.
    public static <T> ArrayList<T> readJsonFile(String filePath, Class<T> type) {
        ArrayList<T> objects;
        // ObjectMapper instance for Json processing
        ObjectMapper objectMapper = new JsonMapper();
        // Add support for Java Date and Time to Jackson
        objectMapper.registerModule(new JavaTimeModule());
        try {
            File jsonFile = new File(filePath); // A File object for the json file
            // checks if the file is empty, return an empty ArrayList
            if (jsonFile.length() == 0) {
                return new ArrayList<>();
            }
            objects = objectMapper.readValue(
                jsonFile,
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, type) // An ArrayList of the given type
            );
            return objects;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
