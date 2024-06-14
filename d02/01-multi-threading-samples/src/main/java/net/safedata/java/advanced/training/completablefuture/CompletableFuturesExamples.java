package net.safedata.java.advanced.training.completablefuture;

import java.util.concurrent.CompletableFuture;

public class CompletableFuturesExamples {

    public static void main(String[] args) {
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
