# ECOMMERCE KAFKA
Projeto de estudo utilizando o kafka para se comunicar com microservices

* **Order Microservice (Producer)**<br>
  Irá produzir mensagens em dois tópicos ECOMMERCE_SEND_EMAIL e ECOMMERCE_NEW_ORDER
<br><br>
* **Fraud Detector Microservice (Consumer)**<br>
  Esse consume estará inscrito (Subscribe) no tópico ECOMMERCE_NEW_ORDER e irá realizar algum processamento de detecção de fraude de um pedido
  <br><br>
* **Email Microservice (Consumer)**<br>
  esse consumer estará inscrito (Subscribe) no tópico ECOMMERCE_SEND_EMAIL e irá enviar um email para o cliente
  <br><br>
* **Log Microservice (Consumer)**<br>
  esse consumer estará inscrito (Subscribe) nos dois tópicos ECOMMERCE_SEND_EMAIL e ECOMMERCE_NEW_ORDER e irá armazenar os logs para fins de auditoria
  <br><br>
* **Commons**<br>
esse projeto cria um abstração da biblioteca kafka-clients:
  * KafkaProducerServicer: essa class é utilizada para produzir mensagens com serialização de objetos
  * KafkaConsumerService: essa class utilizada para consumir mensagens com deserialização de objetos
  * ConsumerFunction: essa interface é utilizada para processar as mensagens recebidas pelo consumer
### Rodar o Zookeeper (**Linux**) 

`bin/zookeeper-server-start.sh config/zookeeper.properties`
### Rodar o Kafka (Linux)

`bin/kafka-server-start.sh config/server.properties`
<br><br>

![Diagram](https://user-images.githubusercontent.com/46445331/137503663-a9ff7b2c-8af4-4b80-b74e-f5414f26d081.png)