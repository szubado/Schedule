package it.camp.schedule.controllers;

import it.camp.schedule.exceptions.LoginAlreadyExistsException;
import it.camp.schedule.exceptions.UserValidationException;
import it.camp.schedule.model.User;
import it.camp.schedule.services.impl.AuthenticationService;
import it.camp.schedule.session.SessionData;
import it.camp.schedule.validators.UserValidator;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;

@Controller
public class AuthenticationController {
@Autowired
    AuthenticationService authenticationService;
    @Resource
    SessionData sessionData;

    @RequestMapping(path = "/main", method = RequestMethod.GET)
        public String main(Model model) {
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        return "index";
    }

    @RequestMapping(path = "/main", method = RequestMethod.POST)
    public String login(@RequestParam String login,
                        @RequestParam String password) {
        try {
            UserValidator.validateLogin(login);
            UserValidator.validatePassword(password);
            this.authenticationService.authenticate(login, password);
            if (sessionData.isLogged()) {
                return "redirect:/profile";
            }
        } catch (UserValidationException e) {
        }
        return "redirect:/main";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout() {
        this.authenticationService.logout();
        return "redirect:/main";
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        model.addAttribute("userModel", new User());
        return "register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute User user,
                           @RequestParam String password2) {
        try {
            UserValidator.validateUser(user);
            UserValidator.validatePasswordsEquality(user.getPassword(), password2);
            this.authenticationService.register(user);
        } catch (LoginAlreadyExistsException | UserValidationException e) {
            return "redirect:/register";
        }
        return "redirect:/main";
    }
}
