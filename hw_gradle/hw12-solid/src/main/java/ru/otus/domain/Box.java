package ru.otus.domain;

public class Box {

    private final Banknote banknote;

    public Box(Banknote banknote) {
        this.banknote = banknote;
    }

    public Banknote getBanknote() {
        return banknote;
    }

}
