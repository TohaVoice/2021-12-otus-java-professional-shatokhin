package ru.otus;

import ru.otus.launcher.TestLauncher;
import ru.otus.statistic.Statistic;
import ru.otus.test.CheckCounterTest;
import ru.otus.test.WithoutBeforeAndAfterTest;

public class TestApp {

    public static void main(String[] args) {
        Statistic statistic = new Statistic();
        TestLauncher.start(statistic,
                CheckCounterTest.class.getName(),
                WithoutBeforeAndAfterTest.class.getName());
        statistic.displayStatistics();
    }

}









