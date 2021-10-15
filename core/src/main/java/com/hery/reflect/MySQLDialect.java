package com.hery.reflect;

/**
 * @ClassName MySQLDialect
 * @Description TODO
 * @Date 2021/10/13 14:07
 * @Author yongheng
 * @Version V1.0
 **/
class MySQLDialect extends AbstractDialect {
    @Override
    public boolean canHandle(String url) {
        return url.startsWith("mysql");
    }

    @Override
    public String dialectName() {
        return "mysql";
    }
}
