package it.camp.schedule.services;

import it.camp.schedule.model.Day;
import it.camp.schedule.model.User;

import java.util.List;

public interface IUserService {
    public List<User> findAllUsers();
}
