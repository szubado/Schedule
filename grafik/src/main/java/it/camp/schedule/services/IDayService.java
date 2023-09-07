package it.camp.schedule.services;

import it.camp.schedule.model.Day;

import java.util.List;

public interface IDayService {
    public List<Day> findByMonth(int month);
    public int lastDayFilled(int month);
    public void calculate(int month);
}
