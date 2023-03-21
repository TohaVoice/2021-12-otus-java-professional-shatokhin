package ru.otus.processor;

import ru.otus.model.Measurement;

import java.util.List;
import java.util.Map;

public interface Processor {

    /**
     * To group output list by name and calc sum of field value
     * @param data
     * @return
     */
    Map<String, Double> process(List<Measurement> data);
}
