package it.camp.schedule.services.impl;

import it.camp.schedule.database.UserDAO;
import it.camp.schedule.exceptions.LoginAlreadyExistsException;
import it.camp.schedule.model.User;
import it.camp.schedule.services.IAuthenticationService;
import it.camp.schedule.session.SessionData;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthenticationService implements IAuthenticationService {
    @Autowired
    UserDAO userDAO;
    @Autowired
    SessionData sessionData;
    @Override
    public void authenticate(String login, String password) {
        Optional<User> userBox = this.userDAO.findByLogin(login);
        if(userBox.isPresent() && userBox.get().getPassword().equals(DigestUtils.md5Hex(password))) {
            userBox.get().setPassword(null);
            this.sessionData.setUser(userBox.get());
        }
    }

    @Override
    public void logout() {
        this.sessionData.setUser(null);
    }

    @Override
    public void register(User user) throws LoginAlreadyExistsException {
        if (this.userDAO.findByLogin(user.getLogin()).isPresent()) {
            throw new LoginAlreadyExistsException();
        }
        user.setPassword(DigestUtils.md5Hex((user.getPassword())));
        user.setRole(User.Role.USER);
        this.userDAO.save(user);
    }
}
