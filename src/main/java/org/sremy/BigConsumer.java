package org.sremy;

import java.util.function.Consumer;

public interface BigConsumer<T> extends Consumer<T> {

    boolean canAcceptTask(Task task);

    default boolean isNotStopped() {
        return true;
    }

    void shutdown();
}
