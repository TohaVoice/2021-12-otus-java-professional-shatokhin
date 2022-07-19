package ru.otus.launcher;

import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;
import ru.otus.statistic.Statistic;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.otus.constant.AnsiColor.*;

public class TestLauncher {

    public static void start(Statistic statistic, String... classNames) {
        for (String className : classNames) {
            final Class<?> clazz;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException ex) {
                System.out.println(ANSI_RED + ex.getMessage());
                return;
            }
            final Method[] declaredMethods = clazz.getDeclaredMethods();
            final List<Method> testMethods = findMethodsByAnnotation(declaredMethods, Test.class);
            final Method beforeMethod = findMethodByAnnotation(declaredMethods, Before.class);
            final Method afterMethod = findMethodByAnnotation(declaredMethods, After.class);

            System.out.println(ANSI_BLUE + className + " - started");

            for (Method testMethod : testMethods) {
                try {
                    runTest(clazz, beforeMethod, testMethod, afterMethod, statistic);
                } catch (Throwable e) {
                    System.out.println(ANSI_RED + e.getMessage());
                }
            }

            System.out.print(ANSI_RESET);
        }
    }

    private static void runTest(Class<?> clazz, Method beforeMethod, Method testMethod, Method afterMethod,
                                Statistic statistic) throws Throwable {
        final Object testClass = clazz.getDeclaredConstructor().newInstance();

        callMethod(testClass, beforeMethod, afterMethod);

        try {
            callMethod(testClass, testMethod, afterMethod);
            statistic.increaseTotal();
            displayResult(testMethod.getName(), false);
            callAfterMethod(testClass, afterMethod);
        } catch (Throwable ex) {
            statistic.increaseFailed();
            displayResult(testMethod.getName(), true);
            System.out.println(ANSI_RED + ex.getMessage());
        }
    }

    private static Method findMethodByAnnotation(Method[] methods, Class annotationClass) {
        Optional<Method> optMethod = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .findFirst();
        return optMethod.isPresent() ? optMethod.get() : null;
    }

    private static List<Method> findMethodsByAnnotation(Method[] methods, Class annotationClass) {
        return Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .collect(Collectors.toList());
    }

    private static void callMethod(Object object, Method method, Method afterMethod) throws Throwable {
        try {
            if (method != null) {
                method.setAccessible(true);
                method.invoke(object);
            }
        } catch (Throwable e) {
            callAfterMethod(object, afterMethod);
            throw new Throwable(String.format("Invoke method '%s' failed: %s", (object.getClass().getName() + "." + method.getName()), e.getCause()));
        }
    }

    private static void callAfterMethod(Object testClass, Method afterMethod) {
        if (afterMethod != null) {
            afterMethod.setAccessible(true);
            try {
                afterMethod.invoke(testClass);
            } catch (ReflectiveOperationException e) {
                System.out.println(ANSI_RED + String.format("Invoke method '%s' failed: %s", (testClass.getClass().getName() + "." + afterMethod.getName()), e.getCause()));
            }
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