package net.safedata.java.advanced.training.async;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ParallelProcessing {

    private static final int CORES_NUMBER = Runtime.getRuntime().availableProcessors();

    private static final Random RANDOM = new Random(1000);

    //private static final AtomicInteger TOTAL_EXECUTION_DURATION = new AtomicInteger(0);
    private static final List<Integer> EXECUTIONS_DURATIONS = new ArrayList<>(CORES_NUMBER);

    public static void main(String[] args) {
        // the used thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(CORES_NUMBER / 2);
        // ForkJoinPool.commonPool();

        // the tasks execution completion service
        ExecutorCompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(executorService);

        long now = System.currentTimeMillis();

        // submit processing tasks to the ExecutorCompletionService --> fork
        IntStream.rangeClosed(0, CORES_NUMBER)
                 .forEach(item -> executorCompletionService.submit(new DepositProcessor()));

        // collect the stocks from each queried deposit --> join
        List<Integer> stocks = new ArrayList<>(CORES_NUMBER);
        try {
            for (int i = 0; i < CORES_NUMBER; i++) {
                final Future<Integer> future = executorCompletionService.poll(100, TimeUnit.MILLISECONDS);
                if (future != null && future.isDone()) {
                    stocks.add(future.get());
                } else {
                    System.out.println("Got a cancelled task");
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println();
        final long duration = System.currentTimeMillis() - now;
        System.out.println("Got " + stocks.size() + " product stocks in " + duration + " ms");

        final int executionTimesSum = getExecutionTimesSum();
        System.out.println("Total executions times: " + executionTimesSum);

        System.out.println("Performance improvement factor: " + (executionTimesSum / duration));

        final List<Runnable> unfinishedTasks = executorService.shutdownNow();
        System.out.println("There are " + unfinishedTasks.size() + " unfinished tasks");
    }

    private static int getExecutionTimesSum() {
        return EXECUTIONS_DURATIONS.stream()
                                   .mapToInt(item -> item)
                                   .sum();
    }

    // returns the available items number (for that deposit)
    private static class DepositProcessor implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            long now = System.currentTimeMillis();
            // simulate a processing time for each task
            final int executionDuration = Math.abs(RANDOM.nextInt(500));
            EXECUTIONS_DURATIONS.add(executionDuration);
            Thread.sleep(executionDuration);

            System.out.println("[" + Thread.currentThread().getName() + "] Returning in " + (System.currentTimeMillis() - now) + " ms");

            return Math.abs(RANDOM.nextInt(5000));
        }
    }
}
