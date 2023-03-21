package ru.otus.serializer;

import java.io.IOException;
import java.util.Map;

public interface Serializer {

    /**
     * To make json result and save it to file
     * @param data
     * @throws IOException
     */
    void serialize(Map<String, Double> data) throws IOException;
}
