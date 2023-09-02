package it.camp.schedule.services;

import it.camp.schedule.model.Day;

import java.util.List;

public interface IDayService {
    public List<Day> findByMonth(int month);
}
