package com.gomistar.proyecto_gomistar.service.email;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.email.EmailDTO;
import com.gomistar.proyecto_gomistar.model.email.ConfirmationTokenEntity;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;
import com.gomistar.proyecto_gomistar.repository.ConfirmationTokenRepository;

@Service
public class ConfirmationTokenService {
    
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository pConfirmationTokenRepository) {
        this.confirmationTokenRepository = pConfirmationTokenRepository;
    }

    public ConfirmationTokenEntity createConfirmationToken(UserEntity pUser) {
        
        String token = UUID.randomUUID().toString();

        ConfirmationTokenEntity myToken = ConfirmationTokenEntity.builder()
                                                                    .token(token)
                                                                    .createdAt(LocalDateTime.now())
                                                                    .expiresAt(LocalDateTime.now().plusMinutes(60))
                                                                    .users(pUser)
                                                                    .isActive(true)
                                                                    .build();

        this.saveConfirmationToken(myToken);

        return myToken;
    }

    public List<ConfirmationTokenEntity> updateTokenActive(UserEntity puser) {

        List<ConfirmationTokenEntity> myList = this.confirmationTokenRepository.findByUsers(puser);
        List<ConfirmationTokenEntity> newList = new ArrayList<>();

        for(ConfirmationTokenEntity token : myList) {
            if(!token.getExpiresAt().isAfter(LocalDateTime.now())) {
                token.setIsActive(false);
            }

            newList.add(token);
        }

        return newList;
    }

    public void desactivateToken(Long pIdUser) {

        this.confirmationTokenRepository.deactivateTokensByUserId(pIdUser);
    }

    public ConfirmationTokenEntity confimrToken(List<ConfirmationTokenEntity> pList, String pToken) {

        for(ConfirmationTokenEntity t : pList) {
    
            if(t.getIsActive() && t.getToken().equals(pToken)) {
                return t;
            }   
        }
        return null;
    }

    public void saveConfirmationToken(ConfirmationTokenEntity pConfirmation) {

        this.confirmationTokenRepository.save(pConfirmation);
    } 
}
