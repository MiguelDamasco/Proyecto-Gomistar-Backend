package com.gomistar.proyecto_gomistar.service.document;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.request.CreateIdentityCardDTO;
import com.gomistar.proyecto_gomistar.DTO.response.ResponseIdentityCardDTO;
import com.gomistar.proyecto_gomistar.model.AbstractDocument;
import com.gomistar.proyecto_gomistar.model.document.IdentityCardDocument;
import com.gomistar.proyecto_gomistar.repository.document.IdentityCardRepository;

@Service
public class IdentityCardService {
    
    private final IdentityCardRepository identityCardRepository;

    public IdentityCardService(IdentityCardRepository pIdentityCardRepository) {
        this.identityCardRepository = pIdentityCardRepository;
    }

    public ResponseIdentityCardDTO getIdentityCardById(String pId) {

        Optional<AbstractDocument> myDocument = this.identityCardRepository.findById(Long.parseLong(pId));
        
        if(myDocument.isPresent()) {
            return new ResponseIdentityCardDTO("Documento de identidad encontrado!", myDocument.get());
        }

        return new ResponseIdentityCardDTO("Documento no encontrado", null);
    }

    public ResponseIdentityCardDTO saveIdentityCard(CreateIdentityCardDTO pIdentityCard) {

        AbstractDocument myDocument = IdentityCardDocument.builder().name(pIdentityCard.name())
                                                                        .lastname(pIdentityCard.lastname())
                                                                        .birthPlace(pIdentityCard.birthPlace())
                                                                        .birthday(pIdentityCard.birthday())
                                                                        .expeditionDate(pIdentityCard.expeditionDate())
                                                                        .identityNumber(pIdentityCard.identityNumber())
                                                                        .expirationData(pIdentityCard.expirationData())
                                                                        .build();
                    
        AbstractDocument result = this.identityCardRepository.save(myDocument);

        return new ResponseIdentityCardDTO("Cedula de identidad ingresada con exito!", result);
        

    }
}
