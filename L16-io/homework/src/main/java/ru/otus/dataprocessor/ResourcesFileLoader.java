package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.util.List;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {

    private final String fileName;
    private final ObjectMapper mapper;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
        mapper = JsonMapper.builder().build();
    }

    @Override
    public List<Measurement> load() throws IOException {
        var file = ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName);
        List<Measurement> measurements;
        measurements = mapper.readValue(file, new TypeReference<>() {});
        return measurements;
    }
}
