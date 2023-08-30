package it.camp.schedule.database;

import it.camp.schedule.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserDAO extends CrudRepository<User, Integer> {
Optional<User> findByLogin(String login);
}
