package com.sun.health.flowable.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 华硕 on 2018-04-19.
 */
public class GenericTypeHandler<E extends Object> extends BaseTypeHandler<E> {

    private Class<E> type;

    public GenericTypeHandler(Class<E> type) {
        if(type == null) {
            throw new IllegalArgumentException("type argument cannot be null!");
        }
        this.type = type;
    }

    public void setNonNullParameter(PreparedStatement preparedStatement, int i, E e, JdbcType jdbcType) throws SQLException {

    }

    public E getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return null;
    }

    public E getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    public E getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
