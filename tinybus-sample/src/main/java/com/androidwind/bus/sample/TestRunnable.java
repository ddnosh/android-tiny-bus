package com.androidwind.bus.sample;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TestRunnable implements Runnable {

    public static int result = 0;// class variables

    private BusCallBack mBusCallBack;

    private StringBuilder sb;

    public void setBusCallBack(BusCallBack mBusCallBack) {
        this.mBusCallBack = mBusCallBack;
    }

    public void setSB(StringBuilder sb) {
        this.sb = sb;
    }

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 100; i++) {
                result++;
                System.out.println(Thread.currentThread().getName() + " : " + result);
                sb.append(Thread.currentThread().getName() + " : " + result).append("\n");
            }
            mBusCallBack.getResult(sb.toString());
        }
    }

    public interface BusCallBack {
        void getResult(String content);
    }

}
