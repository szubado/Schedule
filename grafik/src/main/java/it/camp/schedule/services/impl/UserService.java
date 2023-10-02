package it.camp.schedule.services.impl;

import it.camp.schedule.database.UserDAO;
import it.camp.schedule.model.User;
import it.camp.schedule.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    UserDAO userDAO;

    @Override
    public List<User> findAllUsers() {
        return (List<User>) this.userDAO.findAll();
    }
}
