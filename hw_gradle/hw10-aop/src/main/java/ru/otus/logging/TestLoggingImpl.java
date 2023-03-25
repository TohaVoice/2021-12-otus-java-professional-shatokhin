package ru.otus.logging;

import ru.otus.annotation.Log;

public class TestLoggingImpl implements TestLogging {

    @Override
    @Log
    public void calculation(int param) {
        System.out.println("Method 'public void calculation(int param)' finished successful");
    }

    @Override
    public void calculation(int param1, int param2) {
        System.out.println("Method 'public void calculation(int param, int param2)' finished successful");
    }

    @Override
    @Log
    public void calculation(int param1, int param2, String param3) {
        System.out.println("Method 'public void calculation(int param, int param2, String param3)' finished successful");
    }

}
