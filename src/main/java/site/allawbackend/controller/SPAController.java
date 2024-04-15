package site.allawbackend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SPAController {
    @RequestMapping("/userinfo")
    public String userInfo() {
        return "forward:/";
    }

    @RequestMapping("/DetailPage/{bill_no}")
    public String bill() {
        return "forward:/";
    }

    @RequestMapping("/searchResult/**")
    public String searchResult() {
        return "forward:/";
    }

    @RequestMapping("/election/**")
    public String election() {
        return "forward:/";
    }

    // @RequestMapping("/file/**")
    // public String file() {
    // return "forward:/";
    // }

    // @RequestMapping("/elastic/**")
    // public String elastic() {
    // return "forward:/";
    // }
}
