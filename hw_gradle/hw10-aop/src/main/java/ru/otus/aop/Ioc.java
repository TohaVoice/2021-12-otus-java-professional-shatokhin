package ru.otus.aop;

import lombok.extern.java.Log;
import ru.otus.logging.TestLogging;
import ru.otus.logging.TestLoggingImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log
public class Ioc {

    public static TestLogging createTestLogging() {
        InvocationHandler handler = new LoggingInvocationHandler(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    private static class LoggingInvocationHandler implements InvocationHandler {
        private final TestLogging testLogging;
        private final List<Method> iMethods;

        LoggingInvocationHandler(TestLogging myClass) {
            this.testLogging = myClass;

            List<Method> logMethods = Arrays.stream(myClass.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(ru.otus.annotation.Log.class))
                    .collect(Collectors.toList());

            iMethods = Arrays.stream(TestLogging.class.getMethods())
                    .filter(method -> logMethods.stream()
                            .filter(logMethod -> logMethod.getName().equals(method.getName()))
                            .filter(logMethod -> logMethod.getReturnType().equals(method.getReturnType()))
                            .filter(logMethod -> logMethod.getParameterCount() == method.getParameterCount())
                            .anyMatch(logMethod -> {
                                for (int i = 0; i < logMethod.getParameterCount(); i++) {
                                    if (logMethod.getParameterTypes()[i].equals(method.getParameterTypes()[i])) {
                                        return true;
                                    }
                                }
                                return false;
                            })
                    ).collect(Collectors.toList());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (iMethods.contains(method)) {
                log.info(String.format("executed method: %s, params: %s", method.getName(),
                        Arrays.stream(args).map(o -> o.toString()).collect(Collectors.joining(", "))));
            }
            return method.invoke(testLogging, args);
        }

        @Override
        public String toString() {
            return "LoggingInvocationHandler{" +
                    "myClass=" + testLogging +
                    '}';
        }
    }
}