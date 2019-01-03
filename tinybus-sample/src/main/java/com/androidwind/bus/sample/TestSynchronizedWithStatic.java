package com.androidwind.bus.sample;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TestSynchronizedWithStatic {
    public synchronized static String calculate(StringBuilder mStringBuilder) {
        int result = 0;
        for (int i = 0; i < 100; i++) {
            result++;
            System.out.println(Thread.currentThread().getName() + " : " + result);
            mStringBuilder.append(Thread.currentThread().getName() + " : " + result).append("\n");
        }
        return mStringBuilder.toString();
    }
}
