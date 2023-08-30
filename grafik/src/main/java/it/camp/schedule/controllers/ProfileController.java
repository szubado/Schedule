package it.camp.schedule.controllers;

import it.camp.schedule.database.DayOffDAO;
import it.camp.schedule.model.DayOff;
import it.camp.schedule.session.SessionData;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller
public class ProfileController {
    @Resource
    SessionData sessionData;
    @Autowired
    DayOffDAO dayOffDAO;

    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public String login(Model model) {
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        return "profile";
    }

    @RequestMapping(path = "/dayOff", method = RequestMethod.GET)
    public String dayOff(Model model) {
        model.addAttribute("dayOff", new DayOff());
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        return "dayOff";
    }

    @RequestMapping(path = "/dayOff", method = RequestMethod.POST)
    public String addDayOff(@ModelAttribute DayOff dayOff, Model model) {
        if (!this.sessionData.isLogged()) {
            return "redirect:/main";
        }
        model.addAttribute("dayOff", new DayOff());
        dayOff.setUser(sessionData.getUser());
        dayOff.setDayOfYear(dayOff.getDate().getDayOfYear());
        this.dayOffDAO.save(dayOff);
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        return "dayOff";
    }
}
