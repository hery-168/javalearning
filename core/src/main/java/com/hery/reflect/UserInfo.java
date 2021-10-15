package com.hery.reflect;

/**
 * @ClassName UserInfo
 * @Description TODO
 * @Date 2021/10/13 13:39
 * @Author yongheng
 * @Version V1.0
 **/
public class UserInfo {
    private final  String name="myname";
    private final String password=new String("mypassword");
    private final  static int age=defautlValue(1);
    private final static int sex=0;
    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
    private static <T> T defautlValue(T value){
        return value;
    }
}
