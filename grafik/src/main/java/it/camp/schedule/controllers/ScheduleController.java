package it.camp.schedule.controllers;

import it.camp.schedule.database.DayDAO;
import it.camp.schedule.model.Day;
import it.camp.schedule.model.DayOff;
import it.camp.schedule.model.User;
import it.camp.schedule.services.IDayService;
import it.camp.schedule.services.impl.UserService;
import it.camp.schedule.session.SessionData;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.exceptions.TemplateProcessingException;

import java.util.Optional;


@Controller
public class ScheduleController {
    @Resource
    SessionData sessionData;
    @Autowired
    IDayService dayService;
    @Autowired
    DayDAO dayDAO;
    @Autowired
    UserService userService;

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
        //blokada na guzikach w html
        if (!this.sessionData.isAdmin()) {
            return "redirect:/main";
        }
        // jesli nie approved dyzury to redrect na approve
        //jesli poprzedni mies nie jest approved to return schedule
        this.dayService.calculate(Integer.parseInt(month.substring(month.length() -2)));
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        return "schedule";
    }

    @RequestMapping(path = "/schedule/read", method = RequestMethod.GET)
    public String read(Model model) {
        if (!this.sessionData.isLogged()) {
            return "redirect:/main";
        }
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        return "read-schedule";
    }

    @RequestMapping(path = "/schedule/read", method = RequestMethod.POST)
    public String show(@RequestParam String month, Model model) {
        if (!this.sessionData.isLogged()) {
            return "redirect:/main";
        }
            model.addAttribute("days", this.dayService.findByMonth(Integer.parseInt(month.substring(month.length() - 2))));
            ModelUtils.addCommonDataToModel(model, this.sessionData);
        return "read-schedule";
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
        Optional<Day> dayBox = this.dayDAO.findById(day.getDate().getDayOfYear());
        if (!this.sessionData.isAdmin()) {
            return "redirect:/main";
        }
        dayBox.get().setUser1(day.getUser1());
        dayBox.get().setUser2(day.getUser2());
        this.dayService.saveDay(dayBox.get());
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        model.addAttribute("users",this.userService.findAllUsers());
        return "edit-schedule";
    }
}
