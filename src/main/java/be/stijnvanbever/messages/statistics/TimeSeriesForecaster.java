package be.stijnvanbever.messages.statistics;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TimeSeriesForecaster {

    public int forecastRemaining(List<LocalDateTime> timeList, LocalDateTime currentTime, EstimationUnit estimationUnit) {
        // exclude current time frame as the data is still ongoing and untrustworthy
        LocalDateTime endEstimation = estimationUnit.toEndRange(currentTime);
        List<LocalDateTime> timeSeries = timeList
                .stream()
                .filter(localDateTime -> localDateTime.isBefore(endEstimation))
                .collect(Collectors.toList());

        double averageCount = averageCountPerUnit(timeSeries, endEstimation, estimationUnit);
        int remainingUnits = getRemainingUnits(estimationUnit, endEstimation);
        return Double.valueOf(averageCount * remainingUnits).intValue();
    }

    private double averageCountPerUnit(List<LocalDateTime> timeList, LocalDateTime endEstimation,
                                       EstimationUnit estimationUnit) {
        Integer endEstimationUnit = estimationUnit.getUnitBelowExtractor().apply(endEstimation);
        return Integer.valueOf(timeList.size()).doubleValue() / endEstimationUnit;
    }

    private int getRemainingUnits(EstimationUnit estimationUnit, LocalDateTime endEstimation) {
        Function<LocalDateTime, Integer> unitBelowExtractor = estimationUnit.getUnitBelowExtractor();
        int currentUnit = unitBelowExtractor.apply(endEstimation);
        int endUnit = unitBelowExtractor.apply(estimationUnit.toEnd(endEstimation));

        return (endUnit) - currentUnit;
    }
}
