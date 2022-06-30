package ru.otus.test;

import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;
import ru.otus.constant.AnsiColor;

public class CheckCounterTest {

    private int count = 0;

    @Before
    public void setting() {
        count = 10;
    }

    @Test
    public void checkCount() throws Exception {
        if (count != 10)
            throw new Exception("unexpected value");
    }

    @Test
    public void checkCount_incorrect() throws Exception {
        if (count == 10)
            throw new Exception("unexpected value");
    }

    @After
    public void clean() {
        count = 0;
        System.out.println(AnsiColor.ANSI_PURPLE + "Data cleaned");
    }
}
