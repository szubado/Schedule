package it.camp.schedule.services.impl;

import it.camp.schedule.database.DayDAO;
import it.camp.schedule.database.DayOffDAO;
import it.camp.schedule.database.UserDAO;
import it.camp.schedule.model.Day;
import it.camp.schedule.model.User;
import it.camp.schedule.services.IDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<Day> nowe = new ArrayList<>();
        for (Day day : findByMonth(month)) {
            if (day.getUser1() != null) {
                nowe.add(day);
            }
        }
        return nowe.size();
/*        Optional<Day> toFill = findByMonth(month).stream().filter(d -> d.getUser1() != null)
                .max(Comparator.comparingInt(d -> d.getUser1().getId()));
        return toFill.map(Day::getId).orElse(0);*/
    }

    public int countNumberOfDuties(int month, User user1, User user2) {
        List<Day> listOfDuties = this.dayDAO.findByUser1OrUser2(user1, user2).stream()
                .filter(d -> d.getDate().getMonthValue() == month).toList();
        return listOfDuties.size();
    }

    public int findNbOfDayInYear(int month, int day) {
        return findByMonth(month).get(day - 1).getId();
    }
    /* @Override
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
                         }
                     }
                 }
             }
         }
     }*/
    @Override
    public void calculate(int month) {
        int numberOfEmployees = (int) this.userDAO.count();
        int day = lastDayFilled(month) + 1;
        Random random = new Random();
        int numberOfDays = findByMonth(month).size();
        int maxDuties = (numberOfDays - lastDayFilled(month)) * 2 / numberOfEmployees + 1;
        List<User> users = (List<User>) this.userDAO.findAll();
        int statement = (numberOfEmployees * (((numberOfDays - lastDayFilled(month)) / numberOfEmployees) + 1));
        // lub -> przpadek grudniowy do kiedy ma sie krecic petla
        while (day <= statement) {
            int previousDay = day - 1;
            List<User> availableUsers = users.stream().filter(u -> u.getId() !=
                    this.dayDAO.findById(previousDay).get().getUser1().getId() &&
                    u.getId() != this.dayDAO.findById(previousDay).get().getUser2().getId()).toList();
            //opis ifow co robia
            if (this.dayOffDAO.findByDayOfYear(previousDay + 1).isPresent()) {
                availableUsers = availableUsers.stream().filter(u -> u.getId() !=
                        this.dayOffDAO.findByDayOfYear(previousDay + 1).get().getUser().getId()).toList();
            }
            int counter = 0;
            outerloop:
            while (this.dayDAO.findById(day).get().getUser2() == null) {
                int randomUser1 = availableUsers.get(random.nextInt(availableUsers.size())).getId();
                User.Lab employee1Lab = this.userDAO.findById(randomUser1).get().getLab();
                if (countNumberOfDuties(month, new User(randomUser1), new User(randomUser1)) < maxDuties) {
                    Optional<Day> dayBox = this.dayDAO.findById(day);
                    dayBox.get().setUser1(new User(randomUser1, employee1Lab));
                    availableUsers = availableUsers.stream().filter(u -> !u.getLab().equals(employee1Lab)).toList();
                } else {
                    //opis wyjscia
                    //couter ile razy weszlismy tutaj jesli przekroczy 2xrozmiar tablicy users (wyselekcjonowanej)
                    counter++;
                    if (counter > numberOfEmployees * 2) {
                        break;
                    }
                    continue;
                }
                while (this.dayDAO.findById(day).get().getUser2() == null) {
                    int randomUser2 = availableUsers.get(random.nextInt(availableUsers.size())).getId();
                    if (countNumberOfDuties(month, new User(randomUser2), new User(randomUser2)) < maxDuties) {
                        Optional<Day> dayBox = this.dayDAO.findById(day);
                        dayBox.get().setUser2(new User(randomUser2, this.userDAO.findById(randomUser2).get().getLab()));
                        saveDay(dayBox.get());
                        day++;
                        break outerloop;
                    }
                }
            }
        }
    }

    public void saveDay(Day day) {
        this.dayDAO.save(day);
    }
}
