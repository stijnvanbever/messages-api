package be.stijnvanbever.messages.estimation;

import be.stijnvanbever.messages.estimation.EstimationUnit;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EstimationUnitTest {
    @Test
    public void shouldReturnStartOfUnit() {
        LocalDateTime localDateTime = LocalDateTime.parse("2020-02-26T22:16:50");
        assertThat(EstimationUnit.DAY.toBeginningRange(localDateTime)).isEqualTo("2020-02-26T00:00:00");
        assertThat(EstimationUnit.WEEK.toBeginningRange(localDateTime)).isEqualTo("2020-02-24T00:00:00");
    }

    @Test
    public void shouldReturnEndOfUnit() {
        LocalDateTime localDateTime = LocalDateTime.parse("2020-02-26T22:16:50");
        assertThat(EstimationUnit.DAY.toEnd(localDateTime)).isEqualTo("2020-02-26T23:59:59");
        assertThat(EstimationUnit.WEEK.toEnd(localDateTime)).isEqualTo("2020-03-01T23:59:59");
    }

    @Test
    public void shouldExtractUnitBelow() {
        LocalDateTime localDateTime = LocalDateTime.parse("2020-02-26T22:16:50");
        assertThat(EstimationUnit.DAY.getUnitBelowExtractor().apply(localDateTime)).isEqualTo(22);
        assertThat(EstimationUnit.WEEK.getUnitBelowExtractor().apply(localDateTime)).isEqualTo(3);
    }
}