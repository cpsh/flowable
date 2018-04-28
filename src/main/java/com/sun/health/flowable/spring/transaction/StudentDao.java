package com.sun.health.flowable.spring.transaction;

import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by 华硕 on 2018-04-28.
 */
public interface StudentDao {

    void setDatasource(DataSource datasource);

//    void setTransactionManager(PlatformTransactionManager transactionManager);

    void create(String name, Integer age, Integer marks, Integer year);

    List<StudentMarks> listStudents();

}
