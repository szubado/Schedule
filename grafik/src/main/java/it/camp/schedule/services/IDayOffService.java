package it.camp.schedule.services;

import it.camp.schedule.model.DayOff;
import it.camp.schedule.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IDayOffService {
    public List<DayOff> toAccept();
    public void discardDayOff(int id);
    public Optional<DayOff> findById(int id);
    public void saveDayOff(DayOff dayOff);
    public List<DayOff> findApprovedByUser(User user);
    public List<DayOff> findNotApprovedByUser(User user);
    public List<DayOff> areDaysOffApproved(int month);
    public List<DayOff> findByApproved(boolean approved);
    public Optional<DayOff> findByDayOfYear(int dayOfYear);
    public List<DayOff> findByUser(User user);

}

