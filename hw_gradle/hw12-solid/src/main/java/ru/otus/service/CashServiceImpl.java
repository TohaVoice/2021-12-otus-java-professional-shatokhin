package ru.otus.service;

import ru.otus.domain.ATM;
import ru.otus.domain.Banknote;
import ru.otus.domain.consts.Denomination;
import ru.otus.exception.NotEnoughBanknotesException;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class CashServiceImpl implements CashService {

    private final ATM atm;

    public CashServiceImpl(ATM atm) {
        this.atm = atm;
    }

    @Override
    public void putCash(List<Banknote> banknotes) {
        final List<Banknote> validBanknotes = banknotes.stream()
                .filter(banknote -> atm.getBoxes().stream()
                                .anyMatch(box -> banknote.getMetaInfo().equals(box.getBanknote().getMetaInfo())))
                .collect(Collectors.toList());
        atm.addBanknotes(validBanknotes);
    }

    @Override
    public List<Banknote> takeCash(int amount) throws NotEnoughBanknotesException {
        int money = 0;
        List<Banknote> preparedCash = new ArrayList<>();
        final List<Denomination> excludes = new ArrayList<>();
        if (amount <= getBalance()) {
            int maxDenomination;
            do {
                maxDenomination = findMaxDenomination(excludes);
                if (maxDenomination == 0)
                    throw new NotEnoughBanknotesException();
                if (maxDenomination <= (amount - money)) {
                    money += maxDenomination;
                    preparedCash.add(getBanknote(maxDenomination));
                } else {
                    excludes.add(Denomination.valueOf(maxDenomination));
                }
            } while (money != amount);
        } else {
            throw new NotEnoughBanknotesException();
        }

        if (money == amount) {
            atm.removeBanknotes(preparedCash);
            return preparedCash;
        } else throw new NotEnoughBanknotesException();
    }

    @Override
    public int getBalance() {
        return atm.getBanknotes().stream()
                .mapToInt(value -> value.getDenomination()).sum();
    }

    private int findMaxDenomination(List<Denomination> excludes) {
        OptionalInt max = atm.getBanknotes().stream()
                .filter(banknote ->
                        !excludes.stream().anyMatch(denomination -> denomination.get() == banknote.getDenomination()))
                .mapToInt(value -> value.getDenomination()).max();
        return max.isPresent() ? max.getAsInt() : 0;
    }

    private Banknote getBanknote(int denomination) {
        return atm.getBanknotes().stream()
                .filter(banknote -> banknote.getDenomination() == denomination)
                .findFirst().orElseThrow();
    }
 }
