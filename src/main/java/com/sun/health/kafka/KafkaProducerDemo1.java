package com.sun.health.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.util.Properties;

/**
 * Created by 华硕 on 2018-07-05.
 */
public class KafkaProducerDemo1 {

    @Test
    public void test1() {
        Producer<String, String> producer = null;
        try {
            Properties props = new Properties();
            props.put("bootstrap.servers", "47.105.97.246:9092");
            props.put("acks", "all");
            props.put("retries", 0);
            props.put("batch.size", 16384);
            props.put("linger.ms", 1);
            props.put("buffer.memory", 33554432);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

            producer = new KafkaProducer<String, String>(props);
            for (int i = 0; i < 10; i++)
                producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));
        } finally {
            producer.close();
        }

    }

}
