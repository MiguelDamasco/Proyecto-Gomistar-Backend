package com.gomistar.proyecto_gomistar.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gomistar.proyecto_gomistar.model.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
 
    Optional<UserEntity> findByUsername(String username);

   /*  @Query("SELECT u FROM UserEntity u WHERE u.username = ?1")
    Optional<UserEntity> getName(String username);
    */
}
