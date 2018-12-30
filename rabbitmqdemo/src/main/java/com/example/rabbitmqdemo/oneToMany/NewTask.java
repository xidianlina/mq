package com.example.rabbitmqdemo.oneToMany;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

public class NewTask {
    private static final String TASKQUEUENAME = "taskQueue";

    public static void main(String[] args) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASKQUEUENAME, true, false, false, null);

        String[] arg = {"li", "na", "ni", "hao"};
        String message = getMessage(args);
        channel.basicPublish("", TASKQUEUENAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

    private static String getMessage(String[] strs) {
        if (strs.length < 1) {
            return "Hello World!";
        }
        return joinStrings(strs, " ");
    }

    private static String joinStrings(String[] strs, String delimiter) {
        int length = strs.length;
        StringBuffer words = new StringBuffer(strs[0]);
        for (int i = 1; i < length; ++i) {
            words.append(delimiter).append(strs[i]);
        }
        return words.toString();
    }
}
