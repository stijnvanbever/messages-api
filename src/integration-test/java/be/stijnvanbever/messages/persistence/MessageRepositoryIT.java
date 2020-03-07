package be.stijnvanbever.messages.persistence;

import be.stijnvanbever.messages.model.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageRepositoryIT {
    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void shouldReturnFourMessages_When_DataCorrectlyInitiated() {
        assertThat(messageRepository.findAll()).hasSize(5);
    }

    @ParameterizedTest
    @CsvSource({"Farnsworth,1", "Bender,1", "Hermes,1", "Fry,2"})
    public void shouldReturnMessages_When_SearchingOnSender(String sender, Integer expectedSize) {
        Message message = new Message(sender, null, null, null, null);
        assertThat(messageRepository.findAll(Example.of(message))).hasSize(expectedSize);
    }

    @ParameterizedTest
    @CsvSource({"Hermes,0","Farnsworth,1", "Bender,2", "Fry,2"})
    public void shouldReturnMessages_When_SearchingOnReceiver(String receiver, Integer expectedSize) {
        Message message = new Message(null, receiver, null, null, null);
        assertThat(messageRepository.findAll(Example.of(message))).hasSize(expectedSize);
    }

    @Test
    public void shouldReturnMessages_When_SearchingBetweenSentDates() {
        LocalDateTime from = LocalDateTime.parse("2020-02-25T00:00:00");
        LocalDateTime to = LocalDateTime.parse("2020-02-26T23:59:59");

        List<Message> messages = messageRepository.findBySentDateBetween(from, to);
        assertThat(messages)
                .hasSize(2)
                .extracting(Message::getSubject)
                .containsExactlyInAnyOrder("RE: Good news", "Kill all humans");
    }
}
