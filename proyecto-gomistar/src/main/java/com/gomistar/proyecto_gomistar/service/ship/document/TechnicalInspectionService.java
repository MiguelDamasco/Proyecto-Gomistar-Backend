package com.gomistar.proyecto_gomistar.service.ship.document;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.S3ResponseDTO;
import com.gomistar.proyecto_gomistar.model.ship.document.TechnicalInspectionEntity;
import com.gomistar.proyecto_gomistar.repository.ship.document.TechnicalInspectionRepository;
import com.gomistar.proyecto_gomistar.service.s3.S3Service;

import java.io.IOException;

@Service
public class TechnicalInspectionService {
    
    private final TechnicalInspectionRepository technicalInspectionRepository;

    private final S3Service s3Service;

    public TechnicalInspectionService(TechnicalInspectionRepository pTechnicalInspectionRepository, S3Service pS3Service) {
        this.technicalInspectionRepository = pTechnicalInspectionRepository;
        this.s3Service = pS3Service;
    }

    public TechnicalInspectionEntity createTechnicalInspection(MultipartFile pFile, LocalDate pExpirationDate) throws IOException {

        S3ResponseDTO response = this.s3Service.uploadFile(pFile);

        TechnicalInspectionEntity myDocument = TechnicalInspectionEntity.builder().image(response.name())
                                                                                            .expirationDate(pExpirationDate)
                                                                                            .build();

        return this.technicalInspectionRepository.save(myDocument);                                                                                    
    }

}
