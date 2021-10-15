package com.hery.reflect;

/**
 * @ClassName JdbcDialect
 * @Description TODO
 * @Date 2021/10/13 14:01
 * @Author yongheng
 * @Version V1.0
 **/
public interface JdbcDialect {
    boolean canHandle(String url);
    String dialectName();
}
