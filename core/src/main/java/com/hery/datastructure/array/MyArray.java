package com.hery.datastructure.array;

/**
 * @author heng
 * @date 2019-11-19 15:19
 * @desc 自己定义数组
 * 1、数组的插入，删除
 * 2、数组查找
 */
public class MyArray {

    //定义整形数组
    public int data[];
    // 定义数组的长度
    private int n;
    // 定义数组的实际长度
    private int count;

    // 构造函数，定义数组的大小
    public MyArray(int capacity) {
        this.data = new int[capacity];
        this.n = capacity;
        this.count = 0;// 因为一开始是没有数据的
    }

    // 根据索引返回数据
    public int find(int index) {
        if (index < 0 && index >= count) {
            return -1;// 没有数据就返回-1
        }
        return data[index];//返回数据
    }

    //插入数据到数组的自定索引上
    public boolean insert(int index, int value) {
        if (n == count) {
            System.out.println("没有位置插入了");
            return false;
        }
        //判断数组索引是否合法
        if (index < 0 && index >= count) {
            System.out.println("位置不合法");
            return false;
        }
        // 从给定的位置开始，把数据进行移动
        for (int i = count; i > index; i--) {
            data[i] = data[i - 1];
        }

        data[index] = value;
        count++;
        return true;
    }

    public boolean delete(int index) {
        // 验证
        if (index < 0 && index >= count) return false;

        for (int i = index; i < count - 1; i++) {
            data[index] = data[i + 1];
        }
        count--;
        return true;
    }

    public void print() {
        for (int i = 0; i < count; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MyArray array = new MyArray(5);
        array.print();
        array.insert(0, 0);
        array.insert(1, 1);
        array.insert(2, 2);
        array.insert(3, 3);
        array.print();
        array.delete(2);
        array.print();
    }
}
