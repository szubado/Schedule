package it.camp.schedule.database;

import it.camp.schedule.model.Day;
import it.camp.schedule.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface DayDAO extends CrudRepository<Day,Integer> {

    Integer countByDate(LocalDate date);
    Integer countByUser1OrUser2(User user1, User user2);
    List<Day> findByUser1OrUser2(User user1, User user2);

}
