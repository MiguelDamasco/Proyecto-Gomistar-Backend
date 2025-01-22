package com.gomistar.proyecto_gomistar.service.document;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.S3ResponseDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;
import com.gomistar.proyecto_gomistar.model.user.document.AbstractDocument;
import com.gomistar.proyecto_gomistar.model.user.document.HealthCardEntity;
import com.gomistar.proyecto_gomistar.model.user.document.QualifyingTitleEntity;
import com.gomistar.proyecto_gomistar.repository.document.QualifyingTitleRepository;
import com.gomistar.proyecto_gomistar.service.s3.S3Service;
import com.gomistar.proyecto_gomistar.service.ship.document.AuxiliarClass;

@Service
public class QualifyingTitleService {
    
    private final QualifyingTitleRepository qualifyingTitleRepository;

    private final S3Service s3Service;

    private final AuxiliarClass auxiliarClass;

    public QualifyingTitleService(QualifyingTitleRepository pQualifyingTitleRepository, S3Service pS3Service, AuxiliarClass pAuxiliarClass) {
        this.qualifyingTitleRepository = pQualifyingTitleRepository;
        this.s3Service = pS3Service;
        this.auxiliarClass = pAuxiliarClass;
    }

    public QualifyingTitleEntity createHealthCard(MultipartFile pFile, LocalDate pLocalDate) throws IOException {

        S3ResponseDTO response = this.s3Service.uploadFile(pFile);

        this.s3Service.uploadDownloadFile(pFile);

        QualifyingTitleEntity myQualifyingTitle = QualifyingTitleEntity.builder()
                                                .image(this.auxiliarClass.getFormat(response.name()))
                                                .expirationDate(pLocalDate)
                                                .build();


        return this.qualifyingTitleRepository.save(myQualifyingTitle);                    
    }

    public String getDownload(UserEntity pUser) {

        QualifyingTitleEntity myDocument = null;

        for(AbstractDocument document : new ArrayList<>(pUser.getDocuments())) {

            if(document instanceof QualifyingTitleEntity) {
                myDocument = (QualifyingTitleEntity) document;
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


    public QualifyingTitleEntity deleteBoatRegistration(UserEntity pUser) throws IOException {

        QualifyingTitleEntity myDocument = null;

        for(AbstractDocument document : new ArrayList<>(pUser.getDocuments())) {

            if(document instanceof QualifyingTitleEntity) {
                myDocument = (QualifyingTitleEntity) document;
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
