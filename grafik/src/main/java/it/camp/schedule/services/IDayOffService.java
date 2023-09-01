package it.camp.schedule.services;

import it.camp.schedule.model.DayOff;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IDayOffService {
    public List<DayOff> toAccept();
    public void discardDayOff(int id);
    public Optional<DayOff> findById(int id);
    public void approveDayOff(DayOff dayOff);
    public void saveDayOff(DayOff dayOff);
    }
