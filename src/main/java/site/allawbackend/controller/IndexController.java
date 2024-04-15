package site.allawbackend.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import site.allawbackend.config.auth.dto.SessionUser;

@RequiredArgsConstructor
@Controller
public class IndexController {
    //private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/loginTest")
    public String index(Model model) {
        //model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }
}
