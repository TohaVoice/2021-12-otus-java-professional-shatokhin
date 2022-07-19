package ru.otus.domain.consts;

import java.util.Arrays;
import java.util.Optional;

public enum Denomination {

    D5000(5000),
    D1000(1000),
    D500(500),
    D100(100),
    D200(200),
    D50(50);

    private final int value;

    Denomination(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    public static Denomination valueOf(int value) {
        Optional<Denomination> d = Arrays.stream(values())
                .filter(denomination -> denomination.get() == value)
                .findFirst();
        return d.orElseThrow();
    }
}
