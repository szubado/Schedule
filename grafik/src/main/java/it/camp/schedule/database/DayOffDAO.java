package it.camp.schedule.database;

import it.camp.schedule.model.DayOff;
import it.camp.schedule.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DayOffDAO extends CrudRepository<DayOff,Integer> {
    List<DayOff> findByApproved(boolean approved);

}
