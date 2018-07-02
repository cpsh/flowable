package com.sun.health.mongodb;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by 华硕 on 2018-07-02.
 */
public class Score implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    private String _id;

    private String name;

    private String subject;

    private Float score;

    public Score() {
    }

    public Score(String _id, String name, String subject, Float score) {
        this._id = _id;
        this.name = name;
        this.subject = subject;
        this.score = score;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", score=" + score +
                '}';
    }
}
