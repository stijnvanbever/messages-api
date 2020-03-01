package be.stijnvanbever.messages.api;

import be.stijnvanbever.messages.model.Message;
import be.stijnvanbever.messages.persistence.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static be.stijnvanbever.messages.test.MessageTestProvider.aMessageWithSenderAndReceiver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {
    @InjectMocks
    private MessageController controller;

    @Mock
    private MessageRepository repository;

    @Test
    public void shouldFetchMessagesFromService_When_GettingAllMessages() {
        List<Message> messages = List.of(
                aMessageWithSenderAndReceiver("X", "Y"),
                aMessageWithSenderAndReceiver("Y", "X"));

        when(repository.findAll()).thenReturn(messages);

        List<Message> allMessages = controller.getMessages();

        assertThat(allMessages).containsAll(messages);
    }
}