package it.camp.schedule.controllers;

import it.camp.schedule.database.DayOffDAO;
import it.camp.schedule.model.DayOff;
import it.camp.schedule.services.IDayOffService;
import it.camp.schedule.session.SessionData;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Controller
public class ProfileController {
    @Resource
    SessionData sessionData;
    @Autowired
    DayOffDAO dayOffDAO;
    @Autowired
    IDayOffService dayOffService;

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
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        model.addAttribute("dayOff", new DayOff());
        dayOff.setUser(sessionData.getUser());
        dayOff.setDayOfYear(dayOff.getDate().getDayOfYear());
        this.dayOffService.saveDayOff(dayOff);
        return "dayOff";
    }

    @RequestMapping(path = "/accept", method = RequestMethod.GET)
    public String acceptDayOff(Model model) {
        if (!this.sessionData.isAdmin()) {
            return "redirect:/main";
        }
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        model.addAttribute("toAccept",this.dayOffService.toAccept());
        return "accept";
    }

    @RequestMapping(path = "/accept/discard/{id}", method = RequestMethod.GET)
    public String discardDayOff(@PathVariable int id) {
        if(!this.sessionData.isAdmin()) {
            return "redirect:/main";
        }
        this.dayOffService.discardDayOff(id);
        return "redirect:/accept";
    }

    @RequestMapping(path = "/accept/approve/{id}", method = RequestMethod.GET)
    public String approveDayOff(@PathVariable int id) {
        Optional<DayOff> dayOffBox = this.dayOffDAO.findById(id);
        if(!this.sessionData.isAdmin() || dayOffBox.isEmpty()) {
            return "redirect:/main";
        }
        dayOffBox.get().setApproved(true);
        this.dayOffService.saveDayOff(dayOffBox.get());
        return "redirect:/accept";
    }
}
