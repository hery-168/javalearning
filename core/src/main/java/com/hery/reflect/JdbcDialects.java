package com.hery.reflect;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName JdbcDialects
 * @Description TODO
 * @Date 2021/10/13 13:56
 * @Author yongheng
 * @Version V1.0
 **/
public final class JdbcDialects {

    private static final List<JdbcDialect> DIALECTS = Arrays.asList(
            new DerbyDialect(),
            new MySQLDialect()
    );
    public static Optional<JdbcDialect> get(String url) {
        for (JdbcDialect dialect : DIALECTS) {
            if (dialect.canHandle(url)) {
                return Optional.of(dialect);
            }
        }
        return Optional.empty();
    }
}
