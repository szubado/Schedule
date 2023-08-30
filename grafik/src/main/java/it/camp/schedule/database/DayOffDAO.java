package it.camp.schedule.database;

import it.camp.schedule.model.DayOff;
import org.springframework.data.repository.CrudRepository;

public interface DayOffDAO extends CrudRepository<DayOff,Integer> {
}
