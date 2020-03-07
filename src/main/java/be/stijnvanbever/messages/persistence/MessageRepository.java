package be.stijnvanbever.messages.persistence;

import be.stijnvanbever.messages.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findBySentDateBetween(LocalDateTime from, LocalDateTime to);
}
