package be.stijnvanbever.messages.statistics;

import be.stijnvanbever.messages.estimation.EstimationUnit;
import be.stijnvanbever.messages.mockdata.MockTimesDataCreator;
import be.stijnvanbever.messages.mockdata.MockTimesDataRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static be.stijnvanbever.messages.mockdata.MockTimesDataRequest.TimeIncreaser.DAY;
import static be.stijnvanbever.messages.mockdata.MockTimesDataRequest.TimeIncreaser.HOUR;
import static org.assertj.core.api.Assertions.assertThat;

class TimeSeriesForecasterTest {
    private TimeSeriesForecaster timeSeriesForecaster = new TimeSeriesForecaster();
    private MockTimesDataCreator dataCreator = new MockTimesDataCreator();

    @Test
    public void shouldForecastRemaining_When_TestingPerDay() {
        LocalDateTime startOfDay = LocalDate.parse("2020-02-26").atStartOfDay();
        LocalDateTime currentTime = startOfDay.withHour(16).withMinute(37);

        List<LocalDateTime> times = dataCreator.createMockData(
                new MockTimesDataRequest(startOfDay, HOUR, List.of(4, 3, 0, 8, 0, 5, 7, 2, 6, 5, 4, 2, 0, 1, 5))); // 52
        times.add(currentTime);

        int remaining = timeSeriesForecaster.forecastRemaining(times, currentTime, EstimationUnit.DAY);
        assertThat(remaining).isEqualTo(27); // (52/15) * 8
    }

    @Test
    public void shouldForecastRemaining_When_TestingPerWeek() {
        LocalDateTime startOfWeek = LocalDate.parse("2020-02-24").atStartOfDay();
        LocalDateTime currentTime = LocalDateTime.parse("2020-02-27T13:37:56");

        List<LocalDateTime> times = dataCreator.createMockData(
                new MockTimesDataRequest(startOfWeek, DAY, List.of(32, 40, 33))); // 105
        times.add(currentTime);

        int remaining = timeSeriesForecaster.forecastRemaining(times, currentTime, EstimationUnit.WEEK);
        assertThat(remaining).isEqualTo(140); // (105/3) * 4
    }
}