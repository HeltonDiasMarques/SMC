package br.com.fourcamp.smc.SMC.dao;

import br.com.fourcamp.smc.SMC.model.Doctor;
import br.com.fourcamp.smc.SMC.model.Patient;
import br.com.fourcamp.smc.SMC.model.User;

import java.util.List;
import java.util.Optional;

public interface IJdbcTemplateUserDao <U>  {
    void save(U user, Class<U> clazz);
    void update(U user, Class<U> clazz);
    Optional<U> findById(String id, Class<U> clazz);
    Optional<U> findByEmail(String email, Class<U> clazz);
    List<U> findAll(Class<U> clazz);
    boolean existsByCpf(String cpf, Class<U> clazz);
    boolean existsByEmail(String email, Class<U> clazz);
    boolean existsByPhone(String phone, Class<U> clazz);
    boolean existsByCrm(String crm);
}
