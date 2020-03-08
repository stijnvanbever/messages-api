package be.stijnvanbever.messages.statistics;

public class MessageEstimation {
    private final Integer totalRemaining;
    private final EstimationUnit estimationUnit;

    public MessageEstimation(Integer totalRemaining, EstimationUnit estimationUnit) {
        this.totalRemaining = totalRemaining;
        this.estimationUnit = estimationUnit;
    }

    public Integer getTotalRemaining() {
        return totalRemaining;
    }

    public EstimationUnit getEstimationUnit() {
        return estimationUnit;
    }
}
