package com.gomistar.proyecto_gomistar.service.ship.document;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.S3ResponseDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.ship.document.AbstractDocumentShip;
import com.gomistar.proyecto_gomistar.model.ship.document.CertificateNavigabilityEntity;
import com.gomistar.proyecto_gomistar.repository.ship.document.CertificateNavigabilityRepository;
import com.gomistar.proyecto_gomistar.service.s3.S3Service;

@Service
public class CertificateNavigabilityService {
    
    private final CertificateNavigabilityRepository certificateNavigabilityRepository;

    private final S3Service s3Service;

    private final AuxiliarClass auxiliarClass;

    public CertificateNavigabilityService(CertificateNavigabilityRepository pCertificateNavigabilityRepository,S3Service pS3Service,AuxiliarClass pAuxiliarClass) {
        this.certificateNavigabilityRepository = pCertificateNavigabilityRepository;
        this.s3Service = pS3Service;
        this.auxiliarClass = pAuxiliarClass;
    }

    public CertificateNavigabilityEntity createCertificateNavigability(MultipartFile pFile, LocalDate pExpirationDate) throws IOException {

        S3ResponseDTO response = this.s3Service.uploadFile(pFile);

        this.s3Service.uploadDownloadFile(pFile);

        CertificateNavigabilityEntity myDocument = CertificateNavigabilityEntity.builder().image(this.auxiliarClass.getFormat(response.name()))
                                                                                            .expirationDate(pExpirationDate)
                                                                                            .build();


        return this.certificateNavigabilityRepository.save(myDocument);

    }

    public String getDownload(AbstractShip pShip) {

        CertificateNavigabilityEntity myDocument = null;

        for(AbstractDocumentShip document : new ArrayList<>(pShip.getDocumentList())) {

            if(document instanceof CertificateNavigabilityEntity) {
                myDocument = (CertificateNavigabilityEntity) document;
                break;
            }
        }
        
        if(myDocument == null) {
            throw new RequestException("p-203", "Documento no encontrado!");
        }

        String image = myDocument.getImage();

        String endImage = "https://" + "descargas-gomistar" + ".s3.us-east-2.amazonaws.com/" + this.auxiliarClass.extractName(image);

        return endImage;
    }

    public CertificateNavigabilityEntity deleteBoatRegistration(AbstractShip pShip) throws IOException {

        CertificateNavigabilityEntity myDocument = null;

        for(AbstractDocumentShip document : new ArrayList<>(pShip.getDocumentList())) {

            if(document instanceof CertificateNavigabilityEntity) {
                myDocument = (CertificateNavigabilityEntity) document;
                break;
            }
        }
        
        if(myDocument == null) {
            throw new RequestException("p-203", "Documento no encontrado!");
        }

        String image = myDocument.getImage();

        String endImage = this.auxiliarClass.extractName(image);

        this.s3Service.deletFile(endImage);

        this.s3Service.deletDownloadFile(endImage);

        return myDocument;

    }


}
