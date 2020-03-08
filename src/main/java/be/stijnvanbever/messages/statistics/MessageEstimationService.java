package be.stijnvanbever.messages.statistics;

import be.stijnvanbever.messages.model.Message;
import be.stijnvanbever.messages.persistence.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageEstimationService {
    private final MessageRepository messageRepository;
    private final TimeSeriesForecaster timeSeriesForecaster;

    public MessageEstimationService(MessageRepository messageRepository, TimeSeriesForecaster timeSeriesForecaster) {
        this.messageRepository = messageRepository;
        this.timeSeriesForecaster = timeSeriesForecaster;
    }

    public MessageEstimation estimateRemainingMessages(EstimationUnit estimationUnit) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfEstimation = estimationUnit.toBeginningRange(now);

        List<Message> messages = messageRepository.findBySentDateBetween(startOfEstimation, now);
        List<LocalDateTime> times = messages.stream().map(Message::getSentDate).collect(Collectors.toList());
        int remainingForecast = timeSeriesForecaster.forecastRemaining(times, now, estimationUnit);

        return new MessageEstimation(remainingForecast, estimationUnit);
    }
}
