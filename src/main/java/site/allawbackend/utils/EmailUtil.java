package site.allawbackend.utils;

import org.springframework.stereotype.Component;
import site.allawbackend.entity.Email;
import site.allawbackend.entity.User;
import site.allawbackend.service.EmailService;

@Component
public class EmailUtil {

    private final EmailService service;

    public EmailUtil(EmailService service) {
        this.service = service;
    }

//    public void sendEmail(final User receiver, final String subject, final String messageBody) {
//        sendEmail("Allaw System", receiver, subject, messageBody);
//    }

    public void sendEmail(
            final User receiver,
            final String subject,
            final String messageBody) {
        final Email email = new Email(receiver, subject, messageBody);
        service.save(email);
    }
}