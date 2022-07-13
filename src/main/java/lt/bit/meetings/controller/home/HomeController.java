package lt.bit.meetings.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(){
        return "home.html";
    }

    @RequestMapping("/login")
    public String loginPage(){
        return "login.html";
    }

    @RequestMapping("/logout-success")
    public String logoutPage(){
        return "logout.html";
    }
}
