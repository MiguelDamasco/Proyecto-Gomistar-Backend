package com.gomistar.proyecto_gomistar.service.ship.document;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.S3ResponseDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.ship.document.AbstractDocumentShip;
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

    public BoatRegistrationEntity createBoatRegistration(MultipartFile pFile, LocalDate pExpirationDate) throws IOException {

        S3ResponseDTO response = this.s3Service.uploadFile(pFile);

        this.s3Service.uploadDownloadFile(pFile);

        BoatRegistrationEntity myBoat = BoatRegistrationEntity.builder().image(getFormat(response
                                                                                .name()))
                                                                                .expirationDate(pExpirationDate)
                                                                                .build();


        return this.boatRegistrationRepository.save(myBoat);

    }


    public BoatRegistrationEntity deleteBoatRegistration(AbstractShip pShip) throws IOException {

        BoatRegistrationEntity myDocument = null;

        for(AbstractDocumentShip document : new ArrayList<>(pShip.getDocumentList())) {

            if(document instanceof BoatRegistrationEntity) {
                myDocument = (BoatRegistrationEntity) document;
                break;
            }
        }
        
        if(myDocument == null) {
            throw new RequestException("p-203", "Documento no encontrado!");
        }

        String image = myDocument.getImage();

        String endImage = this.extractName(image);

        this.s3Service.deletFile(endImage);

        this.s3Service.deletDownloadFile(endImage);

        return myDocument;

    }


    public String getDownload(AbstractShip pShip) {

        BoatRegistrationEntity myDocument = null;

        for(AbstractDocumentShip document : new ArrayList<>(pShip.getDocumentList())) {

            if(document instanceof BoatRegistrationEntity) {
                myDocument = (BoatRegistrationEntity) document;
                break;
            }
        }
        
        if(myDocument == null) {
            throw new RequestException("p-203", "Documento no encontrado!");
        }

        String image = myDocument.getImage();

        String endImage = "https://" + "descargas-gomistar" + ".s3.us-east-2.amazonaws.com/" + this.extractName(image);

        System.out.println("Resultado substring: " + endImage);

        return endImage;
    }

    public String extractName(String s) {

        StringBuilder sb = new StringBuilder();

        for(int i = s.length() - 1; i >= 0; i--) {

            if(s.charAt(i) == '/') {
                break;
            }

            sb.append(s.charAt(i));
        }

        return reverseString(sb.toString());
    }

    public String reverseString(String s) {

        StringBuilder sb = new StringBuilder();

        for(int i = s.length() - 1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }

        return sb.toString();
    }

}
