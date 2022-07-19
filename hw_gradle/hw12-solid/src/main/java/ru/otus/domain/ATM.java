package ru.otus.domain;

import java.util.ArrayList;
import java.util.List;

public class ATM {

    private final List<Box> boxes;
    private final List<Banknote> banknotes;

    public ATM(List<Box> boxes) {
        this.boxes = boxes;
        banknotes = new ArrayList<>();
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void addBanknotes(List<Banknote> banknotes) {
        this.banknotes.addAll(banknotes);
    }

    public void removeBanknotes(List<Banknote> banknotes) {
        this.banknotes.removeAll(banknotes);
    }

    public List<Banknote> getBanknotes() {
        return banknotes;
    }
}
