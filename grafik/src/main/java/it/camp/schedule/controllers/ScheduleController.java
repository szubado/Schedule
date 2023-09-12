package it.camp.schedule.controllers;

import it.camp.schedule.services.IDayOffService;
import it.camp.schedule.services.IDayService;
import it.camp.schedule.session.SessionData;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ScheduleController {
    @Resource
    SessionData sessionData;
    @Autowired
    IDayService dayService;

    @RequestMapping(path = "/schedule", method = RequestMethod.GET)
    public String create(Model model) {
        if (!this.sessionData.isLogged()) {
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
/*
        System.out.println("wszystkie w mies " + this.dayService.findByMonth(Integer.parseInt(month.substring(month.length() -2))));
*/
/*
        System.out.println(" lastdayfilled " + this.dayService.lastDayFilled(Integer.parseInt(month.substring(month.length() -2))));
*/
        this.dayService.calculate(Integer.parseInt(month.substring(month.length() -2)));
        ModelUtils.addCommonDataToModel(model, this.sessionData);
        return "schedule";
    }
}
