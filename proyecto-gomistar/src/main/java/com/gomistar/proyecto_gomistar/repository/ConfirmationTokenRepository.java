package com.gomistar.proyecto_gomistar.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gomistar.proyecto_gomistar.model.ConfirmationTokenEntity;
import com.gomistar.proyecto_gomistar.model.UserEntity;

import java.util.List;


@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationTokenEntity, Long>{

    List<ConfirmationTokenEntity> findByUsers(UserEntity users);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationTokenEntity ct SET ct.isActive = false WHERE ct.users.id = :userId")
    void deactivateTokensByUserId(@Param("userId") Long userId);

}
