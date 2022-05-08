package de.th_luebeck.swt2praktikum.controllers.Login;

import de.th_luebeck.swt2praktikum.entities.User;
import de.th_luebeck.swt2praktikum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
@Controller
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    /**
     * @param model connection to user input class
     * @return login.html page
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginInput", new LoginInput());
        return "login";
    }

    /**
     * @author Baraa Hejazi
     * @return login.html page
     */
    @GetMapping("/")
    public String forwardingToLogin() {
        return "redirect:/login";
    }

    /**
     * @param loginInput user input from Login page
     * @param model connection to user input class
     * @return login page or following page
     */
    @PostMapping(value = "/login", params = "submit")
    public String login(@ModelAttribute("loginInput")LoginInput loginInput, Model model, HttpServletRequest request) {
        final User userFound = userRepository.findByUserName(loginInput.getUserName());
        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String result = "redirect:/dashboard";
        if (userFound == null || !encoder.matches(loginInput.getUserPassword(), userFound.getPassword())) {
            System.out.println(userFound);
            model.addAttribute("errorMsg", "Invalid Username or Password");
            result = "login";
        }
        else if (userFound != null && encoder.matches(loginInput.getUserPassword(), userFound.getPassword()) ){
            System.out.println(userFound);
            request.getSession().setAttribute("USER_ID", userFound.getId());
        return dashboard();
        }
        System.out.println(userFound);
            return "login";

    }

    /**
     * A method to redirect the user to the registration.html
     * @return registration page
     */
    @PostMapping(value = "/login", params = "registration")
    public String goToRegistration() {
        return "redirect:/registration";
    }

    @GetMapping(value ="/kontoansicht")
    public String dashboard() {
        return "kontoansicht";
    }

}