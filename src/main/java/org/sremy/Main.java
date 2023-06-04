package org.sremy;

import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");

        Printer printer = new Printer();
        Consumer<Task> smallConsumer = new SmallPrinter(printer);
        BigConsumer<Task> bigConsumer = new BigPrinter(printer);

        Regulator regulator = new Regulator(smallConsumer, bigConsumer);
        TaskProducer taskProducer = new TaskProducer();


        for (int i = 0; i < 20; i++) {
            regulator.acceptTask(taskProducer.get());
        }


        Thread.sleep(10_000);
        bigConsumer.shutdown();
        printer.shutdown();

    }
}