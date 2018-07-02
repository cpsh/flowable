package com.sun.health.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFSFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by 华硕 on 2018-07-02.
 */
@EnableAutoConfiguration
@SpringBootTest(classes = {MongoDBDemo1.class})
@RunWith(SpringRunner.class)
public class MongoDBDemo1 {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Test
    @Profile("dev")
    public void test1() {
        Query query = new Query();

        DBObject dbObject = new BasicDBObject();
        dbObject.put("subject", "语文");

        DBCursor result = mongoTemplate.getCollection("scores").find(dbObject);
        while(result.hasNext()) {
            DBObject next = result.next();
            for (String s : next.keySet()) {
                System.out.println(s + ":" + next.get(s));
            }
        }

    }

    @Test
    @Profile("dev")
    public void test2() {
        User user = new User();
        user.setName("孙健");
        user.setAge(25);
        user.setGender("男");
        mongoTemplate.insert(user);
    }

    @Test
    @Profile("dev")
    public void test3() {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("subject").is("语文");
        query.addCriteria(criteria);
        List<Score> scores = mongoTemplate.find(query, Score.class);
        for (Score score : scores) {
            System.out.println(score);
        }
    }

    @Test
    @Profile("dev")
    public void test4() {
        List<User> users = mongoTemplate.findAll(User.class);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    @Profile("dev")
    public void test5() {
        try {
            GridFSFile gridFSFile = gridFsTemplate.store(new FileInputStream("F:\\BaiduYunDownload\\Gill - R.O.A.D PROJECT #1.rar"), "Gil.rar");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
