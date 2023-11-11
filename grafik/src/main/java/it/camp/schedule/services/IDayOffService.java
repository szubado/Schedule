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
    List<DayOff> findApprovedByUser(User user);
    List<DayOff> findNotApprovedByUser(User user);
    public List<DayOff> areDaysOffApproved(int month);
    List<DayOff> findByApproved(boolean approved);
    Optional<DayOff> findByDayOfYear(int dayOfYear);
    List<DayOff> findByUser(User user);

}

