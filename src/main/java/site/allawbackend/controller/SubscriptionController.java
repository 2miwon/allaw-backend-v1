package site.allawbackend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import site.allawbackend.common.auth.dto.SessionUser;
import site.allawbackend.dto.KeywordDto;
import site.allawbackend.service.SubscriptionService;

import java.util.List;

@RestController
@Controller
public class SubscriptionController {

    private final HttpSession httpSession;
    private final SubscriptionService subscriptionService; // 구독 서비스

    public SubscriptionController(HttpSession httpSession, SubscriptionService subscriptionService) {
        this.httpSession = httpSession;
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody KeywordDto keywordDto) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>("사용자가 로그인하지 않았습니다.", HttpStatus.UNAUTHORIZED);
        }
        KeywordDto result = subscriptionService.subscribe(user.getEmail(), keywordDto.getValue());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/unsubscribe/{keywordId}")
    public ResponseEntity<?> unsubscribe(@PathVariable Long keywordId) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>("사용자가 로그인하지 않았습니다.", HttpStatus.UNAUTHORIZED);
        }
        String result = subscriptionService.unsubscribe(user.getEmail(), keywordId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/subscriptions")
    public ResponseEntity<?> subscriptions() {

        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>("사용자가 로그인하지 않았습니다.", HttpStatus.UNAUTHORIZED);
        }
        List<KeywordDto> keywordDtos = subscriptionService.subscriptions(user.getEmail());

        return new ResponseEntity<>(keywordDtos, HttpStatus.OK);
    }
}
