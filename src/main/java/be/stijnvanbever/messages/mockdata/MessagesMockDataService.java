package be.stijnvanbever.messages.mockdata;

import be.stijnvanbever.messages.model.Message;
import be.stijnvanbever.messages.persistence.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessagesMockDataService {
    public static final String SENDER = "MockSender";
    public static final String RECEIVER = "MockReceiver";
    public static final String SUBJECT = "MockSubject";
    public static final String CONTENT = "MockContent";

    private final TimesMockDataCreator timesMockDataCreator;
    private final MessageRepository messageRepository;

    public MessagesMockDataService(TimesMockDataCreator timesMockDataCreator, MessageRepository messageRepository) {
        this.timesMockDataCreator = timesMockDataCreator;
        this.messageRepository = messageRepository;
    }

    public List<Message> createMessagesForTimeAndPersist(TimesMockDataRequest timesDataRequest) {
        List<LocalDateTime> times = timesMockDataCreator.createMockData(timesDataRequest);
        List<Message> mockMessages = times.stream()
                .map(time -> new Message(SENDER, RECEIVER, SUBJECT, CONTENT, time))
                .collect(Collectors.toList());

        return messageRepository.saveAll(mockMessages);
    }

}
