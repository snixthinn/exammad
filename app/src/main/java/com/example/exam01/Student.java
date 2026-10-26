package com.example.exam01;

public class Student {
    private final String id;
    private final String name;
    private final boolean enrolled;

    public Student(String id, String name, boolean enrolled) {
        this.id = id;
        this.name = name;
        this.enrolled = enrolled;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public boolean isEnrolled() { return enrolled; }

    @Override
    public String toString() {
        return id + " - " + name + " (" + (enrolled ? "Enrolled" : "Not enrolled") + ")";
    }
}