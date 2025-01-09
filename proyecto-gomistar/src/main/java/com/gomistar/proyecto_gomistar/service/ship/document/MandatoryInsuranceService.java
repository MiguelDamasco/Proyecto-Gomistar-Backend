package com.gomistar.proyecto_gomistar.service.ship.document;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.S3ResponseDTO;
import com.gomistar.proyecto_gomistar.model.ship.document.MandatoryInsuranceEntity;
import com.gomistar.proyecto_gomistar.repository.ship.document.MandatoryInsuranceRepository;
import com.gomistar.proyecto_gomistar.service.s3.S3Service;

@Service
public class MandatoryInsuranceService {
    
    private final MandatoryInsuranceRepository mandatoryInsuranceRepository;

    private final S3Service s3Service;

    public MandatoryInsuranceService(MandatoryInsuranceRepository pMandatoryInsuranceRepository, S3Service pS3Service) {
        this.mandatoryInsuranceRepository = pMandatoryInsuranceRepository;
        this.s3Service = pS3Service;
    }

    public String getFormat(String s) {

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < s.length(); i++) {

            if(s.charAt(i) == ' ') {
                sb.append('-');
                continue;
            }

            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

    public MandatoryInsuranceEntity createMandatoryInsurance(MultipartFile pFile, LocalDate pExpirationDate) throws IOException {

        S3ResponseDTO resonse = this.s3Service.uploadFile(pFile);

        this.s3Service.uploadDownloadFile(pFile);

        MandatoryInsuranceEntity myDocument = MandatoryInsuranceEntity.builder().image(getFormat(resonse.name()))
                                                                                .expirationDate(pExpirationDate)
                                                                                .build();

        return this.mandatoryInsuranceRepository.save(myDocument);
    }
}
