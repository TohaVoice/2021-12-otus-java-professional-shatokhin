package ru.otus.domain;

import java.util.Objects;

public class Banknote {

    private final int denomination;

    public Banknote(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }

    public String getMetaInfo() {
        return getClass().getName() + getDenomination();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banknote banknote = (Banknote) o;
        return getMetaInfo().equals(banknote.getMetaInfo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMetaInfo());
    }
}
