package capslock.tg.component.router;

import capslock.tg.model.Packet;
import com.google.common.collect.ImmutableMap;
import com.rabbitmq.client.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Created by alvin.
 */
@Component
public class MessageRouter {
    private void route(final Packet packet) throws IOException, TimeoutException {
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        final String EXCHANGE_NAME = "telegram";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "x-consistent-hash");
        String node1QueueName = "node1";
        String node2QueueName = "node2";
        String node3QueueName = "node3";
        String node4QueueName = "node4";
        String node5QueueName = "node5";
        channel.queueDeclare(node1QueueName, false, false, false, ImmutableMap.of());
        channel.queueDeclare(node2QueueName, false, false, false, ImmutableMap.of());
        channel.queueDeclare(node3QueueName, false, false, false, ImmutableMap.of());
        channel.queueDeclare(node4QueueName, false, false, false, ImmutableMap.of());
        channel.queueDeclare(node5QueueName, false, false, false, ImmutableMap.of());

        channel.queueBind(node1QueueName, EXCHANGE_NAME, "1");
        channel.queueBind(node2QueueName, EXCHANGE_NAME, "1");
        channel.queueBind(node3QueueName, EXCHANGE_NAME, "1");
        channel.queueBind(node4QueueName, EXCHANGE_NAME, "1");
        channel.queueBind(node5QueueName, EXCHANGE_NAME, "1");

        for (int i = 0; i < 10000000; i++) {
            String uuid = UUID.randomUUID().toString();
            channel.basicPublish(EXCHANGE_NAME, uuid, null, "t".getBytes());
        }


        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                    AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
//        channel.basicConsume(node1QueueName, true, consumer);
    }
}
