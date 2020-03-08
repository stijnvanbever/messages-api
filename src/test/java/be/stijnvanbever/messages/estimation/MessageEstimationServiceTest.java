package be.stijnvanbever.messages.estimation;

import be.stijnvanbever.messages.model.Message;
import be.stijnvanbever.messages.persistence.MessageRepository;
import be.stijnvanbever.messages.statistics.TimeSeriesForecaster;
import be.stijnvanbever.messages.test.MessageTestProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageEstimationServiceTest {
    @InjectMocks
    private MessageEstimationService service;

    @Mock
    private MessageRepository repository;

    @Mock
    private TimeSeriesForecaster forecaster;

    @Test
    public void shouldEstimateByDay_When_UnitIsDay() {
        LocalDateTime startTime = LocalDateTime.parse("2020-02-26T07:05:20");
        LocalDateTime plus1Hour = startTime.plusHours(1);
        LocalDateTime plus1Hour40 = startTime.plusHours(1).plusMinutes(40);
        LocalDateTime plus3Hour = startTime.plusHours(3);
        LocalDateTime plus5Hour = startTime.plusHours(5);
        LocalDateTime plus5Hour20 = startTime.plusHours(5).plusMinutes(20);

        List<LocalDateTime> times = List.of(startTime, plus1Hour, plus1Hour40, plus3Hour, plus5Hour, plus5Hour20);
        List<Message> messages = times.stream()
                .map(MessageTestProvider::aMessageWithSentDate)
                .collect(Collectors.toList());

        when(repository.findBySentDateGreaterThan(any())).thenReturn(messages);
        when(forecaster.forecastRemaining(any(), any(), eq(EstimationUnit.DAY))).thenReturn(42);

        MessageEstimation messageEstimation = service.estimateRemainingMessages(EstimationUnit.DAY);

        verify(forecaster).forecastRemaining(eq(times), any(), eq(EstimationUnit.DAY));

        assertThat(messageEstimation.getTotalRemaining()).isEqualTo(42);
        assertThat(messageEstimation.getEstimationUnit()).isEqualTo(EstimationUnit.DAY);
    }
}