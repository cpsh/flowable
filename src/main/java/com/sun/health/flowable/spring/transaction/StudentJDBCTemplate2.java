package com.sun.health.flowable.spring.transaction;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionException;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Created by 华硕 on 2018-04-28.
 */
public class StudentJDBCTemplate2 implements StudentDao {

    private JdbcTemplate jdbcTemplateObj;


    public void setDatasource(DataSource datasource) {
        this.jdbcTemplateObj = new JdbcTemplate(datasource);
    }

    public void create(String name, Integer age, Integer marks, Integer year) {

        try {
            String SQL1 = "INSERT INTO student(name, age) VALUES(?,?)";
            jdbcTemplateObj.update(SQL1, name, age);
            String SQL2 = "SELECT MAX(id) FROM student";
            Map<String, Object> resultMap = jdbcTemplateObj.queryForMap(SQL2);
            int sid = Integer.valueOf(resultMap.get("MAX(id)").toString());
            String SQL3 = "UPDATE student SET sid = ?, marks = ?, year = ? WHERE id = ?";
            jdbcTemplateObj.update(SQL3,sid, marks, year, sid);
            System.out.println("Create new student");
        } catch (DataAccessException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
    }

    public List<StudentMarks> listStudents() {
        String SQL = "SELECT * FROM student";
        List<StudentMarks> students = jdbcTemplateObj.query(SQL, new StudentMarksMapper());
        return students;
    }
}

