package be.stijnvanbever.messages.persistence;

import be.stijnvanbever.messages.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByReceiver(String receiver);
    List<Message> findBySender(String sender);
}
