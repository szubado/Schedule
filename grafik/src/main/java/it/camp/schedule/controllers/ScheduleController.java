package it.camp.schedule.controllers;

import it.camp.schedule.session.SessionData;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ScheduleController {
    @Resource
    SessionData sessionData;

    @RequestMapping(path = "/schedule", method = RequestMethod.GET)
    public String create(Model model) {
        if (!this.sessionData.isLogged()) {
            return "redirect:/main";
        }
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        return "schedule";
    }
}
