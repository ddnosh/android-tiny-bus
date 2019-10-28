package com.androidwind.bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TinyBusManager {

    private static volatile TinyBusManager mTinyBusManager;

    private ConcurrentHashMap<Class<?>, List<TinyValue>> hashMap = new ConcurrentHashMap<>();

    public static TinyBusManager getInstance() {
        if (mTinyBusManager == null) {
            synchronized (TinyBusManager.class) {
                if (mTinyBusManager == null) {
                    mTinyBusManager = new TinyBusManager();
                }
            }
        }
        return mTinyBusManager;
    }

    public void add(Class<?> cls, TinyValue tinyValue) {
        List<TinyValue> list = get(cls);
        if (list == null) {
            list = new ArrayList<>();
            list.add(tinyValue);
            hashMap.put(cls, list);
        } else {
            boolean isExisted = false;
            for (TinyValue value : list) {
                if (value.getObject() != null && value.getObject().equals(tinyValue.getObject())) {
                    isExisted = true;
                    break;
                }
            }
            if (!isExisted) {
                list.add(tinyValue);
            }
        }
    }

    public List<TinyValue> get(Class<?> cls) {
        return hashMap.get(cls);
    }

    public void remove(Class<?> cls) {
        for (Map.Entry<Class<?>, List<TinyValue>> entry : hashMap.entrySet()) {
            if (entry.getValue()!=null) {
                List<TinyValue> list = entry.getValue();
                Iterator<TinyValue> iterator = list.iterator();
                while (iterator.hasNext()) {
                    TinyValue tinyValue = iterator.next();
                    if (tinyValue.getObject() != null && tinyValue.getObject().getClass().equals(cls)) {
                        iterator.remove();
                    }
                }
            }
        }
    }

}
