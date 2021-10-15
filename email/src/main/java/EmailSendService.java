import br.com.ourmind.ecommerce.commons.services.KafkaConsumerService;
import br.com.ourmind.ecommerce.email.models.Email;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class EmailSendService {

    public static void main(String args[]){
        var emailService = new EmailSendService();
        try(var service = new KafkaConsumerService<Email>(
                "ECOMMERCE_SEND_EMAIL",
                EmailSendService.class.getSimpleName(),
                emailService::parse,
                Email.class,
                Map.of())){
            service.run();
        }
    }

    public void parse(ConsumerRecord<String, Email> record){
        System.out.println("Email consumido!");
        System.out.println("Offset: "+record.offset());
        System.out.println("Partition: "+record.partition());
        System.out.println("Value: "+record.value());
        System.out.println("Key: "+record.key());
        System.out.println("###########################################");
    }
}
