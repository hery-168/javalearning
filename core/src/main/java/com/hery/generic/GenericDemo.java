package com.hery.generic;

/**
 * @ClassName GenericDemo
 * @Description 泛型
 * @Date 2021/11/8 18:48
 * @Author yongheng
 * @Version V1.0
 **/
public class GenericDemo {
    public static void main(String[] args) {
        //MyPrint.print("hello");
        //MyPrint.print(2);

        /**
         * 可以这样使用
         */
        MyPrint2 myPrint2 = new MyPrint2();
        myPrint2.print("hello");
        myPrint2.show(2);

        MyPrint2 print = new MyPrint2<String>();
        print.print("string....");
        print.show(3);
    }
}

/**
 * 泛型方法，在方法返利类型前 用<T> 来标识
 */
class MyPrint {
    static <T> void print(T t) {
        System.out.println(t);
    }

    static <T> T getV(T t) {
        System.out.println(t);
        return t;
    }

    static <T> void show(T t) {
        System.out.println(t);
    }
}

class MyPrint2<T> {
    void print(T t) {
        System.out.println(t);
    }

    void show(T t) {
        System.out.println(t);
    }
}