package com.hery.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
public class RegistGaussDbDialect {
    public static void registGaussDbDialect() {
        try {
            Field dialects = JdbcDialects.class.getDeclaredField("DIALECTS");
            dialects.setAccessible(true);
            Field modifiers = dialects.getClass().getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(dialects, dialects.getModifiers() & ~Modifier.FINAL);
            ArrayList<JdbcDialect> jdbcDialects = new ArrayList<JdbcDialect>() {
                {
                    add(new DerbyDialect());
                    add(new MySQLDialect());
                    add(new GuassDBDialect());
                }
            };
            dialects.set(JdbcDialects.class, jdbcDialects);
            modifiers.setInt(dialects, dialects.getModifiers() & ~Modifier.FINAL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        registGaussDbDialect();
        System.out.println(JdbcDialects.get("aa"));
    }
}

