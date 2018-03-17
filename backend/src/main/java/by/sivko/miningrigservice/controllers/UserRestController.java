package by.sivko.miningrigservice.controllers;

import by.sivko.miningrigservice.controllers.exceptions.AlreadyExistsException;
import by.sivko.miningrigservice.controllers.exceptions.NotExistException;
import by.sivko.miningrigservice.controllers.exceptions.PasswordException;
import by.sivko.miningrigservice.dto.UserDto;
import by.sivko.miningrigservice.models.user.User;
import by.sivko.miningrigservice.services.user.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserRestController {

    public static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    private static final int MIN_PASSWORD_LENGTH = 5;

    private UserService userService;

    @CrossOrigin
    @RequestMapping("/account/login")
    public String user(Principal principal) {
        return principal.getName();
    }

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@Valid UserDto userDto) {
        User user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
        User userExistsLogin = this.userService.findUserByUsername(user.getUsername());
        if (userExistsLogin != null) {
            throw new AlreadyExistsException("A user with this name already exists");
        }
        User userExistsEmail = this.userService.findUserByUsername(user.getEmail());
        if (userExistsEmail != null) {
            throw new AlreadyExistsException("A user with this email already exists");
        }
        this.userService.saveUser(user);
        return new ResponseEntity<>("User has been registered successfully", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseEntity<Void> changeUserPassword(Principal principal, String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            throw new PasswordException("Password must have al least 5 characters");
        }
        User user = this.userService.findUserByUsername(principal.getName());
        this.userService.changeUserPassword(user, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = this.userService.getAllUsers();
        if (userList.isEmpty()) {
            throw new NotExistException("Don't exist any user");
        } else {
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = this.userService.findUserByUsername(username);
        if (user == null) {
            throw new NotExistException(String.format("User with name [%s] not exists", username));
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

}
