package be.stijnvanbever.messages.api;

import be.stijnvanbever.messages.model.Message;
import be.stijnvanbever.messages.persistence.MessageRepository;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {
    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public List<Message> getMessages(
            @RequestParam(value = "sender", required = false) String sender,
            @RequestParam(value = "receiver", required = false) String receiver) {
        Message messageExample = new Message(sender, receiver, null, null, null);
        return messageRepository.findAll(Example.of(messageExample));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable("id") String messageId) {
        return ResponseEntity.of(messageRepository.findById(messageId));
    }

    @PostMapping
    public ResponseEntity<Message> addMessage(@RequestBody Message message) {
        Message createdMessage = messageRepository.save(message);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdMessage.getId()).toUri();

        return ResponseEntity.created(location).body(createdMessage);
    }
}
