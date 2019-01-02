package com.androidwind.bus;

import java.lang.reflect.InvocationTargetException;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface ITinyBus {

    void register(Object object);

    void post(Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    void release(Object object);
}
