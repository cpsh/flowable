package com.sun.health.flowable.spring.transaction;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Created by 华硕 on 2018-04-28.
 */
public class StudentJDBCTemplate implements StudentDao {

    private DataSource dataSource;

    private JdbcTemplate jdbcTemplateObj;

    private PlatformTransactionManager transactionManager;

    public void setDatasource(DataSource datasource) {
        this.dataSource = datasource;
        jdbcTemplateObj = new JdbcTemplate(datasource);
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void create(String name, Integer age, Integer marks, Integer year) {
//        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
//        StudentMarks studentMarks = new StudentMarks();
//        studentMarks.setName(name);
//        studentMarks.setAge(age);
//        studentMarks.setMarks(marks);
//        studentMarks.setYear(year);
//        tran

        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(defaultTransactionDefinition);
        try {
            String SQL1 = "INSERT INTO student(name, age) VALUES(?,?)";
            jdbcTemplateObj.update(SQL1, name, age);
            String SQL2 = "SELECT MAX(id) FROM student";
            Map<String, Object> resultMap = jdbcTemplateObj.queryForMap(SQL2);
            int sid = Integer.valueOf(resultMap.get("MAX(id)").toString());
            String SQL3 = "UPDATE student SET sid = ?, marks = ?, year = ? WHERE id = ?";
            jdbcTemplateObj.update(SQL3,sid, marks, year, sid);
            System.out.println("Create new student");
            transactionManager.commit(transactionStatus);
        } catch (DataAccessException e) {
            e.printStackTrace();
            transactionManager.rollback(transactionStatus);
        } catch (TransactionException e) {
            e.printStackTrace();
            transactionManager.rollback(transactionStatus);
        }
    }

    public List<StudentMarks> listStudents() {
        String SQL = "SELECT * FROM student";
        List<StudentMarks> students = jdbcTemplateObj.query(SQL, new StudentMarksMapper());
        return students;
    }
}
