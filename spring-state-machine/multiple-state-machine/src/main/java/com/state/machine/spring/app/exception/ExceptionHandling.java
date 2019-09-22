package com.state.machine.spring.app.exception;

public interface ExceptionHandling {
    static <T> T handleException(String message, ExceptionHandling.Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (HandledException var3) {
            throw var3;
        } catch (Exception var4) {
            throw new ApplicationErrorException(message, var4);
        }
    }

    static void handleException(String exceptionMessage, final ExceptionHandling.Runnable runnable) {
        handleException(exceptionMessage, new ExceptionHandling.Supplier<Void>() {
            public Void get() throws Exception {
                runnable.run();
                return null;
            }
        });
    }

    @FunctionalInterface
    public interface Runnable {
        void run() throws Exception;
    }

    @FunctionalInterface
    public interface Supplier<T> {
        T get() throws Exception;
    }
}
