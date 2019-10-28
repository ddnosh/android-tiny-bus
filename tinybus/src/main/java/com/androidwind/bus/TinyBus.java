package com.androidwind.bus;

import com.androidwind.task.TinyTaskExecutor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TinyBus implements ITinyBus {

    private static volatile TinyBus sTinyBus;

    public static TinyBus getInstance() {
        if (sTinyBus == null) {
            synchronized(TinyBus.class){
                if (sTinyBus == null) {
                    sTinyBus = new TinyBus();
                }
            }
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
                TinyValue tinyValue = new TinyValue();
                tinyValue.setMethodName(method.getName());
                tinyValue.setObject(object);
                Subscriber subscriber = method.getAnnotation(Subscriber.class);
                System.out.println("subscriber = " + subscriber.threadMode());
                tinyValue.setThreadMode(subscriber.threadMode());
                TinyBusManager.getInstance().add(method.getParameterTypes()[0], tinyValue);
            }
        }
    }

    @Override
    public void post(final Object object) {
        if (object == null) return;
        final List<TinyValue> list = TinyBusManager.getInstance().get(object.getClass());
        Iterator<TinyValue> iterator = list.iterator();
        while (iterator.hasNext()) {
            final TinyValue tinyValue = iterator.next();
            if (tinyValue != null && tinyValue.getObject() != null) {
                if (tinyValue.getThreadMode() == ThreadMode.MAIN) {
                    TinyTaskExecutor.postToMainThread(new Runnable() {
                        @Override
                        public void run() {
                            invoke(tinyValue, object);
                        }
                    });
                } else if (tinyValue.getThreadMode() == ThreadMode.BACKGROUND) {
                    TinyTaskExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("[post]Current thread id is: " + Thread.currentThread().getId());
                            invoke(tinyValue, object);
                        }
                    });
                }
            }
        }
    }

    @Override
    public void release(Object object) {
        TinyBusManager.getInstance().remove(object.getClass());
    }

    private void invoke(TinyValue tinyValue, Object object) {
        Method method = null;
        try {
            method = tinyValue.getObject().getClass().getMethod(tinyValue.getMethodName(), new Class[] {object.getClass()});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            method.invoke(tinyValue.getObject(), object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
