package com.gomistar.proyecto_gomistar.service.ship.document;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.S3ResponseDTO;
import com.gomistar.proyecto_gomistar.model.ship.document.BoatRegistrationEntity;
import com.gomistar.proyecto_gomistar.repository.ship.document.BoatRegistrationRepository;
import com.gomistar.proyecto_gomistar.service.s3.S3Service;

@Service
public class BoatRegistrationService {
    
    private final BoatRegistrationRepository boatRegistrationRepository;

    private final S3Service s3Service;

    public BoatRegistrationService(S3Service pS3Service, BoatRegistrationRepository pBoatRegistrationRepository) {
        this.s3Service = pS3Service;
        this.boatRegistrationRepository = pBoatRegistrationRepository;
    }

    public BoatRegistrationEntity createBoatRegistration(MultipartFile pFile, LocalDate pExpirationDate) throws IOException {

        S3ResponseDTO response = this.s3Service.uploadFile(pFile);

        BoatRegistrationEntity myBoat = BoatRegistrationEntity.builder().image(response
                                                                                .name())
                                                                                .expirationDate(pExpirationDate)
                                                                                .build();


        return this.boatRegistrationRepository.save(myBoat);

    }

}
