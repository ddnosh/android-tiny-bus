package com.androidwind.bus;

import java.util.HashMap;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TinyBusManager {

    private static TinyBusManager mTinyBusManager;

    private HashMap<Class<?>, TinyHandler> hashMap = new HashMap<>();

    public static TinyBusManager getInstance() {
        if (mTinyBusManager == null) {
            return mTinyBusManager = new TinyBusManager();
        }
        return mTinyBusManager;
    }

    public void add(Class<?> cls, TinyHandler tinyHandler) {
        hashMap.put(cls, tinyHandler);
    }

    public TinyHandler get(Class<?> cls) {
        return hashMap.get(cls);
    }

    public void remove(Class<?> cls) {
        hashMap.remove(cls);
    }

}
