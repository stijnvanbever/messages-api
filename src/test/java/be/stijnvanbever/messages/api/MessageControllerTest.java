package be.stijnvanbever.messages.api;

import be.stijnvanbever.messages.model.Message;
import be.stijnvanbever.messages.persistence.MessageRepository;
import be.stijnvanbever.messages.test.MessageTestProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static be.stijnvanbever.messages.test.MessageTestProvider.aMessageWithSenderAndReceiver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {
    @InjectMocks
    private MessageController controller;

    @Mock
    private MessageRepository repository;

    @Test
    public void shouldFetchMessages_When_GettingAllMessages() {
        List<Message> messages = List.of(
                aMessageWithSenderAndReceiver("X", "Y"),
                aMessageWithSenderAndReceiver("Y", "X"));

        when(repository.findAll(any(Example.class))).thenReturn(messages);

        List<Message> allMessages = controller.getMessages(null, null);

        assertThat(allMessages).containsAll(messages);
    }

    @Test
    public void shouldFetchMessage_When_GettingById() {
        String id = UUID.randomUUID().toString();
        Message message = MessageTestProvider.aMessage();
        when(repository.findById(id)).thenReturn(Optional.of(message));

        ResponseEntity<Message> response = controller.getMessageById(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(message);
    }

    @Test
    public void shouldReturnNotFound_When_GettingByNonExistingId() {
        String id = UUID.randomUUID().toString();
        when(repository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Message> response = controller.getMessageById(id);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    public void shouldCreateMessageWithId_When_AddingAMessage() {
         Message message = MessageTestProvider.aMessage();

         when(repository.save(message)).thenAnswer(this::messageWithId);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        ResponseEntity<Message> response = controller.addMessage(message);

        verify(repository).save(message);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isNotNull();
    }

    private Message messageWithId(InvocationOnMock invocation) {
        Message message = invocation.getArgument(0);
        ReflectionTestUtils.setField(message, "id", UUID.randomUUID().toString());
        return message;
    }
}