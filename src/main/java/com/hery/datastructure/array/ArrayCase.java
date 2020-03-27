package com.hery.datastructure.array;

/**
 * @author heng
 * @date 2019-09-30 11:09
 * @desc 数组
 */
public class ArrayCase {
    public static void main(String[] args) {
//        test();

        // 解决数组长度不可变的问题

    }


    // 数组基本操作
    public static void test() {
        // 创建数据
        int[] arr = new int[3];
        arr[0] = 20;
        arr[1] = 10;
        // 遍历数据
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        // 查看数组
        System.out.println(arr[0]);
        // 创建数组并赋值
        int[] arr2 = new int[]{1, 2, 3, 4, 5};
        System.out.println(arr2.length);
        // 数组的长度是不可变得
    }
}
