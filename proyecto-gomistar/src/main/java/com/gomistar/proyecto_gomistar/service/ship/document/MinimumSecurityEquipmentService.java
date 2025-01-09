package com.gomistar.proyecto_gomistar.service.ship.document;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.S3ResponseDTO;
import com.gomistar.proyecto_gomistar.model.ship.document.MinimumSecurityEquipmentEntity;
import com.gomistar.proyecto_gomistar.repository.ship.document.MinimumSecurityEquipmentRepository;
import com.gomistar.proyecto_gomistar.service.s3.S3Service;

@Service
public class MinimumSecurityEquipmentService {
    
    private final MinimumSecurityEquipmentRepository minimumSecurityEquipmentRepository;

    private final S3Service s3Service;

    public MinimumSecurityEquipmentService(MinimumSecurityEquipmentRepository pMinimumSecurityEquipmentRepository, S3Service pS3Service) {
        this.minimumSecurityEquipmentRepository = pMinimumSecurityEquipmentRepository;
        this.s3Service = pS3Service;
    }

    public MinimumSecurityEquipmentEntity createMinimumSecurityEquipment(MultipartFile pFile, LocalDate pExpirationDate) throws IOException {
        
        S3ResponseDTO response = this.s3Service.uploadFile(pFile);

        this.s3Service.uploadDownloadFile(pFile);

        MinimumSecurityEquipmentEntity myDocument = MinimumSecurityEquipmentEntity.builder().image(response.name())
                                                                                            .expirationDate(pExpirationDate)
                                                                                            .build();

        return this.minimumSecurityEquipmentRepository.save(myDocument);
    }
}
