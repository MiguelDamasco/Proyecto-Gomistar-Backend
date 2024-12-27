package com.gomistar.proyecto_gomistar.repository.document;

import org.springframework.stereotype.Repository;

import com.gomistar.proyecto_gomistar.model.user.EmployeeEntity;
import com.gomistar.proyecto_gomistar.model.user.document.IdentityCardDocument;

import java.util.List;


@Repository
public interface IdentityCardRepository extends AbstractDocumentRepository{
    
    List<IdentityCardDocument> findByEmployee(EmployeeEntity employee);
}
