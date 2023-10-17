package it.camp.schedule.services;

import it.camp.schedule.model.Day;
import it.camp.schedule.model.User;

import java.util.List;

public interface IDayService {
    public List<Day> findByMonth(int month);
    public boolean checkIfFilled(int month);
    public void acceptDuties(int month);
    public List<Day> findTwoClosestMonths(int month);
    public int lastDayFilled(int month);
    public void calculate(int month);
    public void saveDay(Day day);
    public List<Day> findAprvDutiesByUser(int id, User user1, User user2);
    public List<Day> findNotApproved(int month);
    public boolean isLastMonthApproved(final int month);
}
