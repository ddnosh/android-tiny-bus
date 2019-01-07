package com.androidwind.bus;

import com.androidwind.task.TinyTaskExecutor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TinyBus implements ITinyBus {

    private static TinyBus sTinyBus;

    public static TinyBus getInstance() {
        if (sTinyBus == null) {
            return sTinyBus = new TinyBus();
        }
        return sTinyBus;
    }

    @Override
    public void register(Object object) {
        if (object == null) return;
        Class cls = object.getClass();//获取类实例(即类信息)
        Method[] methods = cls.getDeclaredMethods();//获取类实例的public方法
        for (Method method : methods) {
            if (method.isAnnotationPresent(Subscriber.class) &&//判断方法是否是Subscriber注释
                    method.getParameterTypes() != null//获取方法的传入参数类型
                    && method.getParameterTypes().length == 1) {//只有一个参数
                TinyHandler tinyHandler = new TinyHandler();
                tinyHandler.setMethodName(method.getName());
                tinyHandler.setObject(object);
                Subscriber subscriber = method.getAnnotation(Subscriber.class);
                System.out.println("subscriber = " + subscriber.threadMode());
                tinyHandler.setThreadMode(subscriber.threadMode());
                TinyBusManager.getInstance().add(method.getParameterTypes()[0], tinyHandler);
            }
        }
    }

    @Override
    public void post(final Object object) {
        if (object == null) return;
        final TinyHandler tinyHandler = TinyBusManager.getInstance().get(object.getClass());
        if (tinyHandler != null && tinyHandler.getObject() != null) {
            if (tinyHandler.getThreadMode() == ThreadMode.MAIN) {
                TinyTaskExecutor.postToMainThread(new Runnable() {
                    @Override
                    public void run() {
                        invoke(tinyHandler, object);
                    }
                });
            } else if (tinyHandler.getThreadMode() == ThreadMode.BACKGROUND) {
                invoke(tinyHandler, object);
            }
        }
    }

    @Override
    public void release(Object object) {
        TinyBusManager.getInstance().remove(object.getClass());
    }

    private void invoke(TinyHandler tinyHandler, Object object) {
        Method method = null;
        try {
            method = tinyHandler.getObject().getClass().getMethod(tinyHandler.getMethodName(), new Class[] {object.getClass()});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            method.invoke(tinyHandler.getObject(), object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
