package br.com.ourmind.ecommerce.order;
import br.com.ourmind.ecommerce.commons.services.KafkaProducerService;
import br.com.ourmind.ecommerce.order.models.Email;
import br.com.ourmind.ecommerce.order.models.Order;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String args[]) throws ExecutionException, InterruptedException {
       try( var service = new KafkaProducerService<Order>()) {
           try (var serviceEmail = new KafkaProducerService<Email>()) {
               System.out.println("aqui");
               var userId = UUID.randomUUID().toString();
               var orderId = UUID.randomUUID().toString();
               var amount = new BigDecimal(Math.random() * 5000 + 1);
               var order = new Order(userId, orderId, amount);

               service.send("ECOMMERCE_NEW_ORDER", userId, order);
               serviceEmail.send("ECOMMERCE_SEND_EMAIL", userId, new Email("sad0", "sad"));
           }
       }
    }

}
