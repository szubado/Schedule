package it.camp.schedule.services;

import it.camp.schedule.model.Day;

import java.util.List;

public interface IDayService {
    public List<Day> findByMonth(String month);
    public int lastDayFilled(String month);
    public void calculate(String month);
}
