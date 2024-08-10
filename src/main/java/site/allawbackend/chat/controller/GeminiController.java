package site.allawbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import site.allawbackend.chat.service.GeminiService;
import site.allawbackend.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    @PostMapping("/chat")
    public ResponseEntity<?> gemini(@RequestBody String prompt) {
        try {
            String response = geminiService.chat(prompt);
            return ResponseEntity.ok().body(response);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred");
        }
    }


    @PostMapping("/{billId}/chat")
    public String chatWithBillContext(@RequestBody Map<String, String> request,
                                      @PathVariable Long billId){
        String prompt = request.get("prompt");
        return geminiService.chatBasedOnBill(prompt, billId);
    }
}
