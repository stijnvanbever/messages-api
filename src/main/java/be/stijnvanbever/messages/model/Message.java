package be.stijnvanbever.messages.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class Message {
    private final String sender;
    private final String receiver;
    private final String subject;
    private final String content;
    private final LocalDateTime sentDate;

    @JsonCreator
    public Message(
            @JsonProperty("sender") String sender,
            @JsonProperty("receiver") String receiver,
            @JsonProperty("subject") String subject,
            @JsonProperty("content") String content,
            @JsonProperty("sentDate") LocalDateTime sentDate) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
        this.sentDate = sentDate;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getSentDate() {
        return sentDate;
    }
}
