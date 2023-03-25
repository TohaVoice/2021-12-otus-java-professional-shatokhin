package ru.otus.loader;

import ru.otus.model.Measurement;

import java.util.List;

public interface Loader {

    /**
     * Read file, parse and return result
     */
    List<Measurement> load();
}
