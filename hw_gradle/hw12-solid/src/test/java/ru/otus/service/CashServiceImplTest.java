package ru.otus.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.domain.ATM;
import ru.otus.domain.Banknote;
import ru.otus.domain.consts.Denomination;
import ru.otus.exception.NotEnoughBanknotesException;
import ru.otus.factory.ATMFactory;
import ru.otus.factory.ATMFactoryImpl;

import java.util.ArrayList;
import java.util.List;

class CashServiceImplTest {

    private ATM atm;
    private CashService cashService;

    @BeforeEach
    void setUp() {
        ATMFactory atmFactory = new ATMFactoryImpl();
        atm = atmFactory.createDefaultATM();
        cashService = new CashServiceImpl(atm);
    }

    @Test
    void putCash() {
        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(new Banknote(Denomination.D100.get()));
        banknotes.add(new Banknote(Denomination.D500.get()));
        banknotes.add(new Banknote(Denomination.D1000.get()));

        cashService.putCash(banknotes);
        Assertions.assertEquals(1600, cashService.getBalance());
    }

    @Test
    void putCash_NotValidDenomination() {
        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(new Banknote(Denomination.D200.get()));
        banknotes.add(new Banknote(Denomination.D500.get()));
        banknotes.add(new Banknote(Denomination.D1000.get()));

        cashService.putCash(banknotes);
        Assertions.assertEquals(1500, cashService.getBalance());
    }

    @Test
    void putCash_NotValidBanknote() {
        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(new Banknote(Denomination.D100.get()));
        banknotes.add(new Banknote(Denomination.D500.get()));
        banknotes.add(new OtherTypeOfBanknote(Denomination.D1000.get()));

        cashService.putCash(banknotes);
        Assertions.assertEquals(600, cashService.getBalance());
    }

    @Test
    void takeCash() {
        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(new Banknote(Denomination.D100.get()));
        banknotes.add(new Banknote(Denomination.D100.get()));
        banknotes.add(new Banknote(Denomination.D100.get()));
        banknotes.add(new Banknote(Denomination.D100.get()));
        banknotes.add(new Banknote(Denomination.D100.get()));
        banknotes.add(new Banknote(Denomination.D100.get()));
        banknotes.add(new Banknote(Denomination.D100.get()));
        banknotes.add(new Banknote(Denomination.D500.get()));
        banknotes.add(new OtherTypeOfBanknote(Denomination.D1000.get()));
        atm.addBanknotes(banknotes);

        List<Banknote> cashBanknotes = cashService.takeCash(1500);
        Assertions.assertEquals(Denomination.D1000.get(), cashBanknotes.get(0).getDenomination());
        Assertions.assertEquals(Denomination.D500.get(), cashBanknotes.get(1).getDenomination());

        Assertions.assertEquals(700, cashService.getBalance());
    }

    @Test
    void takeCash_NotEnoughMoney() {
        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(new Banknote(Denomination.D100.get()));
        banknotes.add(new Banknote(Denomination.D500.get()));
        banknotes.add(new Banknote(Denomination.D1000.get()));
        atm.addBanknotes(banknotes);

        NotEnoughBanknotesException exception = Assertions.assertThrows(NotEnoughBanknotesException.class,
                () -> cashService.takeCash(2500));

        Assertions.assertEquals("Not enough banknotes to issue the requested amount", exception.getMessage());
    }

    @Test
    void takeCash_NotValidRequest() {
        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(new Banknote(Denomination.D100.get()));
        banknotes.add(new Banknote(Denomination.D500.get()));
        banknotes.add(new Banknote(Denomination.D1000.get()));
        atm.addBanknotes(banknotes);

        NotEnoughBanknotesException exception = Assertions.assertThrows(NotEnoughBanknotesException.class,
                () -> cashService.takeCash(1550));

        Assertions.assertEquals("Not enough banknotes to issue the requested amount", exception.getMessage());
    }

    @Test
    void getBalance() {
        List<Banknote> banknotes = new ArrayList<>();
        banknotes.add(new Banknote(Denomination.D100.get()));
        banknotes.add(new Banknote(Denomination.D500.get()));
        banknotes.add(new Banknote(Denomination.D1000.get()));

        cashService.putCash(banknotes);
        Assertions.assertEquals(1600, cashService.getBalance());
    }

    private class OtherTypeOfBanknote extends Banknote {

        public OtherTypeOfBanknote(int denomination) {
            super(denomination);
        }
    }

}