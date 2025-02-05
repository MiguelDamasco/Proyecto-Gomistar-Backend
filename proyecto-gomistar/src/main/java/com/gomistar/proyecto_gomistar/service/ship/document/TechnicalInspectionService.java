package com.gomistar.proyecto_gomistar.service.ship.document;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.S3ResponseDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.ship.document.AbstractDocumentShip;
import com.gomistar.proyecto_gomistar.model.ship.document.TechnicalInspectionEntity;
import com.gomistar.proyecto_gomistar.repository.ship.document.TechnicalInspectionRepository;
import com.gomistar.proyecto_gomistar.service.s3.S3Service;

import java.io.IOException;

@Service
public class TechnicalInspectionService {
    
    private final TechnicalInspectionRepository technicalInspectionRepository;

    private final S3Service s3Service;

    private final AuxiliarClass auxiliarClass;

    public TechnicalInspectionService(TechnicalInspectionRepository pTechnicalInspectionRepository, S3Service pS3Service, AuxiliarClass pAuxiliarClass) {
        this.technicalInspectionRepository = pTechnicalInspectionRepository;
        this.s3Service = pS3Service;
        this.auxiliarClass = pAuxiliarClass;
    }

    public TechnicalInspectionEntity createTechnicalInspection(MultipartFile pFile, LocalDate pExpirationDate) throws IOException {

        S3ResponseDTO response = this.s3Service.uploadFile(pFile);

        this.s3Service.uploadDownloadFile(pFile);

        TechnicalInspectionEntity myDocument = TechnicalInspectionEntity.builder().image(this.auxiliarClass.getFormat(response.name()))
                                                                                            .expirationDate(pExpirationDate)
                                                                                            .build();

        return this.technicalInspectionRepository.save(myDocument);                                                                                    
    }

    public String getDownload(AbstractShip pShip) {

        TechnicalInspectionEntity myDocument = null;

        for(AbstractDocumentShip document : new ArrayList<>(pShip.getDocumentList())) {

            if(document instanceof TechnicalInspectionEntity) {
                myDocument = (TechnicalInspectionEntity) document;
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

    public TechnicalInspectionEntity deleteBoatRegistration(AbstractShip pShip) throws IOException {

        TechnicalInspectionEntity myDocument = null;

        for(AbstractDocumentShip document : new ArrayList<>(pShip.getDocumentList())) {

            if(document instanceof TechnicalInspectionEntity) {
                myDocument = (TechnicalInspectionEntity) document;
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
