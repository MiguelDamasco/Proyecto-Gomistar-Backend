package com.gomistar.proyecto_gomistar.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.DTO.IDocument;
import com.gomistar.proyecto_gomistar.DTO.request.CreateIdentityCardDTO;
import com.gomistar.proyecto_gomistar.DTO.request.CreateTextDocumentDTO;
import com.gomistar.proyecto_gomistar.DTO.request.RemoveDocumentFromEmployeeDTO;
import com.gomistar.proyecto_gomistar.exception.RequestException;
import com.gomistar.proyecto_gomistar.model.AbstractDocument;
import com.gomistar.proyecto_gomistar.model.document.IdentityCardDocument;
import com.gomistar.proyecto_gomistar.model.document.TextDocument;
import com.gomistar.proyecto_gomistar.model.EmployeeEntity;
import com.gomistar.proyecto_gomistar.repository.document.TextDocumentRepository;
import com.gomistar.proyecto_gomistar.service.document.DocumentService;
import com.gomistar.proyecto_gomistar.service.document.IdentityCardService;

@Service
public class EmployeeDocumentService {
    
    private final EmployeeService employeeService;

    private final DocumentService documentService;

    private final IdentityCardService identityCardService;

    private final TextDocumentRepository textDocumentRepository;

    public EmployeeDocumentService(EmployeeService pEmployeeService,DocumentService pDocumentService, IdentityCardService pIdentityCardService, TextDocumentRepository pTextDocumentRepository) {
        this.employeeService = pEmployeeService;
        this.documentService = pDocumentService;
        this.identityCardService = pIdentityCardService;
        this.textDocumentRepository = pTextDocumentRepository;
    }

    public List<TextDocument> listDocuments(CreateTextDocumentDTO pDTO) {

        EmployeeEntity myEmployeeOptional = this.employeeService.findEmployeeById(pDTO.idUser());

        return this.textDocumentRepository.findByEmployee(myEmployeeOptional);
    }

    public AbstractDocument saveAndAddToEmployee(IDocument pDocument) {

        AbstractDocument myDocument = null;
        EmployeeEntity myEmployee = null;

        if(pDocument instanceof CreateTextDocumentDTO) {

            CreateTextDocumentDTO DTO = (CreateTextDocumentDTO) pDocument;
            myDocument = TextDocument.builder().texto(DTO.name()).build();
            this.textDocumentRepository.save(myDocument);
        }
        else if(pDocument instanceof CreateIdentityCardDTO) {

            CreateIdentityCardDTO DTO = (CreateIdentityCardDTO) pDocument;
            myEmployee = this.employeeService.findEmployeeById(DTO.idUser());
            myDocument = IdentityCardDocument.builder().name(DTO.name())
                                                                        .lastname(DTO.lastname())
                                                                        .birthPlace(DTO.birthPlace())
                                                                        .birthday(DTO.birthday())
                                                                        .nationality(DTO.nationality())
                                                                        .expeditionDate(DTO.expeditionDate())
                                                                        .identityNumber(DTO.identityNumber())
                                                                        .expirationData(DTO.expirationData())
                                                                        .build();

            if(!this.identityCardService.exists(myEmployee)) {

                throw new RequestException("P-400", "El usuario ya tiene una cedula de identidad asignada!");
            }

            //this.identityCardService.saveIdentityCard(myDocument);
            myEmployee.addDocument(myDocument);
            this.employeeService.saveEmployee(myEmployee);                                                                                                    
        }

        return myDocument;
    }

    public void removeDocumentFromEMployee (RemoveDocumentFromEmployeeDTO pDTO) {

        EmployeeEntity myEmployee = this.employeeService.findEmployeeById(pDTO.idEmployee());
        IdentityCardDocument myDocument = (IdentityCardDocument) this.identityCardService.getIdentityCardById(pDTO.idDocument());

        myEmployee.removeDocument(myDocument);
        this.employeeService.saveEmployee(myEmployee);
    }
}
