package be.stijnvanbever.messages.mockdata;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockTimesDataCreator {
    public List<LocalDateTime> createMockData(MockTimesDataRequest request) {
        List<LocalDateTime> mockData = new ArrayList<>();
        LocalDateTime currentTime = request.getStartTime();

        for (Integer size : request.getDistribution()) {
            for (int i = 0; i < size; i++) {
                mockData.add(currentTime);
            }
            currentTime = request.getTimeIncreaser().increaseTime(currentTime);
        }

        return mockData;
    }
}
