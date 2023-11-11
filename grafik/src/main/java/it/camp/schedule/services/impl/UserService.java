package it.camp.schedule.services.impl;

import it.camp.schedule.database.UserDAO;
import it.camp.schedule.exceptions.LoginAlreadyExistsException;
import it.camp.schedule.model.User;
import it.camp.schedule.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    UserDAO userDAO;

    @Override
    public List<User> findAllUsers() {
        return (List<User>) this.userDAO.findAll();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return this.userDAO.findByLogin(login);
    }

    @Override
    public void save(User user) throws LoginAlreadyExistsException {
        if (this.findByLogin(user.getLogin()).isPresent()) {
            throw new LoginAlreadyExistsException();
        }
        this.userDAO.save(user);
    }
}
