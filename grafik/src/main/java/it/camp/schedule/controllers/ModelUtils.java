package it.camp.schedule.controllers;

import it.camp.schedule.session.SessionData;
import org.springframework.ui.Model;

public class ModelUtils {

    public static void addCommonDataToModel(Model model, SessionData sessionData){
        model.addAttribute("logged", sessionData.isLogged());
        model.addAttribute("admin", sessionData.isAdmin());
    }
}
