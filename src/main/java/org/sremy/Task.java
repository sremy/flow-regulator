package org.sremy;

public class Task {
    String name;
    Integer load;

    public Task(String name, Integer load) {
        this.name = name;
        this.load = load;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", load=" + load +
                '}';
    }
}
