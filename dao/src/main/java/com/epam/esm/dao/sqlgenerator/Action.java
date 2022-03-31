package com.epam.esm.dao.sqlgenerator;

public interface Action {
    void doAction(StringBuilder sql, String key, String value);
}
