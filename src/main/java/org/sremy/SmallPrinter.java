package org.sremy;

import java.util.function.Consumer;

public class SmallPrinter implements Consumer<Task> {

    Consumer<Task> innerConsumer;

    public SmallPrinter(Consumer<Task> innerConsumer) {
        this.innerConsumer = innerConsumer;
    }

    @Override
    public void accept(Task task) {
        // Real consumer
//        System.out.println("SmallPrinter -> " + task);
        innerConsumer.accept(task);
//        System.out.println("SmallPrinter <-" + task);

    }

}
