package org.sremy;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class BigPrinter implements BigConsumer<Task> {

    private final AtomicInteger pendingTasks = new AtomicInteger(0);
    private final AtomicLong currentLoad = new AtomicLong(0);

    Consumer<Task> innerConsumer;

    public BigPrinter(Consumer<Task> innerConsumer) {
        this.innerConsumer = innerConsumer;
    }

    private boolean stopped = false;
    @Override
    public boolean isNotStopped() {
        return !stopped;
    }

    public void shutdown() {
        stopped = true;
    }

    @Override
    public boolean canAcceptTask(Task task) {
        return currentLoad.get() < 10 || pendingTasks.get() == 0;
    }

    @Override
    public void accept(Task task) {
        pendingTasks.incrementAndGet();
        currentLoad.addAndGet(task.load);

        // Real consumer
        System.out.println("BigPrinter Submit -> " + task);
        innerConsumer.accept(task);
//        try {
//            int seconds = task.load < 10 ? 1 : 3;
//            Thread.sleep(seconds * 1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("# BigPrinter End <-" + task);

        currentLoad.addAndGet(-task.load);
        pendingTasks.decrementAndGet();
    }
}
