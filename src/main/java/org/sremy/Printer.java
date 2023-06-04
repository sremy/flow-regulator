package org.sremy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class Printer implements Consumer<Task> {

//    ThreadFactory threadFactory = ThreadFactory
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public void accept(Task task) {
        executorService.execute(() -> run(task));
    }

    private void run(Task task) {
        System.out.println("# Printer Begin -> " + task);
        try {
            int seconds = task.load < 10 ? 1 : 4;
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("# Printer End <- " + task);
    }

    public void shutdown() {
        executorService.shutdown();
        System.out.println("[Printer] Bye!");
    }
}
