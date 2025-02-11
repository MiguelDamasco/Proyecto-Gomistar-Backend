package com.gomistar.proyecto_gomistar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gomistar.proyecto_gomistar.model.user.UserEntity;

import java.util.List;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
 
    Optional<UserEntity> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query("SELECT u.amountAlerts FROM UserEntity u WHERE u.id = :id")
    Optional<Integer> getAmountAlerts(@Param("id") Long id);

    Optional<UserEntity> findByEmail(String email);

    @Query(value = """
            SELECT e.id as employee_id, u.id, u.email, u.password, u.username
            FROM users u
            INNER JOIN employee e
            ON u.employee_id = e.id
            WHERE e.id = :id;
            """, nativeQuery = true)
    Optional<UserEntity> findByEmployee(@Param("id") long pId);

   /*  @Query("SELECT u FROM UserEntity u WHERE u.username = ?1")
    Optional<UserEntity> getName(String username);
    */
}
