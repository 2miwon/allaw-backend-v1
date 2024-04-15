package site.allawbackend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.allawbackend.config.auth.dto.SessionUser;
import site.allawbackend.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final HttpSession httpSession;

    @GetMapping("/isLogin")
    public boolean isLogin() {
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            return true;
        }
        return false;
    }

    @GetMapping("/name")
    public String name() {
        try {
            SessionUser user = (SessionUser) httpSession.getAttribute("user");
            if (user != null) {
                return user.getName();
            } else {
                return "User not found";
            }
        } catch (Exception e) {
            return "name Error occurred";
        }
    }
}
