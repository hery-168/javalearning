package com.hery.reflect;

/**
 * @ClassName DerbyDialect
 * @Description TODO
 * @Date 2021/10/13 14:05
 * @Author yongheng
 * @Version V1.0
 **/
 class DerbyDialect extends AbstractDialect{
    @Override
    public boolean canHandle(String url) {
        return url.startsWith("derby");
    }

    @Override
    public String dialectName() {
        return "Derby";
    }
}
