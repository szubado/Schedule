package it.camp.schedule.database;

import it.camp.schedule.model.Day;
import it.camp.schedule.model.DayOff;
import it.camp.schedule.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface DayOffDAO extends CrudRepository<DayOff,Integer> {
    List<DayOff> findByApproved(boolean approved);
    List<DayOff> findByDayOfYearAndUser(int dayOfYear, User user);


}
