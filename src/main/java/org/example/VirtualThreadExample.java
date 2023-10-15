package org.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class VirtualThreadExample {
    private static final Logger log = Logger.getAnonymousLogger();

    public static void main(String[] args) throws InterruptedException {
        // 10월 15, 2023 1:26:45 오후 org.example.VirtualThreadExample main
        // INFO:
        // threadName: main
        // availableProcessors: 8
        log.info("\nthreadName: " + Thread.currentThread().getName() + "\navailableProcessors: " + Runtime.getRuntime().availableProcessors());

        final long start = System.currentTimeMillis();
        final AtomicLong index = new AtomicLong();
        final int count = 100;
        final CountDownLatch countDownLatch = new CountDownLatch(count);

        final Runnable runnable = () -> {
            try {
                final long indexValue = index.incrementAndGet();
                Thread.sleep(1000L);

                // INFO:
                // threadName:
                // value: xx
                // 10월 15, 2023 1:26:46 오후 org.example.VirtualThreadExample lambda$main$0
                log.info("\nthreadName: " + Thread.currentThread().getName() + "\nvalue: " + indexValue);

                countDownLatch.countDown();
            } catch (final InterruptedException e) {
                countDownLatch.countDown();
            }
        };

        // 일반적으로 쓰레드 풀의 갯수를 지정하는 것과는 달리 쓰레드의 갯수를 지정할 필요가 없다.
        try (final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < count; i++) {
                executorService.submit(runnable);
            }
        }

        countDownLatch.await();
        final long finish = System.currentTimeMillis();
        final long timeElapsed = finish - start;

        // 10월 15, 2023 1:26:46 오후 org.example.VirtualThreadExample main
        // INFO:
        // threadName: main
        // Run time: 1063
        log.info("\nthreadName: " + Thread.currentThread().getName() + "\nRun time: " + timeElapsed);
    }
}