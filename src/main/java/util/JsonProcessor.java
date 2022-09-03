package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.TwineJson;

import java.io.File;
import java.io.IOException;

public class JsonProcessor {

    public static TwineJson load(File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        TwineJson resJson = mapper.readValue(file, TwineJson.class);
        return resJson;
    }

    public static void save(TwineJson json, File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, json);
    }
}
