package tp_jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Scanner;

public class Producer {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Vers: ");
        String code=scanner.nextLine();
        try {
            ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(
                    "tcp://localhost:61616"
            );
            Connection connection=connectionFactory.createConnection();
            connection.start();
            Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //Destination destination=session.createQueue("MyQueue");
            Destination destination=session.createTopic("MyTopic");
            MessageProducer producer=session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            TextMessage textMessage=session.createTextMessage();
            textMessage.setText("Hello ........");
            textMessage.setStringProperty("code", code);
            producer.send(textMessage);
            System.out.println("Envoie du message");
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
