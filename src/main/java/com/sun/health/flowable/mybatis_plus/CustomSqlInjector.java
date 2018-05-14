package com.sun.health.flowable.mybatis_plus;

import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.mapper.AutoSqlInjector;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;

/**
 * Created by 华硕 on 2018-05-14.
 */
public class CustomSqlInjector extends AutoSqlInjector {

    @Override
    protected void injectDeleteByIdSql(boolean batch, Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        super.injectDeleteByIdSql(batch, mapperClass, modelClass, table);
        deleteAllUser(mapperClass, modelClass, table);
    }

    public void deleteAllUser(Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        String sql = "delete from " + table.getTableName();

        String method = "deleteAll";
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        this.addMappedStatement(mapperClass,
                method,
                sqlSource,
                SqlCommandType.DELETE,
                Integer.class,
                null,
                Integer.class,
                new NoKeyGenerator(),
                null,
                null);
    }
}
