package it.camp.schedule.services.impl;

import it.camp.schedule.database.DayDAO;
import it.camp.schedule.model.Day;
import it.camp.schedule.services.IDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DayService implements IDayService {
    @Autowired
    DayDAO dayDAO;
    @Override
    public List<Day> findByMonth(int month) {
        List<Day> list = (List<Day>) this.dayDAO.findAll();
        return list.stream().filter(d -> d.getDate().getMonthValue() == month).toList();
    }
}
