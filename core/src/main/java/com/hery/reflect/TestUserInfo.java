package com.hery.reflect;

/**
 * @ClassName TestUserInfo
 * @Description TODO
 * @Date 2021/10/13 13:36
 * @Author yongheng
 * @Version V1.0
 **/
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class TestUserInfo {

    public static final void main(String[] args)throws Exception{
        UserInfo userInfo=new UserInfo();
        System.out.println("修改之前："+userInfo);

        modify(userInfo,"name","myname_1");
        modify(userInfo,"password","password_1");
        modify(userInfo,"age",21);
        modify(userInfo,"sex",1);
        System.out.println("修改之后:"+userInfo);
    }

    public static void modify(Object object, String fieldName, Object newFieldValue) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        if(Modifier.isFinal(field.getModifiers())){
            Field modifiersField = Field.class.getDeclaredField("modifiers");//1.获取Field对象
            modifiersField.setAccessible(true); //2.Field 的 modifiers 是私有的
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);//3.擦除final修饰
        }
        if(!field.isAccessible()) {
            field.setAccessible(true);
        }
        field.set(object, newFieldValue);
    }
}
