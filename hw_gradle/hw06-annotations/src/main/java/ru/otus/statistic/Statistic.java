package ru.otus.statistic;

import static ru.otus.constant.AnsiColor.*;

public class Statistic {

    private int total = 0;
    private int failed = 0;

    public void increaseTotal() {
        total++;
    }

    public void increaseFailed() {
        failed++;
        total++;
    }

    public int getTotal() {
        return total;
    }

    public int getFailed() {
        return failed;
    }

    public int getSuccess() {
        return total - failed;
    }

    public void displayStatistics() {
        System.out.println();
        System.out.println(ANSI_BLUE + " Total count of tests - " + getTotal());
        System.out.println(ANSI_GREEN + " Tests passed successfully - " + getSuccess());
        System.out.println(ANSI_RED + " Tests failed - " + getFailed());
        System.out.print(ANSI_RESET);
    }
}
