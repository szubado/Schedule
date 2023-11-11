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
    DayOffService dayOffService;

    @Override
    public List<Day> findByMonth(final int month) {
        List<Day> days = (List<Day>) this.dayDAO.findAll();
        if (month == 12) {
            return days.stream().filter(d -> d.getDate().getMonthValue() == month).filter(d -> d.getId() != 0).toList();
        } else {
            return days.stream().filter(d -> d.getDate().getMonthValue() == month).toList();
        }
    }



    @Override
    public boolean checkIfFilled(int month) {
        boolean result = true;
        for (Day day : findByMonth(month)) {
            if (day.getUser1() == null || day.getUser2() == null) {
                result = false;
                break;
            }
        }
         return result;
    }

    @Override
    public void acceptDuties(int month) {
        for (Day day : findByMonth(month)) {
            day.setApproved(true);
            saveDay(day);
        }
    }

    @Override
    public List<Day> findTwoClosestMonths(int month) {
        List<Day> days = (List<Day>) this.dayDAO.findAll();
        if (month == 12) {
            return days.stream().filter(d -> d.getDate().getMonthValue() == month ||
                    d.getDate().getMonthValue() == 1).filter(d -> d.getId() > 31).toList();
        } else {
            return days.stream().filter(d -> d.getDate().getMonthValue() == month ||
                    d.getDate().getMonthValue() == month + 1).filter(d -> d.getId() != 0).toList();
        }
    }

    @Override
    public int lastDayFilled(final int month) {
        List<Day> days = new ArrayList<>();
        for (Day day : findByMonth(month)) {
            if (day.getUser1() != null) {
                days.add(day);
            }
        }
        return days.size();
    }

    public int countNumberOfDuties(int month, User user1, User user2) {
        List<Day> listOfDuties = this.findByUser1OrUser2(user1, user2).stream()
                .filter(d -> d.getDate().getMonthValue() == month).toList();
        return listOfDuties.size();
    }

    public int findNbOfDayInYear(int month, int day) {
        if (day == 0) {
            return findTwoClosestMonths(month).get(day).getId() - 1;
        } else {
            return findTwoClosestMonths(month).get(day - 1).getId();
        }
    }

    @Override
    public void calculate(int month) {
        int numberOfEmployees = (int) this.userDAO.count();
        int day = lastDayFilled(month) + 1;
        Random random = new Random();
        int numberOfDays = findByMonth(month).size();
        int maxDuties = (numberOfDays - lastDayFilled(month)) * 2 / numberOfEmployees + 1;
        List<User> users = (List<User>) this.userDAO.findAll();
        int statement = (numberOfEmployees * (((numberOfDays - lastDayFilled(month)) / numberOfEmployees) + 1));
        outerloop:
        while (day <= statement) {
            final int previousDay = findNbOfDayInYear(month, day - 1);
            List<User> availableUsers = users.stream().filter(u -> u.getId() !=
                    this.dayDAO.findById(previousDay).get().getUser1().getId() &&
                    u.getId() != this.dayDAO.findById(previousDay).get().getUser2().getId()).toList();
            if (this.dayOffService.findByDayOfYear(previousDay + 1).isPresent()) {
                availableUsers = availableUsers.stream().filter(u -> u.getId() !=
                        this.dayOffService.findByDayOfYear(previousDay + 1).get().getUser().getId()).toList();
            }
            int counter1 = 0;
            int counter2 = 0;
            innerloop:
            while (this.dayDAO.findById(previousDay + 1).get().getUser2() == null) {
                int randomUser1 = availableUsers.get(random.nextInt(availableUsers.size())).getId();
                User.Lab employee1Lab = this.userDAO.findById(randomUser1).get().getLab();
                if (countNumberOfDuties(month, new User(randomUser1), new User(randomUser1)) < maxDuties) {
                    Optional<Day> dayBox = this.dayDAO.findById(previousDay + 1);
                    dayBox.get().setUser1(new User(randomUser1, employee1Lab));
                    availableUsers = availableUsers.stream().filter(u -> !u.getLab().equals(employee1Lab)).toList();
                } else {
                    counter1++;
                    if (counter1 > numberOfEmployees * 2) {
                        break;
                    }
                    continue;
                }
                while (this.dayDAO.findById(previousDay + 1).get().getUser2() == null) {
                    int randomUser2 = availableUsers.get(random.nextInt(availableUsers.size())).getId();
                    if (countNumberOfDuties(month, new User(randomUser2), new User(randomUser2)) < maxDuties) {
                        Optional<Day> dayBox = this.dayDAO.findById(previousDay + 1);
                        dayBox.get().setUser2(new User(randomUser2, this.userDAO.findById(randomUser2).get().getLab()));
                        saveDay(dayBox.get());
                        day++;
                        break innerloop;
                    } else {
                        counter2++;
                        if (counter2 > numberOfEmployees * 2) {
                            break outerloop;
                        }
                    }
                }
            }
        }
    }

    public void saveDay(Day day) {
        this.dayDAO.save(day);
    }

    @Override
    public List<Day> findAprvDutiesByUser(int id, User user1, User user2) {
        return this.findByUser1OrUser2(user1, user2).stream().filter(Day::isApproved).toList();
    }

    @Override
    public List<Day> findNotApproved(int month) {
        return this.findByMonth(month).stream().filter(d -> !d.isApproved()).toList();
    }

    @Override
    public boolean isLastMonthApproved(int month) {
        System.out.println(this.dayDAO.findById(this.findByMonth(month).get(0).getId()));
        System.out.println(this.dayDAO.findById(this.findByMonth(month).get(0).getId() - 1).get().isApproved());
        return this.dayDAO.findById(this.findByMonth(month).get(0).getId() - 1).get().isApproved();
    }

    @Override
    public int changeToNumber(String month) {
        return Integer.parseInt(month.substring(month.length() - 2));
    }

    @Override
    public List<Day> findByUser1OrUser2(User user1, User user2) {
        return this.dayDAO.findByUser1OrUser2(user1, user2);
    }

    @Override
    public Optional<Day> findById(int id) {
        return this.dayDAO.findById(id);
    }
}
