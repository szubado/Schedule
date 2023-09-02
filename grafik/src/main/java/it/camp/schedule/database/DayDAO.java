package it.camp.schedule.database;

import it.camp.schedule.model.Day;
import it.camp.schedule.model.DayOff;
import org.springframework.data.repository.CrudRepository;

public interface DayDAO extends CrudRepository<Day,Integer> {
}
