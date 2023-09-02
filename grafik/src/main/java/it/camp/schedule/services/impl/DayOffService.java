package it.camp.schedule.services.impl;

import it.camp.schedule.database.DayOffDAO;
import it.camp.schedule.model.DayOff;
import it.camp.schedule.services.IDayOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class DayOffService implements IDayOffService {
    @Autowired
    DayOffDAO dayOffDAO;

    @Override
    public List<DayOff> toAccept() {
        return dayOffDAO.findByApproved(false);
    }

    @Override
    public void discardDayOff(int id) {
        this.dayOffDAO.deleteById(id);
    }
    public Optional<DayOff> findById(int id) {
        return this.dayOffDAO.findById(id);
    }

    @Override
    public void approveDayOff(DayOff dayOff) {
/*
        this.dayOffDAO.findById(id)
*/
    }

    @Override
    public void saveDayOff(DayOff dayOff) {
        this.dayOffDAO.save(dayOff);
    }

}
