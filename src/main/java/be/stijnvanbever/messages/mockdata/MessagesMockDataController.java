package be.stijnvanbever.messages.mockdata;

import be.stijnvanbever.messages.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mockdata/messages")
public class MessagesMockDataController {
    private final MessagesMockDataService mockDataService;

    public MessagesMockDataController(MessagesMockDataService mockDataService) {
        this.mockDataService = mockDataService;
    }

    @PostMapping
    public ResponseEntity<List<Message>> createMockData(@RequestBody TimesMockDataRequest mockDataRequest) {
        List<Message> mockMessages = mockDataService.createMessagesForTimeAndPersist(mockDataRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(mockMessages);
    }
}
