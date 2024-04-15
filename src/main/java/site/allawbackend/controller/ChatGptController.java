package site.allawbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.allawbackend.service.ChatGptService;

@RestController
@RequestMapping("/gpt")
@RequiredArgsConstructor
public class ChatGptController {
    private final ChatGptService chatGptService;

    @PostMapping("/gen")
    public String chat(@RequestBody String prompt) {
        return chatGptService.chat(prompt);
    }

    @PostMapping("/{billsNum}/summary")
    public String summary(
    @PathVariable final Integer billsNum){
        return chatGptService.summary(billsNum);
    }

    @PostMapping("/{billsNum}/pdfChat")
    public String pdfChat(@RequestBody String prompt,
                          @PathVariable final Integer billsNum){
        return chatGptService.checkChat(prompt,billsNum);
    }

    @PostMapping("/agree")
    public String agree(@RequestBody String prompt, String pdf){
        return chatGptService.agree(prompt, pdf);
    }

    @PostMapping("/disagree")
    public String disagree(@RequestBody String prompt, String pdf){
        return chatGptService.disagree(prompt, pdf);
    }
}
