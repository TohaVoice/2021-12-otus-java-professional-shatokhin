package ru.otus;

import ru.otus.aop.Ioc;
import ru.otus.logging.TestLogging;

public class DemoLogging {

    public static void main(String[] args) {
        TestLogging testLogging = Ioc.createTestLogging();
        testLogging.calculation(6);
        testLogging.calculation(9, 10);
        testLogging.calculation(8, 7, "Hello OTUS!!!");

    }
}
