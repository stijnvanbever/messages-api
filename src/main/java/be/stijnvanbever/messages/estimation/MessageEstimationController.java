package be.stijnvanbever.messages.estimation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/estimation/messages")
public class MessageEstimationController {
    private final MessageEstimationService estimationService;

    public MessageEstimationController(MessageEstimationService estimationService) {
        this.estimationService = estimationService;
    }

    @GetMapping
    public MessageEstimation estimateRemaining(@RequestParam("estimationUnit") EstimationUnit estimationUnit) {
        return estimationService.estimateRemainingMessages(estimationUnit);
    }
}
