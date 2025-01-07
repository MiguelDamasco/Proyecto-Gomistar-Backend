package com.gomistar.proyecto_gomistar.service.ship.document;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.S3ResponseDTO;
import com.gomistar.proyecto_gomistar.model.ship.document.RadioCommunicationsEntity;
import com.gomistar.proyecto_gomistar.repository.ship.document.RadioCommunicationsRepository;
import com.gomistar.proyecto_gomistar.service.s3.S3Service;

@Service
public class RadioCommunicationsService {
    
    private final RadioCommunicationsRepository radioCommunicationsRepository;

    private final S3Service s3Service;

    public RadioCommunicationsService(RadioCommunicationsRepository pRadioCommunicationsRepository, S3Service pS3Service) {
        this.radioCommunicationsRepository = pRadioCommunicationsRepository;
        this.s3Service = pS3Service;
    }

    public RadioCommunicationsEntity createRadioCommunications(MultipartFile pFile, LocalDate pExpirationDate) throws IOException {

        S3ResponseDTO response = this.s3Service.uploadFile(pFile);

        RadioCommunicationsEntity myDocument = RadioCommunicationsEntity.builder().image(response.name()).expirationDate(pExpirationDate).build();

        return this.radioCommunicationsRepository.save(myDocument);
    }
}
