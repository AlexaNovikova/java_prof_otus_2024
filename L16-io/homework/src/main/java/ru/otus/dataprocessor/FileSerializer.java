package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final ObjectMapper objectMapper;
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
        objectMapper = JsonMapper.builder().build();
    }

    @Override
    public void serialize(Map<String, Double> data) throws IOException {
        var file = new File(fileName);
        objectMapper.writeValue(file, data);
    }
}
