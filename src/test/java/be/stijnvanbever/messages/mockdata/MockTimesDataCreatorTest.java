package be.stijnvanbever.messages.mockdata;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static be.stijnvanbever.messages.mockdata.MockTimesDataRequest.TimeIncreaser.DAY;
import static be.stijnvanbever.messages.mockdata.MockTimesDataRequest.TimeIncreaser.HOUR;
import static org.assertj.core.api.Assertions.assertThat;

class MockTimesDataCreatorTest {
    private MockTimesDataCreator dataCreator = new MockTimesDataCreator();

    @Test
    public void shouldCreateMockDataPerHour() {
        LocalDateTime startTime = LocalDateTime.parse("2020-02-24T03:04:55");
        List<Integer> distribution = List.of(3, 2, 0, 5, 0, 4);
        MockTimesDataRequest request = new MockTimesDataRequest(startTime, HOUR, distribution);

        List<LocalDateTime> mockData = dataCreator.createMockData(request);

        assertThat(mockData).hasSize(14);
        verifyData(mockData, distribution, startTime.getHour(), LocalDateTime::getHour);
    }

    @Test
    public void shouldCreateMockDataPerDay() {
        LocalDateTime startTime = LocalDateTime.parse("2020-02-24T03:04:55");
        List<Integer> distribution = List.of(27, 31, 19);
        MockTimesDataRequest request = new MockTimesDataRequest(startTime, DAY, distribution);

        List<LocalDateTime> mockData = dataCreator.createMockData(request);

        assertThat(mockData).hasSize(77);
        verifyData(mockData, distribution, startTime.getDayOfWeek().getValue(), time -> time.getDayOfWeek().getValue());
    }

    private void verifyData(List<LocalDateTime> data, List<Integer> distribution, Integer startValue,
                            Function<LocalDateTime, Integer> valueExtractor) {
        Integer currentValue = startValue;
        for (Integer expectedSize : distribution) {
            assertThat(filterOnValue(data, valueExtractor, currentValue)).hasSize(expectedSize);
            currentValue++;
        }
    }

    private List<LocalDateTime> filterOnValue(List<LocalDateTime> data,
                                              Function<LocalDateTime, Integer> valueExtractor,
                                              Integer value) {
        return data.stream()
                .filter(time -> valueExtractor.apply(time).equals(value))
                .collect(Collectors.toList());
    }
}