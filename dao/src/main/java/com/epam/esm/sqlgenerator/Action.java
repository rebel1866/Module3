package com.epam.esm.sqlgenerator;

public interface Action {
    void doAction(StringBuilder sql, String key, String value);
}
