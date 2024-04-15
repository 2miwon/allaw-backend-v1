package site.allawbackend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.allawbackend.config.auth.dto.SessionUser;
import site.allawbackend.dto.OpinionDto;
import site.allawbackend.service.OpinionService;

import java.util.List;

@RestController
public class OpinionController {

    private final HttpSession httpSession;
    private final OpinionService opinionService;

    public OpinionController(HttpSession httpSession, OpinionService opinionService) {
        this.httpSession = httpSession;
        this.opinionService = opinionService;
    }

    @PostMapping("/makeOpinion")
    public ResponseEntity<?> makeOpinion(@RequestBody OpinionDto opinionDto) {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user == null) {
            return new ResponseEntity<>("사용자가 로그인하지 않았습니다.", HttpStatus.UNAUTHORIZED);
        }

        opinionService.makeOpinion(user.getEmail(), opinionDto.getBillsNo(), opinionDto.getDetail(), opinionDto.getGrade());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/opinions")
    public ResponseEntity<?> opinions(@RequestParam int billsNo) {
        List<OpinionDto> opinionDtos = opinionService.opinions(billsNo);
        return new ResponseEntity<>(opinionDtos, HttpStatus.OK);
    }
}
