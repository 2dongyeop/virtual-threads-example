package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Main {

    private static Logger logger = Logger.getGlobal();

    public static void main(String[] args) {
        Thread.startVirtualThread(() -> {
            System.out.println("Hello Virtual Thread");
            logger.info("\nthreadName: " + Thread.currentThread().getName() + "\navailableProcessors: " + Runtime.getRuntime().availableProcessors());
        });

        // Virtual Thread 방법 2
        Thread virtualThread1 = Thread.ofVirtual().start(() -> {
            System.out.println("Hi Virtual Thread");
            logger.info("\nthreadName: " + Thread.currentThread().getName() + "\navailableProcessors: " + Runtime.getRuntime().availableProcessors());
        });

        // Virtual Thread 이름 지정
        Thread.Builder builder = Thread.ofVirtual().name("JVM-Thread");
        Thread virtualThread2 = builder.start(() -> {
            System.out.println("Hi Virtual Thread");
            logger.info("\nthreadName: " + Thread.currentThread().getName() + "\navailableProcessors: " + Runtime.getRuntime().availableProcessors());
        });

        // 스레드가 Virtual Thread인지 확인하여 출력
        System.out.println("Thread is Virtual? " + virtualThread2.isVirtual());

        // ExecutorService 사용
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 3; i++) {
                executorService.submit(() -> {
                    System.out.println("Hi Virtual Thread");
                    logger.info("\nthreadName: " + Thread.currentThread().getName() + "\navailableProcessors: " + Runtime.getRuntime().availableProcessors());
                });
            }
        }
    }
}