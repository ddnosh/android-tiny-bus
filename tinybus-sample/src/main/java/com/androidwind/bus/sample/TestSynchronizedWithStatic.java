package com.androidwind.bus.sample;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TestSynchronizedWithStatic {
    public synchronized static void calculate() {
        int result = 0;
        for (int i = 0; i < 100; i++) {
            result++;
            System.out.println(Thread.currentThread().getName() + " : " + result);
        }
    }
}
