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
import com.gomistar.proyecto_gomistar.model.ship.document.MandatoryInsuranceEntity;
import com.gomistar.proyecto_gomistar.model.ship.document.RadioCommunicationsEntity;
import com.gomistar.proyecto_gomistar.repository.ship.document.RadioCommunicationsRepository;
import com.gomistar.proyecto_gomistar.service.s3.S3Service;

@Service
public class RadioCommunicationsService {
    
    private final RadioCommunicationsRepository radioCommunicationsRepository;

    private final S3Service s3Service;

    private final AuxiliarClass auxiliarClass;

    public RadioCommunicationsService(RadioCommunicationsRepository pRadioCommunicationsRepository, S3Service pS3Service, AuxiliarClass pAuxiliarClass) {
        this.radioCommunicationsRepository = pRadioCommunicationsRepository;
        this.s3Service = pS3Service;
        this.auxiliarClass = pAuxiliarClass;
    }

    public RadioCommunicationsEntity createRadioCommunications(MultipartFile pFile, LocalDate pExpirationDate) throws IOException {

        S3ResponseDTO response = this.s3Service.uploadFile(pFile);

        this.s3Service.uploadDownloadFile(pFile);

        RadioCommunicationsEntity myDocument = RadioCommunicationsEntity.builder().image(this.auxiliarClass.getFormat(response.name())).expirationDate(pExpirationDate).build();

        return this.radioCommunicationsRepository.save(myDocument);
    }

    public String getDownload(AbstractShip pShip) {

        RadioCommunicationsEntity myDocument = null;

        for(AbstractDocumentShip document : new ArrayList<>(pShip.getDocumentList())) {

            if(document instanceof RadioCommunicationsEntity) {
                myDocument = (RadioCommunicationsEntity) document;
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

    public RadioCommunicationsEntity deleteBoatRegistration(AbstractShip pShip) throws IOException {

        RadioCommunicationsEntity myDocument = null;

        for(AbstractDocumentShip document : new ArrayList<>(pShip.getDocumentList())) {

            if(document instanceof RadioCommunicationsEntity) {
                myDocument = (RadioCommunicationsEntity) document;
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
