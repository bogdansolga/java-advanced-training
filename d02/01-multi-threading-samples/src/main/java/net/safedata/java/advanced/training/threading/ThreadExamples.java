package net.safedata.java.advanced.training.threading;

public class ThreadExamples {

    public static void main(String[] args) {
        System.out.println("Current thread is " + Thread.currentThread().getName());

        Thread thread = new Thread(() ->
                System.out.println("Running in the platform thread named " +
                        Thread.currentThread().getName())
        );
        thread.start();

        Thread.startVirtualThread(() -> System.out.println("Virtual thread - " +
                Thread.currentThread().getName()));

        Thread.ofVirtual()
              .name("the-virtual-one")
              .start(() -> System.out.println("something"));
    }
}
