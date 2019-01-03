package com.androidwind.bus.sample;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TestRunnable implements Runnable {

    private static int result = 0;// class variables

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                result++;
                System.out.println(Thread.currentThread().getName() + " : " + result);
            }
        }
    }

}
