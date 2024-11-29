package WatChill.FileHandling;

import WatChill.Subscription.Subscription;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JsonReader {
    public static <T> ArrayList<T> readJsonFile(String filePath, Class<T> type) {
        ArrayList<T> objects;
        ObjectMapper objectMapper = new JsonMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            File jsonFile = new File(filePath);
            if (jsonFile.length() == 0) {
                return new ArrayList<>();
            }
            objects = objectMapper.readValue(
                jsonFile,
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, type)
            );
            return objects;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
