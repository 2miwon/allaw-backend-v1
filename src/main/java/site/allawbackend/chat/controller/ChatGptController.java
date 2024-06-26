package site.allawbackend.chat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.allawbackend.chat.service.ChatService;

import java.util.Map;

@RestController
@RequestMapping("/api/gpt")
@RequiredArgsConstructor
public class ChatGptController {
    private final ChatService chatService;

    @PostMapping("/query")
    public String query(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        return chatService.chat(prompt);
    }

    @PostMapping("/{billId}/summary")
    public String summary(
    @PathVariable Long billId){
        return chatService.summary(billId);
    }

    @PostMapping("/{billId}/chat")
    public String chatWithBillContext(@RequestBody Map<String, String> request,
                          @PathVariable Long billId){
        String prompt = request.get("prompt");
        return chatService.chatBasedOnBill(prompt, billId);
    }

    @PostMapping("/agree")
    public String agree(@RequestBody String prompt, String pledge){
        return chatService.agree(prompt, pledge);
    }

    @PostMapping("/disagree")
    public String disagree(@RequestBody String prompt, String pledge){
        return chatService.disagree(prompt, pledge);
    }
}
