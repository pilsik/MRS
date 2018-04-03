package by.sivko.miningrigservice.controllers;

import by.sivko.miningrigservice.controllers.exceptions.AlreadyExistsException;
import by.sivko.miningrigservice.dto.UserDto;
import by.sivko.miningrigservice.models.user.User;
import by.sivko.miningrigservice.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class AccountRestController {

    private UserService userService;

    @Autowired
    public AccountRestController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @RequestMapping("/api/account/login")
    public String user(Principal principal) {
        return principal != null ? principal.getName() : null;
    }

    @CrossOrigin
    @RequestMapping(value = "/api/account/register/checkLogin", method = RequestMethod.GET)
    public ResponseEntity<String> checkLogin(String login) {
        boolean existUsername = this.userService.findUserByUsername(login) == null;
        return existUsername ? new ResponseEntity<>("false", HttpStatus.OK) : new ResponseEntity<>("true", HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/account/register/checkEmail", method = RequestMethod.GET)
    public ResponseEntity<String> checkEmail(String email) {
        boolean existEmail = this.userService.findUserByEmail(email) == null;
        return existEmail ? new ResponseEntity<>("false", HttpStatus.OK) : new ResponseEntity<>("true", HttpStatus.OK);
    }

    @RequestMapping("/login")
    public ResponseEntity<Void> forLogout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/api/account/register", method = RequestMethod.POST, consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDto userDto) {
        User user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getEmail());
        User userExistsLogin = this.userService.findUserByUsername(user.getUsername());
        if (userExistsLogin != null) {
            throw new AlreadyExistsException("A user with this name already exists");
        }
        User userExistsEmail = this.userService.findUserByEmail(user.getEmail());
        if (userExistsEmail != null) {
            throw new AlreadyExistsException("A user with this email already exists");
        }
        this.userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
