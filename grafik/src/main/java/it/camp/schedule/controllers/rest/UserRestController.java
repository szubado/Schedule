package it.camp.schedule.controllers.rest;

import it.camp.schedule.exceptions.LoginAlreadyExistsException;
import it.camp.schedule.model.User;
import it.camp.schedule.model.dto.ListResponse;
import it.camp.schedule.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserRestController {
    @Autowired
    IUserService userService;

    @RequestMapping(path = "/{login}", method = RequestMethod.GET)
    public ResponseEntity<User> findByLogin(@PathVariable String login) {
        Optional<User> userBox = this.userService.findByLogin(login);
        return userBox.map(user -> ResponseEntity.status(OK).body(user))
                        .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public ListResponse<User> findAll() {
            return new ListResponse<>(this.userService.findAllUsers());
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        user.setId(0);
        try {
            this.userService.save(user);
        } catch (LoginAlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).build();
        }
        return ResponseEntity.status(CREATED).body(user);
    }
}
