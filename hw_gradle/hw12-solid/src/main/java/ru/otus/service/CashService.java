package ru.otus.service;

import ru.otus.domain.Banknote;

import java.util.List;

public interface CashService {

    void putCash(List<Banknote> banknotes);

    List<Banknote> takeCash(int amount);

    int getBalance();

}
