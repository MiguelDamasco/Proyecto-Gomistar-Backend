package com.gomistar.proyecto_gomistar.service.alert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.alert.AbstractAlert;
import com.gomistar.proyecto_gomistar.model.alert.DocumentAlertEntity;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;
import com.gomistar.proyecto_gomistar.repository.alert.UserAlertRepository;

@Service
public class UserAlertService {
    
    private final UserAlertRepository userAlertRepository;

    public UserAlertService(UserAlertRepository pUserAlertRepository) {
        this.userAlertRepository = pUserAlertRepository;
    }

    public DocumentAlertEntity getByType(UserEntity pUser, Byte pType) {

        for(DocumentAlertEntity alert : new ArrayList<>(pUser.getAlertList())) {

            if(alert.getType() == pType) {
                return alert;
            }
        }

        return null;
    }

    public List<DocumentAlertEntity> listAll() {

        List<DocumentAlertEntity> resultList = new ArrayList<>();
        List<AbstractAlert> alertList = (List<AbstractAlert>) this.userAlertRepository.findAll();

        for(AbstractAlert alert : alertList) {

            if(alert instanceof DocumentAlertEntity) {
                resultList.add((DocumentAlertEntity) alert);
            }
        }

        return resultList;
    }

    public AbstractAlert getAbstractAlert(String pId) {

        Optional<AbstractAlert> myAlertOptional = this.userAlertRepository.findById(Long.parseLong(pId));

        if(!myAlertOptional.isPresent()) {
            throw new RequestException("p-209", "No existe la alerta buscada");
        }

        return myAlertOptional.get();
    }

    public DocumentAlertEntity getAlert(String pId) {

        Optional<AbstractAlert> myAlertOptional = this.userAlertRepository.findById(Long.parseLong(pId));

        if(!myAlertOptional.isPresent()) {
            throw new RequestException("p-209", "No existe la alerta buscada");
        }

        return (DocumentAlertEntity) myAlertOptional.get();
    }

    public DocumentAlertEntity createAlert(LocalDate pDate, UserEntity pUser, Byte pByte) {

        DocumentAlertEntity myAlert = DocumentAlertEntity.builder().build();
        myAlert.setDate(pDate);
        myAlert.setUser(pUser);
        myAlert.setType(pByte);

        return this.userAlertRepository.save(myAlert);
    }

    public DocumentAlertEntity modifyAlert(DocumentAlertEntity pAlert, LocalDate pDate) {

        pAlert.setDate(pDate);

        return this.userAlertRepository.save(pAlert);
    }

    public void deleteAlertById(String pIdAlert) {

        DocumentAlertEntity myAlert = this.getAlert(pIdAlert);
        
        this.userAlertRepository.delete(myAlert);
    }

    public void deleteAlert(DocumentAlertEntity pAlert) {

        this.userAlertRepository.delete(pAlert);
    }

}
