package com.hua.activity.fanshe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by sundh on 2015/11/19.
 */
public class TestRef {

    public static void test() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        Foo foo = new Foo("这个一个Foo对象！");
        Class clazz = foo.getClass();
        Method m1 = clazz.getDeclaredMethod("outInfo");
        clazz.getDeclaredField("msg");
        Method m2 = clazz.getDeclaredMethod("setMsg", String.class);
        Method m3 = clazz.getDeclaredMethod("getMsg");
        m1.invoke(foo);
        m2.invoke(foo, "重新设置msg信息！");
        String msg = (String) m3.invoke(foo);
        System.out.println(msg);
    }
}

class Foo {
    private String msg;
    private String mWay = "adasda";
    protected  String ll = "kKK";
    public Foo(String msg) {
        this.msg = msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void outInfo() {
        System.out.println("这是测试Java反射的测试类");
    }
}

/**
 * 控制台输出结果：
 这是测试Java反射的测试类
 重新设置msg信息！

 Process finished with exit code 0
 *
 * */
