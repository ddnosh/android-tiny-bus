package com.androidwind.bus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscriber {
    ThreadMode threadMode() default ThreadMode.MAIN;
}
