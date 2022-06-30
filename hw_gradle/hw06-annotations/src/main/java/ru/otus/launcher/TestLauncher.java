package ru.otus.launcher;

import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;
import ru.otus.statistic.Statistic;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static ru.otus.constant.AnsiColor.*;

public class TestLauncher {

    public static void start(Statistic statistic, String... classNames) {
        for (String className : classNames) {
            final Class<?> clazz;
            final Object testClass;
            try {
                clazz = Class.forName(className);
                testClass = clazz.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException ex) {
                System.out.println(ex.getCause().getMessage());
                return;
            }
            final Method[] declaredMethods = clazz.getDeclaredMethods();
            final Method beforeMethod = findMethodByAnnotation(declaredMethods, Before.class);
            final Method afterMethod = findMethodByAnnotation(declaredMethods, After.class);

            System.out.println(ANSI_BLUE + className + " - started");
            try {
                callMethod(testClass, beforeMethod);
            } catch (Throwable e) {
                System.out.println(e.getCause().getMessage());
                return;
            }

            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(Test.class)) {
                    try {
                        callMethod(testClass, method);
                        statistic.increaseTotal();
                        displayResult(method.getName(), false);
                    } catch (Throwable e) {
                        statistic.increaseFailed();
                        displayResult(method.getName(), true);
                    }
                }
            }

            try {
                callMethod(testClass, afterMethod);
            } catch (Throwable e) {
                System.out.println(e.getCause().getMessage());
            }

            System.out.print(ANSI_RESET);
        }
    }

    private static Method findMethodByAnnotation(Method[] methods, Class annotationClass) {
        Optional<Method> optMethod = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .findFirst();
        return optMethod.isPresent() ? optMethod.get() : null;
    }

    private static void callMethod(Object object, Method method) throws Throwable {
        if (method != null) {
            method.setAccessible(true);
            method.invoke(object);
        }
    }

    private static void displayResult(String methodName, boolean isFailed) {
        if (isFailed) {
            System.out.println(ANSI_YELLOW + "...." + methodName + ANSI_RED + " - Test failed");
        } else {
            System.out.println(ANSI_YELLOW + "...." + methodName + ANSI_GREEN + " - Test passed successfully");
        }
    }
}
