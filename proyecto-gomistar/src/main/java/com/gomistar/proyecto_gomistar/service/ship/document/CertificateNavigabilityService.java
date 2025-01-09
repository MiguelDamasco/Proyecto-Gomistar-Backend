package com.gomistar.proyecto_gomistar.service.ship.document;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.S3ResponseDTO;
import com.gomistar.proyecto_gomistar.model.ship.document.CertificateNavigabilityEntity;
import com.gomistar.proyecto_gomistar.repository.ship.document.CertificateNavigabilityRepository;
import com.gomistar.proyecto_gomistar.service.s3.S3Service;

@Service
public class CertificateNavigabilityService {
    
    private final CertificateNavigabilityRepository certificateNavigabilityRepository;

    private final S3Service s3Service;

    public CertificateNavigabilityService(CertificateNavigabilityRepository pCertificateNavigabilityRepository,S3Service pS3Service) {
        this.certificateNavigabilityRepository = pCertificateNavigabilityRepository;
        this.s3Service = pS3Service;
    }

    public CertificateNavigabilityEntity createCertificateNavigability(MultipartFile pFile, LocalDate pExpirationDate) throws IOException {

        S3ResponseDTO response = this.s3Service.uploadFile(pFile);

        this.s3Service.uploadDownloadFile(pFile);

        CertificateNavigabilityEntity myDocument = CertificateNavigabilityEntity.builder().image(response.name())
                                                                                            .expirationDate(pExpirationDate)
                                                                                            .build();


        return this.certificateNavigabilityRepository.save(myDocument);

    }
}
