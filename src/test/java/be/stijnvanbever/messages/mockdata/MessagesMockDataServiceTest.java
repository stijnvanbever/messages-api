package be.stijnvanbever.messages.mockdata;

import be.stijnvanbever.messages.model.Message;
import be.stijnvanbever.messages.persistence.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static be.stijnvanbever.messages.mockdata.MessagesMockDataService.*;
import static be.stijnvanbever.messages.mockdata.MessagesMockDataService.CONTENT;
import static be.stijnvanbever.messages.mockdata.TimesMockDataRequest.TimeIncreaser.HOUR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessagesMockDataServiceTest {
    @InjectMocks
    private MessagesMockDataService service;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private TimesMockDataCreator timesMockDataCreator;

    @Captor
    private ArgumentCaptor<List<Message>> messageCaptor;

    @Test
    public void shouldCreateMessagesAndPersist_When_ProvidingTimesMockDataRequest() {
        LocalDateTime fixedTime = LocalDateTime.now();

        TimesMockDataRequest mockDataRequest = new TimesMockDataRequest(
                LocalDateTime.parse("2020-02-26T14:50:50"),
                HOUR,
                List.of(3,4,0,2,1) // 10 elements expected
        );

        List<LocalDateTime> times = IntStream.range(0, 10)
                .mapToObj(i -> fixedTime)
                .collect(Collectors.toList());

        when(timesMockDataCreator.createMockData(mockDataRequest)).thenReturn(times);

        service.createMessagesForTimeAndPersist(mockDataRequest);

        verify(messageRepository).saveAll(messageCaptor.capture());

        List<Message> persistedMessages = messageCaptor.getValue();
        assertThat(persistedMessages).hasSize(10);
        assertThat(persistedMessages).allSatisfy(message -> {
            assertThat(message.getSender()).isEqualTo(SENDER);
            assertThat(message.getReceiver()).isEqualTo(RECEIVER);
            assertThat(message.getSubject()).isEqualTo(SUBJECT);
            assertThat(message.getContent()).isEqualTo(CONTENT);
            assertThat(message.getSentDate()).isEqualTo(fixedTime);
        });
    }
}