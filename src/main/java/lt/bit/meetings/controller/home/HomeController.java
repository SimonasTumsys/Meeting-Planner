package lt.bit.meetings.controller.home;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication
                = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null ||
                authentication instanceof AnonymousAuthenticationToken){
            return "login.html";
        }
        return "redirect:/";
    }

    @RequestMapping("/logout-success")
    public String logoutPage(){
        return "logout.html";
    }
}
