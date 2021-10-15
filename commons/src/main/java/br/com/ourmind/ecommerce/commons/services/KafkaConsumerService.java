package br.com.ourmind.ecommerce.commons.services;

import br.com.ourmind.ecommerce.commons.deserializers.GsonDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

public class KafkaConsumerService<T> implements Closeable {
    private KafkaConsumer<String, T> consumer;
    private final ConsumerFunction<T> parse;
    private final String groupName;
    private Class<T> type;
    private Map<String, String> proprertiesGlobal;

    private KafkaConsumerService(String groupName, ConsumerFunction<T> parse, Class<T> type, Map<String,String> proprertiesGlobal){
        this.type = type;
        this.parse = parse;
        this.groupName = groupName;
        this.proprertiesGlobal = proprertiesGlobal;
    }
    public KafkaConsumerService(String topic, String groupName, ConsumerFunction<T> parse, Class<T> type, Map<String,String> proprertiesGlobal) {
        this(groupName, parse, type, proprertiesGlobal);
        this.consumer = new KafkaConsumer<>(properties());
        this.consumer.subscribe(Collections.singleton(topic));
    }
    public KafkaConsumerService(Pattern topic, String groupName, ConsumerFunction<T> parse, Class<T> type, Map<String,String> proprertiesGlobal) {
        this(groupName, parse, type, proprertiesGlobal);
        this.consumer = new KafkaConsumer<>(properties());
        this.consumer.subscribe(topic);
    }


    public void run() {
        while (true) {
            var records = this.consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()) {
                System.out.println("Encontrei " + records.count() + " registros");
            }
            for (var record : records) {
                parse.consume(record);
            }
        }
    }

    public  Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, this.groupName);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        // VAI PEGAR APENAS UM NO POOL
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        //Propriedade customizada para pegar no deserializer
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, this.type.getName());
        properties.putAll(this.proprertiesGlobal);
        return properties;
    }

    @Override
    public void close() {
        System.out.println("Close...");
        this.consumer.close();
    }
}