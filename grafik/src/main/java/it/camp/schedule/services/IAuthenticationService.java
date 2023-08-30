package it.camp.schedule.services;

import it.camp.schedule.exceptions.LoginAlreadyExistsException;
import it.camp.schedule.model.User;

public interface IAuthenticationService {
    void authenticate(String login, String password);
    void logout();
    void register(User user) throws LoginAlreadyExistsException;
}
