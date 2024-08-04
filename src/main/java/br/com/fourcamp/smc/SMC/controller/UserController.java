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

/**
 * Abstract base controller for user-related operations.
 *
 * @param <U> the type of user
 */
@RestController
public abstract class UserController<U extends User> {

    protected final UserService<U> userService;

    @Autowired
    public UserController(UserService<U> userService) {
        this.userService = userService;
    }

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return the response entity with the created user or an error message
     */
    protected ResponseEntity<?> createUser(@RequestBody U user) {
        try {
            userService.saveUser(user, (Class<U>) user.getClass());
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    /**
     * Updates an existing user.
     *
     * @param user the user to update
     * @return the response entity with the updated user or an error message
     */
    protected ResponseEntity<?> updateUser(@RequestBody U user) {
        try {
            userService.updateUser(user, (Class<U>) user.getClass());
            return ResponseEntity.ok(user);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    /**
     * Gets a user by their ID.
     *
     * @param id the ID of the user
     * @param clazz the class type of the user
     * @return the response entity with the user or an error message
     */
    protected ResponseEntity<?> getUserById(String id, Class<U> clazz) {
        try {
            Optional<U> user = userService.findUserById(id, clazz);
            return user.map(ResponseEntity::ok).orElseThrow(UserNotFoundException::new);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    /**
     * Gets all users.
     *
     * @param clazz the class type of the users
     * @return the response entity with the list of users or an error message
     */
    protected ResponseEntity<?> getAllUsers(Class<U> clazz) {
        try {
            List<U> users = userService.findAllUsers(clazz);
            return ResponseEntity.ok(users);
        } catch (CustomException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
}