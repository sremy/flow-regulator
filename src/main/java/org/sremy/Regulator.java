package org.sremy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Regulator {

    public static final int SLEEPING_DELAY_MILLIS = 100;
    BlockingQueue<Task> regulatedQueue = new ArrayBlockingQueue<>(10000);
    Thread thread = new Thread(this::run, "InputRegulator");

    Predicate<Task> isSmallTask = (Task t) -> t.load < 10;

    Consumer<Task> directSmallTaskConsumer;
    BigConsumer<Task> bigTaskConsumer;

    public Regulator(Consumer<Task> directSmallTaskConsumer, BigConsumer<Task> bigTaskConsumer) {
        this.directSmallTaskConsumer = directSmallTaskConsumer;
        this.bigTaskConsumer = bigTaskConsumer;
        thread.start();
    }

    // Accept immediately all small tasks, but enqueue big tasks on regulated queue,
    // which is dequeued periodically by the thread
    public void acceptTask(Task task) {
        if (isSmallTask.test(task)) {
            directSmallTaskConsumer.accept(task);
        } else {
            regulatedQueue.add(task);
        }
    }

    // Look periodically at queue if the consumer can take the head task
    private void run() {
        while (bigTaskConsumer.isNotStopped()) {
            Task task = regulatedQueue.peek();
            if (task != null && bigTaskConsumer.canAcceptTask(task)) {
                task = regulatedQueue.poll();
                bigTaskConsumer.accept(task);
            } else {
                try {
                    Thread.sleep(SLEEPING_DELAY_MILLIS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("[Regulator] Bye!");
    }


}
