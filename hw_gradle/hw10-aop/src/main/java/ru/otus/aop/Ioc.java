package ru.otus.aop;

import lombok.extern.java.Log;
import ru.otus.logging.TestLogging;
import ru.otus.logging.TestLoggingImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
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

        LoggingInvocationHandler(TestLogging myClass) {
            this.testLogging = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (testLogging.getClass().getMethod(method.getName(),
                    method.getParameterTypes()).isAnnotationPresent(ru.otus.annotation.Log.class)) {
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
