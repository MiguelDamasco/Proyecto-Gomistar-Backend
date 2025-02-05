package com.gomistar.proyecto_gomistar.service.document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gomistar.proyecto_gomistar.DTO.response.S3ResponseDTO;
import com.gomistar.proyecto_gomistar.DTO.response.user.CreateIdentityCardDTO;
import com.gomistar.proyecto_gomistar.DTO.response.user.IdentityCardLectureDTO;
import com.gomistar.proyecto_gomistar.DTO.response.user.ViewIdentityCardDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;
import com.gomistar.proyecto_gomistar.model.user.document.AbstractDocument;
import com.gomistar.proyecto_gomistar.model.user.document.IdentityCardDocument;
import com.gomistar.proyecto_gomistar.repository.document.IdentityCardRepository;
import com.gomistar.proyecto_gomistar.service.lambda.LambdaService;
import com.gomistar.proyecto_gomistar.service.s3.S3Service;
import com.gomistar.proyecto_gomistar.service.ship.document.AuxiliarClass;

@Service
public class IdentityCardService {
    
    private final IdentityCardRepository identityCardRepository;

    private final LambdaService lambdaService;

    private final S3Service s3Service;

    private final AuxiliarClass auxiliarClass;

    public IdentityCardService(IdentityCardRepository pIdentityCardRepository, LambdaService pLambdaService, S3Service pS3Service, AuxiliarClass pAuxiliarClass) {
        this.identityCardRepository = pIdentityCardRepository;
        this.lambdaService = pLambdaService;
        this.s3Service = pS3Service;
        this.auxiliarClass = pAuxiliarClass;
    }

    public IdentityCardLectureDTO fileLecture(MultipartFile pFile) throws IOException {

        S3ResponseDTO response = this.s3Service.uploadFile(pFile);
        this.s3Service.uploadDownloadFile(pFile);
        IdentityCardLectureDTO myDTO = this.lambdaService.getText(pFile.getOriginalFilename());
        IdentityCardLectureDTO result = new IdentityCardLectureDTO(response.name(), myDTO.name(), myDTO.lastname(), myDTO.nationality(), myDTO.identityNumber(), myDTO.birthday(), myDTO.expeditionDate(), myDTO.expirationData());
        return result;
    }

    public IdentityCardDocument createIdentityCard(CreateIdentityCardDTO pDTO) throws IOException {

        IdentityCardDocument myDocument = IdentityCardDocument.builder().name(pDTO.name())
                                                                        .lastname(pDTO.lastname())
                                                                        .nationality(pDTO.nationality())
                                                                        .identityNumber(pDTO.identityNumber())
                                                                        .birthday(pDTO.birthday())
                                                                        .expeditionDate(pDTO.expeditionDate())
                                                                        .expirationData(pDTO.expirationData())
                                                                        .image(pDTO.file())
                                                                        .build();


        return this.identityCardRepository.save(myDocument);

    }

    public ViewIdentityCardDTO getIdentityCard(UserEntity pUser) {

        IdentityCardDocument myDocument = null;

        for(AbstractDocument document : new ArrayList<>(pUser.getDocuments())) {

            if(document instanceof IdentityCardDocument) {
                myDocument = (IdentityCardDocument) document;
                break;
            }
        }
        
        if(myDocument == null) {
            throw new RequestException("p-203", "Documento no encontrado!");
        }

        ViewIdentityCardDTO myDTO = new ViewIdentityCardDTO(myDocument.getName(), myDocument.getLastname(), myDocument.getNationality(), myDocument.getIdentityNumber(), this.auxiliarClass.getDate(myDocument.getBirthday()), this.auxiliarClass.getDate(myDocument.getExpeditionDate()), this.auxiliarClass.getDate(myDocument.getExpirationData()));
        return myDTO;
    }

    public AbstractDocument getIdentityCardById(String pId) {

        Optional<AbstractDocument> myDocumentOptional = this.identityCardRepository.findById(Long.parseLong(pId));
        
        if(!myDocumentOptional.isPresent()) {
            throw new RequestException("P-280!", "Documento no encontrado");
        }

        return myDocumentOptional.get(); 
    }

    public AbstractDocument saveIdentityCard(AbstractDocument pIdentityCard) {
                    
        return this.identityCardRepository.save(pIdentityCard);
    }

    public String getDownload(UserEntity pUser) {

        IdentityCardDocument myDocument = null;

        for(AbstractDocument document : new ArrayList<>(pUser.getDocuments())) {

            if(document instanceof IdentityCardDocument) {
                myDocument = (IdentityCardDocument) document;
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

    public IdentityCardDocument deleteIdentityCard(UserEntity pUser) throws IOException {

        IdentityCardDocument myDocument = null;

        for(AbstractDocument document : new ArrayList<>(pUser.getDocuments())) {

            if(document instanceof IdentityCardDocument) {
                myDocument = (IdentityCardDocument) document;
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

    public void cancelIdentityCard(String pImage) throws IOException {

        String endImage = this.auxiliarClass.extractName(pImage);

        this.s3Service.deletFile(endImage);

        this.s3Service.deletDownloadFile(endImage);

    }
}
