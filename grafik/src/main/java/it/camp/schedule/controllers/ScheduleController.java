package it.camp.schedule.controllers;

import it.camp.schedule.exceptions.LabValidationException;
import it.camp.schedule.model.Day;
import it.camp.schedule.services.IDayOffService;
import it.camp.schedule.services.IDayService;
import it.camp.schedule.services.impl.UserService;
import it.camp.schedule.session.SessionData;
import it.camp.schedule.validators.UserValidator;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
public class ScheduleController {
    @Resource
    SessionData sessionData;
    @Autowired
    IDayService dayService;
    @Autowired
    UserService userService;
    @Autowired
    IDayOffService dayOffService;

    @RequestMapping(path = "/schedule", method = RequestMethod.GET)
    public String create(Model model) {
        if (!this.sessionData.isAdmin()) {
            return "redirect:/main";
        }
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        return "schedule";
    }

    @RequestMapping(path = "/schedule", method = RequestMethod.POST)
    public String calculate(@RequestParam String month, Model model) {
        if (!this.sessionData.isAdmin()) {
            return "redirect:/main";
        }
        if (!this.dayOffService.areDaysOffApproved(dayService.changeToNumber(month)).isEmpty()) {
            return "redirect:/accept";
        }
        if (!this.dayService.isLastMonthApproved(dayService.changeToNumber(month))) {
            return "redirect:/schedule/read";
        }
        this.dayService.calculate(dayService.changeToNumber(month));
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        return "schedule";
    }

    @RequestMapping(path = "/schedule/read", method = RequestMethod.GET)
    public String read(Model model, @RequestParam(required = false) String month) {
        if (!this.sessionData.isLogged()) {
            return "redirect:/main";
        }
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        if (month != null) {
            model.addAttribute("days", this.dayService.findByMonth(dayService.changeToNumber(month)));
            return "redirect:/schedule/read/"+month;
        }
        else {
            return "read-schedule";
        }
    }

    @RequestMapping(path = "/schedule/read/{month}", method = RequestMethod.GET)
    public String readMonth(Model model, @PathVariable String month) {
        if (!this.sessionData.isLogged()) {
            return "redirect:/main";
        }
        if (month != null) {
            model.addAttribute("days",
                    this.dayService.findByMonth(dayService.changeToNumber(month)));
        }
        model.addAttribute("notApproved",
                this.dayService.findNotApproved(dayService.changeToNumber(month)));
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        return "read-schedule2";
    }

    @RequestMapping(path = "/schedule/edit", method = RequestMethod.GET)
    public String edit(Model model) {
        if (!this.sessionData.isAdmin()) {
            return "redirect:/main";
        }
        model.addAttribute("day", new Day());
        model.addAttribute("users",this.userService.findAllUsers());
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        return "edit-schedule";
    }

    @RequestMapping(path = "/schedule/edit", method = RequestMethod.POST)
    public String editDay(@ModelAttribute Day day, Model model) {
        Optional<Day> dayBox = this.dayService.findById(day.getDate().getDayOfYear());
        if (!this.sessionData.isAdmin()) {
            return "redirect:/main";
        }
        System.out.println(day.getUser1());
        System.out.println(day.getUser2());
        if (day.getUser1() == null || day.getUser2() == null) {
            return "redirect:/schedule/edit";
        }
        try {
            UserValidator.validateLabEquality(day.getUser1().getLab(), day.getUser2().getLab());
            dayBox.get().setUser1(day.getUser1());
            dayBox.get().setUser2(day.getUser2());
            dayBox.get().setApproved(false);
            this.dayService.saveDay(dayBox.get());
        } catch (LabValidationException e) {
            return "redirect:/schedule/edit";
        }
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        model.addAttribute("users",this.userService.findAllUsers());
        return "redirect:/schedule/edit";
    }

    @RequestMapping(path = "/schedule/approve/{month}", method = RequestMethod.GET)
    public String acceptDuties(@PathVariable String month) {
        if(!this.sessionData.isAdmin()) {
            return "redirect:/main";
        }
        if (!this.dayService.checkIfFilled(dayService.changeToNumber(month))) {
            return "redirect:/schedule/edit";
        }
        this.dayService.acceptDuties(dayService.changeToNumber(month));
        return "redirect:/schedule/read";
    }
}
