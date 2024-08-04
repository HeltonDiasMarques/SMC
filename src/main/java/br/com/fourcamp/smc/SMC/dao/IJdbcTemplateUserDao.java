package br.com.fourcamp.smc.SMC.dao;

import br.com.fourcamp.smc.SMC.model.Doctor;
import br.com.fourcamp.smc.SMC.model.Patient;
import br.com.fourcamp.smc.SMC.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface for DAO operations related to User using JdbcTemplate.
 *
 * @param <U> the type of user
 */
public interface IJdbcTemplateUserDao <U>  {

    /**
     * Saves a user.
     *
     * @param user the user to save
     * @param clazz the class type of the user
     */
    void save(U user, Class<U> clazz);

    /**
     * Updates a user.
     *
     * @param user the user to update
     * @param clazz the class type of the user
     */
    void update(U user, Class<U> clazz);

    /**
     * Finds a user by ID.
     *
     * @param id the ID of the user
     * @param clazz the class type of the user
     * @return an Optional containing the found user, or empty if not found
     */
    Optional<U> findById(String id, Class<U> clazz);

    /**
     * Finds a user by email.
     *
     * @param email the email of the user
     * @param clazz the class type of the user
     * @return an Optional containing the found user, or empty if not found
     */
    Optional<U> findByEmail(String email, Class<U> clazz);

    /**
     * Finds all users.
     *
     * @param clazz the class type of the users
     * @return a list of all users
     */
    List<U> findAll(Class<U> clazz);

    /**
     * Checks if a user exists by CPF.
     *
     * @param cpf the CPF of the user
     * @param clazz the class type of the user
     * @return true if a user exists with the given CPF, false otherwise
     */
    boolean existsByCpf(String cpf, Class<U> clazz);

    /**
     * Checks if a user exists by email.
     *
     * @param email the email of the user
     * @param clazz the class type of the user
     * @return true if a user exists with the given email, false otherwise
     */
    boolean existsByEmail(String email, Class<U> clazz);

    /**
     * Checks if a user exists by phone number.
     *
     * @param phone the phone number of the user
     * @param clazz the class type of the user
     * @return true if a user exists with the given phone number, false otherwise
     */
    boolean existsByPhone(String phone, Class<U> clazz);

    /**
     * Checks if a doctor exists by CRM.
     *
     * @param crm the CRM of the doctor
     * @return true if a doctor exists with the given CRM, false otherwise
     */
    boolean existsByCrm(String crm);
}
