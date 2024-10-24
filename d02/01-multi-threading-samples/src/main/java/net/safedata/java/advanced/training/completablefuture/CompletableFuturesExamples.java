package net.safedata.java.advanced.training.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFuturesExamples {

    public static void main(String[] args) {
        newExamples();

        //oldExamples();
    }

    private static void newExamples() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello, CF!");
        completableFuture.thenApply(String::toUpperCase) // intermediary op, returns a CF<Type>
                         .thenComposeAsync(value -> CompletableFuture.supplyAsync(() -> "World!"))
                         .handleAsync((value, exception) -> { // BiFunction, returns the value type
                             return exception.getMessage();
                         })
                         .whenCompleteAsync((value, exception) -> { //BiConsumer, does not return anything
                             if (value != null) {
                                 System.out.println("do something with the value");
                             }
                         })
                         .completeOnTimeout("something", 1000, TimeUnit.MILLISECONDS)
                         .exceptionallyAsync(Throwable::getMessage)
                         .thenAccept(System.out::println)
                         .thenRunAsync(() -> System.out.println("Done!"))
        ;

        final CompletableFuture<String> completableFuture1 = completableFuture.thenApplyAsync(value -> value.toUpperCase());
        final CompletableFuture<String> completableFuture2 = completableFuture.thenComposeAsync(value -> CompletableFuture.supplyAsync(() -> "World!"));

        completableFuture.thenAcceptAsync(value ->
                System.out.println(Thread.currentThread().getName() + " - value: " + value)).join();
    }

    private static void oldExamples() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "Hello");
        //System.out.println(completableFuture.join());

        String chainedCFs = CompletableFuture.supplyAsync(() -> "Something")
                                             .thenApplyAsync(value -> value.toUpperCase())
                                             .thenApplyAsync(newValue -> newValue + " else")
                                             .join();
        System.out.println(chainedCFs);

        CompletableFuture<String> anotherCFuture = CompletableFuture.supplyAsync(() -> "Hello 2");
        //anotherCFuture.thenAccept(value -> System.out.println(value));

        final CompletableFuture<Void> allOf = CompletableFuture.allOf(completableFuture, anotherCFuture);
        final CompletableFuture<Object> anyOf = CompletableFuture.anyOf(completableFuture, anotherCFuture);
    }
}
