package it.camp.schedule.services.impl;

import it.camp.schedule.database.DayDAO;
import it.camp.schedule.database.DayOffDAO;
import it.camp.schedule.database.UserDAO;
import it.camp.schedule.model.Day;
import it.camp.schedule.model.DayOff;
import it.camp.schedule.model.User;
import it.camp.schedule.services.IDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;

@Service
public class DayService implements IDayService {
    @Autowired
    DayDAO dayDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    DayOffDAO dayOffDAO;

    @Override
    public List<Day> findByMonth(final int month) {
/*        int newMonth = Integer.parseInt(month.substring(month.length() - 2));*/
        List<Day> days = (List<Day>) this.dayDAO.findAll();
        return days.stream().filter(d -> d.getDate().getMonthValue() == month).toList();
    }

    @Override
    public int lastDayFilled(final int month) {
/*        List<Day> nowe = new ArrayList<>();
        for (Day day : findByMonth(month)) {
            if(day.getUser1() != null) {
                nowe.add(day);
            }
        }
        return nowe.size();*/
        Optional<Day> toFill = findByMonth(month).stream().filter(d -> d.getUser1() != null)
                .max(Comparator.comparingInt(d -> d.getUser1().getId()));
        return toFill.map(Day::getId).orElse(0);
    }

    @Override
    public void calculate(int month) {
        int numberOfEmployees = (int) this.userDAO.count();
        int day = lastDayFilled(month) + 1;
        Random random = new Random();
        int statement = (numberOfEmployees * (((findByMonth(month).size() - lastDayFilled(month)) / numberOfEmployees) + 1));
        // lub -> przpadek grudniowy do kiedy ma sie krecic petla
        while (day <= statement) {
            int randomEmployeeId = random.nextInt(1, numberOfEmployees + 1);
            User.Lab employeeLab = this.userDAO.findById(randomEmployeeId).get().getLab();
            if (this.dayDAO.findById(day - 1).get().getUser1().getId() !=
                    this.userDAO.findById(randomEmployeeId).get().getId() &&
                    this.dayDAO.findById(day - 1).get().getUser2().getId() !=
                            this.userDAO.findById(randomEmployeeId).get().getId()) {
                if (this.dayOffDAO.findByDayOfYearAndUser(day, new User(randomEmployeeId)).isEmpty()) {
                    if (this.dayDAO.findById(day).get().getUser1() == null) {
                        Optional<Day> dayBox = this.dayDAO.findById(day);
                        dayBox.get().setUser1(new User (randomEmployeeId, employeeLab));
                        saveDay(dayBox.get());
                    } else {
                        if (!this.dayDAO.findById(day).get().getUser1().getLab().equals(employeeLab)) {
                            Optional<Day> dayBox = this.dayDAO.findById(day);
                            dayBox.get().setUser2(new User (randomEmployeeId, employeeLab));
                            saveDay(dayBox.get());
                            day++;
                        } continue;
                    }
                } continue;
            } continue;
        }
    }

    public void saveDay(Day day) {
        this.dayDAO.save(day);
    }
}
