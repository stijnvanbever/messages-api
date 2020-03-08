package be.stijnvanbever.messages.estimation;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.function.Function;

public enum EstimationUnit {
    DAY(
            localDateTime -> localDateTime.toLocalDate().atStartOfDay(),
            localDateTime -> localDateTime.minusHours(1).withMinute(59).withSecond(59),
            localDateTime -> localDateTime.toLocalDate().atTime(23, 59, 59),
            LocalDateTime::getHour
    ),
    WEEK(
            localDateTime -> localDateTime.with(DayOfWeek.MONDAY).toLocalDate().atStartOfDay(),
            localDateTime -> localDateTime.minusDays(1).withHour(23).withMinute(59).withSecond(59),
            localDateTime -> localDateTime.with(DayOfWeek.SUNDAY).toLocalDate().atTime(23, 59, 59),
            localDateTime -> localDateTime.getDayOfWeek().getValue()
    );

    private Function<LocalDateTime, LocalDateTime> startEstimationRangeFunction;
    private Function<LocalDateTime, LocalDateTime> endEstimationRangeFunction;
    private Function<LocalDateTime, LocalDateTime> endUnitFunction;
    private Function<LocalDateTime, Integer> unitBelowExtractor;

    EstimationUnit(Function<LocalDateTime, LocalDateTime> startEstimationRangeFunction,
                   Function<LocalDateTime, LocalDateTime> endEstimationRangeFunction,
                   Function<LocalDateTime, LocalDateTime> endUnitFunction,
                   Function<LocalDateTime, Integer> unitBelowExtractor) {
        this.startEstimationRangeFunction = startEstimationRangeFunction;
        this.endEstimationRangeFunction = endEstimationRangeFunction;
        this.endUnitFunction = endUnitFunction;
        this.unitBelowExtractor = unitBelowExtractor;
    }

    public LocalDateTime toBeginningRange(LocalDateTime localDateTime) {
        return startEstimationRangeFunction.apply(localDateTime);
    }

    public LocalDateTime toEndRange(LocalDateTime localDateTime) {
        return endEstimationRangeFunction.apply(localDateTime);
    }

    public LocalDateTime toEnd(LocalDateTime localDateTime) {
        return endUnitFunction.apply(localDateTime);
    }

    public Function<LocalDateTime, Integer> getUnitBelowExtractor() {
        return unitBelowExtractor;
    }
}
