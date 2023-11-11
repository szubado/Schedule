package it.camp.schedule.services;

import it.camp.schedule.exceptions.LoginAlreadyExistsException;
import it.camp.schedule.model.Day;
import it.camp.schedule.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public List<User> findAllUsers();
    Optional<User> findByLogin(String login);
    public void save(User user) throws LoginAlreadyExistsException;
}
