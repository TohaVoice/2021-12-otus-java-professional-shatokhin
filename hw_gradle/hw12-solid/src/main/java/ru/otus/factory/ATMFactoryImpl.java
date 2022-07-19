package ru.otus.factory;

import ru.otus.domain.ATM;
import ru.otus.domain.Banknote;
import ru.otus.domain.Box;
import ru.otus.domain.consts.Denomination;

import java.util.ArrayList;
import java.util.List;

public class ATMFactoryImpl implements ATMFactory {

    public ATM createDefaultATM() {
        List<Box> boxes = new ArrayList<>();
        boxes.add(new Box(new Banknote(Denomination.D100.get())));
        boxes.add(new Box(new Banknote(Denomination.D500.get())));
        boxes.add(new Box(new Banknote(Denomination.D1000.get())));
        return new ATM(boxes);
    }
}
