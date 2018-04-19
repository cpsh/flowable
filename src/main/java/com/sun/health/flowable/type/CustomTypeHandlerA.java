package com.sun.health.flowable.type;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 华硕 on 2018-04-19.
 */
@MappedTypes(String.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class CustomTypeHandlerA implements TypeHandler<String> {


    public void setParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {

    }

    public String getResult(ResultSet resultSet, String s) throws SQLException {
        return null;
    }

    public String getResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    public String getResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
