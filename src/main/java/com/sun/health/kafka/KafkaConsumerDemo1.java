package com.sun.health.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;

import java.util.*;

/**
 * Created by 华硕 on 2018-07-05.
 */
public class KafkaConsumerDemo1 {

    @Test
    public void test1() {
        KafkaConsumer<String, String> consumer = null;
        try {
            Map<String, Object> consumerProperties = new HashMap<String, Object>();
            consumerProperties.put("bootstrap.servers", "47.105.97.246:9092");
            consumerProperties.put("group.id", "sj");
            consumerProperties.put("enable.auto.commit", "false");
            consumerProperties.put("auto.offset.reset", "earliest");
            consumerProperties.put("auto.commit.interval.ms", "1000");
            consumerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            consumerProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
            consumer = new KafkaConsumer<String, String>(consumerProperties);
            consumer.subscribe(Arrays.asList("test"));
            while(true) {
                ConsumerRecords<String, String> consumerRecords = consumer.poll(100);
                if(consumerRecords.count() > 0) {
                    for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                        System.out.printf("offset = %d, key = %s, value = %s \n", consumerRecord.offset(), consumerRecord.key(), consumerRecord.value());
                    }
                }
            }
        } finally {
            if(consumer != null) {
                consumer.close();
            }
        }
    }

}
