package com.gomistar.proyecto_gomistar.service.document;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.request.CreateIdentityCardDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.AbstractDocument;
import com.gomistar.proyecto_gomistar.model.EmployeeEntity;
import com.gomistar.proyecto_gomistar.model.document.IdentityCardDocument;
import com.gomistar.proyecto_gomistar.repository.document.IdentityCardRepository;

@Service
public class IdentityCardService {
    
    private final IdentityCardRepository identityCardRepository;

    public IdentityCardService(IdentityCardRepository pIdentityCardRepository) {
        this.identityCardRepository = pIdentityCardRepository;
    }

    public AbstractDocument getIdentityCardById(String pId) {

        Optional<AbstractDocument> myDocumentOptional = this.identityCardRepository.findById(Long.parseLong(pId));
        
        if(!myDocumentOptional.isPresent()) {
            throw new RequestException("P-280!", "Documento no encontrado");
        }

        return myDocumentOptional.get(); 
    }

    public boolean exists(EmployeeEntity pEmployee) {
        
        List<IdentityCardDocument> myList = this.identityCardRepository.findByEmployee(pEmployee);

        return myList.size() == 0;
    }

    public AbstractDocument saveIdentityCard(AbstractDocument pIdentityCard) {
                    
        return this.identityCardRepository.save(pIdentityCard);
    }
}
