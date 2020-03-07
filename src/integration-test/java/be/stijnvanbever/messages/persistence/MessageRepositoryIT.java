package be.stijnvanbever.messages.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        assertThat(messageRepository.findBySender(sender)).hasSize(expectedSize);
    }

    @ParameterizedTest
    @CsvSource({"Hermes,0","Farnsworth,1", "Bender,2", "Fry,2"})
    public void shouldReturnMessages_When_SearchingOnReceiver(String receiver, Integer expectedSize) {
        assertThat(messageRepository.findByReceiver(receiver)).hasSize(expectedSize);
    }
}
