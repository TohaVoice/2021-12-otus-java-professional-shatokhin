package ru.otus.test;

import ru.otus.annotation.Test;

public class WithoutBeforeAndAfterTest {

    @Test
    public void fineWork() throws Exception {
        String value = "Fine work!";
        if (!value.contains("Fine"))
            throw new Exception("unexpected value");
    }
}
