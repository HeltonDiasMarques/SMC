package br.com.fourcamp.smc.SMC.controller;

import br.com.fourcamp.smc.SMC.exceptions.CustomException;
import br.com.fourcamp.smc.SMC.exceptions.UserNotFoundException;
import br.com.fourcamp.smc.SMC.model.User;
import br.com.fourcamp.smc.SMC.usecase.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public abstract class UserController<U extends User> {

    protected final UserService<U> userService;

    @Autowired
    public UserController(UserService<U> userService) {
        this.userService = userService;
    }

    protected ResponseEntity<?> createUser(@RequestBody U user) {
        try {
            userService.saveUser(user, (Class<U>) user.getClass());
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    protected ResponseEntity<?> updateUser(@RequestBody U user) {
        try {
            userService.updateUser(user, (Class<U>) user.getClass());
            return ResponseEntity.ok(user);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    protected ResponseEntity<?> getUserById(String id, Class<U> clazz) {
        try {
            Optional<U> user = userService.findUserById(id, clazz);
            return user.map(ResponseEntity::ok).orElseThrow(UserNotFoundException::new);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    protected ResponseEntity<?> getAllUsers(Class<U> clazz) {
        try {
            List<U> users = userService.findAllUsers(clazz);
            return ResponseEntity.ok(users);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
}