package com.hery.reflect;

/**
 * @ClassName GuassDBDialect
 * @Description TODO
 * @Date 2021/10/13 14:10
 * @Author yongheng
 * @Version V1.0
 **/
 class GuassDBDialect extends AbstractDialect{
    @Override
    public boolean canHandle(String url) {
        return url.startsWith("guass");
    }

    @Override
    public String dialectName() {
        return "guass";
    }
}
