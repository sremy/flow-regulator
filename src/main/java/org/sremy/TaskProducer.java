package org.sremy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TaskProducer implements Supplier<Task> {

    private final List<Task> taskList = new ArrayList<>();
    private int index = 0;
    public TaskProducer() {
        taskList.add(new Task("task1", 1));
        taskList.add(new Task("task2", 20));
        taskList.add(new Task("task3", 10));
        taskList.add(new Task("task4", 10));
        taskList.add(new Task("task5", 2));
        taskList.add(new Task("task6", 50));
        taskList.add(new Task("task7", 1));
        taskList.add(new Task("task8", 2));
        taskList.add(new Task("task9", 1));
    }

    @Override
    public Task get() {
        if (index >= taskList.size()) {
            index = 0;
        }
        return taskList.get(index++);

    }
}
