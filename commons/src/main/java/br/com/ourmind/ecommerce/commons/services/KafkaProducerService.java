package br.com.ourmind.ecommerce.commons.services;

import br.com.ourmind.ecommerce.commons.serializers.GsonSerializer;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaProducerService<T> implements Closeable {

    private final KafkaProducer<String, T> producer;

    public KafkaProducerService(){
        this.producer = new KafkaProducer<>(properties());
    }


    private Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        return properties;
    }

    public void send(String topic, String key, T value) throws ExecutionException, InterruptedException {
        var record = new ProducerRecord<>(topic, key, value);
        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }

            System.out.println("Topic: "+data.topic());
            System.out.println("Partition: "+data.partition());
            System.out.println("Offset: "+data.offset());
            System.out.println("Timestamp: "+data.timestamp());
            System.out.println("###########################################");

        };
        this.producer.send(record, callback).get();
    }

    @Override
    public void close(){
        System.out.println("Close...");
        this.producer.close();
    }
}
