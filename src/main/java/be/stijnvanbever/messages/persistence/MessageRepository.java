package be.stijnvanbever.messages.persistence;

import be.stijnvanbever.messages.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
