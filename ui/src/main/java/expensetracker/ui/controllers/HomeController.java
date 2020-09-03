package expensetracker.ui.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@Controller
public class HomeController {

    HomeController() {

    }

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String showExpenses(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities
                .stream()
                .map(authority -> authority.getAuthority())
                .anyMatch(n -> n.equals("ROLE_USER"))) {
            model.addAttribute("authenticated", true);
        } else {
            model.addAttribute("authenticated", false);
        }


//        if (authentication.isAuthenticated()) {
//            model.addAttribute("authenticated", true);
//        } else {
//            model.addAttribute("authenticated", false);
//        }

        return "index";
    }

}
