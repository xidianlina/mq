package com.example.rabbitmqdemo.publishSubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class EmitLog {
    private static final String EXCHANGENAME = "logs";

    public static void main(String[] args) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGENAME, "fanout");

        String[] strs = {"i", "low", "java", "and", "python"};
        String message = getMessage(strs);

        channel.basicPublish(EXCHANGENAME, "", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }

    private static String getMessage(String[] strs) {
        if (strs.length < 1) {
            return "info: Hello World!";
        }
        return joinStrings(strs, " ");
    }

    private static String joinStrings(String[] strs, String delimiter) {
        int length = strs.length;
        if (length == 0) {
            return "";
        }
        StringBuffer words = new StringBuffer(strs[0]);
        for (int i = 1; i < length; ++i) {
            words.append(delimiter).append(strs[i]);
        }
        return words.toString();
    }
}
