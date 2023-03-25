package ru.otus.serializer;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) throws IOException {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        for (String key : data.keySet()) {
            jsonObjectBuilder.add(key, data.get(key));
        }

        try (var bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName))) {
            bufferedOutputStream.write(jsonObjectBuilder.build().toString().getBytes(StandardCharsets.UTF_8));
        }
    }
}
