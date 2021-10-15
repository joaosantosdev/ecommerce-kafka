package br.com.ourmind.fraudDetector;
import br.com.ourmind.ecommerce.commons.services.KafkaConsumerService;
import br.com.ourmind.fraudDetector.models.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;


public class FraudDetectorService {

    public static void main(String args[]){
        var fraudService = new FraudDetectorService();
        try(var service = new KafkaConsumerService<Order>(
                "ECOMMERCE_NEW_ORDER",
                FraudDetectorService.class.getSimpleName(),
                fraudService::parse,
                Order.class,
                Map.of())){
            service.run();
        }
    }

    public void parse(ConsumerRecord<String, Order> record){
        System.out.println("Processando fraude...");
        System.out.println("Offset: "+record.offset());
        System.out.println("Partition: "+record.partition());
        System.out.println("Value: "+record.value());
        System.out.println("Key: "+record.key());
        System.out.println("###########################################");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
