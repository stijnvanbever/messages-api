package be.stijnvanbever.messages.test;

import be.stijnvanbever.messages.model.Message;

import java.time.LocalDateTime;

public class MessageTestProvider {
    private MessageTestProvider() {

    }

    public static Message aMessage() {
        return aMessageWithSenderAndReceiver("Sender", "Receiver");
    }

    public static Message aMessageWithSenderAndReceiver(String sender, String receiver) {
        return new Message(sender, receiver, "Subject", "Content", LocalDateTime.now());
    }

    public static Message aMessageWithSentDate(LocalDateTime sentDate) {
        return new Message("Sender", "Receiver", "Subject", "Content", sentDate);
    }
}
