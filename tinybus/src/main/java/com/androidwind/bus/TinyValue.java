package com.androidwind.bus;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TinyValue {

    private String methodName;

    private Object object;

    private ThreadMode threadMode;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }
}
