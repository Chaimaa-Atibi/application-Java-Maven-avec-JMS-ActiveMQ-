package tp_jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Scanner;

public class Consumer {
    public static void main(String[] args) {
        try {
            ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(
                    "tcp://localhost:61616"
            );
            Connection connection=connectionFactory.createConnection();
            connection.start();
            Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //Destination destination=session.createQueue("MyQueue");
            Destination destination=session.createTopic("MyTopic");
            MessageConsumer consumer=session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if(message instanceof TextMessage){
                        try {
                            TextMessage textMessage=(TextMessage) message;
                            System.out.println("Réception du message: "+textMessage.getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
