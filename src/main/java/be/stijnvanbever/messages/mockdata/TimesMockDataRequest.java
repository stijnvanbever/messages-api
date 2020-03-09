package be.stijnvanbever.messages.mockdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class TimesMockDataRequest {
    private final LocalDateTime startTime;
    private final TimeIncreaser timeIncreaser;
    private final List<Integer> distribution;

    @JsonCreator
    public TimesMockDataRequest(
            @JsonProperty("startTime") LocalDateTime startTime,
            @JsonProperty("timeIncreaser") TimeIncreaser timeIncreaser,
            @JsonProperty("distribution") List<Integer> distribution) {
        this.startTime = startTime;
        this.timeIncreaser = timeIncreaser;
        this.distribution = distribution;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public TimeIncreaser getTimeIncreaser() {
        return timeIncreaser;
    }

    public List<Integer> getDistribution() {
        return distribution;
    }

    public enum TimeIncreaser {
        HOUR(time -> time.plusHours(1)),
        DAY (time -> time.plusDays(1));

        private Function<LocalDateTime, LocalDateTime> timeIncreaseFunction;

        TimeIncreaser(Function<LocalDateTime, LocalDateTime> timeIncreaseFunction) {
            this.timeIncreaseFunction = timeIncreaseFunction;
        }

        public LocalDateTime increaseTime(LocalDateTime localDateTime) {
            return timeIncreaseFunction.apply(localDateTime);
        }
    }
}
