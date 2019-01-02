package com.androidwind.bus;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TestEvent {

    private int age = 18;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "TestEvent{" +
                "age=" + age +
                '}';
    }
}
