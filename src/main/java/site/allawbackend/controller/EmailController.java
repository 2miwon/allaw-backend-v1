package site.allawbackend.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.allawbackend.dto.EmailDto;
import site.allawbackend.entity.Email;
import site.allawbackend.service.EmailService;
import site.allawbackend.service.UserService;

import java.util.List;
@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailController{

    private final EmailService service;
    private final UserService userService;
    //private final LoggerUtil loggerUtil;

    @GetMapping("/")
    public List<Email> getEmails() {
        return (List<Email>) service.findAll();
    }

    @PostMapping("/send")
    public String sendEmailByKeyword(
            @RequestBody @NotNull EmailDto emailForm){
        try{
            service.sendEmailByKeyword(
                    emailForm.getKeyword(),
                    emailForm.getSubject(),
                    emailForm.getMessageBody());
        }catch (Exception e) {
            return "이메일 전송에 실패하였습니다. 오류: " + e.getMessage();
        }
        return "이메일 전송에 성공하였습니다!";
    }


//    @PostMapping("/send")
//    @PreAuthorize("isAuthenticated()")
//    public String sendEmail(
//            @RequestBody @NotNull EmailDto emailForm) {
//        //String senderName = loggerUtil.getCurrentUsername();
//        try{
////            service.sendEmail(
////                    emailForm.getReceiver(),
////                    emailForm.getSubject(),
////                    emailForm.getMessageBody());
//            //loggerUtil.log(TransactionType.USER_TO_USER_EMAIL, senderName, emailForm.getReceiver());
//        }catch (Exception e) {
//            return "이메일 전송에 실패하였습니다. 오류: " + e.getMessage();
//        }
//        return "이메일 전송에 성공하였습니다!";
//    }
    /**
    @GetMapping("/Outbox")
    public List<Email> viewOutbox() {
        String currentUsername = l til.getCurrentUsername();
        return service.findBySender(currentUsername);
    }

    @GetMapping("/Inbox")
    public List<Email> viewInbox() {
        String currentUsername = loggerUtil.getCurrentUsername();
        User currentUser = userService.findByName(currentUsername);
        return service.findByReceiver(currentUser);
    }**/
}