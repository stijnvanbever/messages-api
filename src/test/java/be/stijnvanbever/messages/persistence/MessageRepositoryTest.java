package be.stijnvanbever.messages.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void shouldReturnFourMessages_When_DataCorrectlyInitiated() {
        assertThat(messageRepository.findAll()).hasSize(4);
    }
}
