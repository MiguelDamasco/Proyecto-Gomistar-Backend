package com.gomistar.proyecto_gomistar.repository.document;

import org.springframework.stereotype.Repository;
import java.util.List;

import com.gomistar.proyecto_gomistar.model.EmployeeEntity;
import com.gomistar.proyecto_gomistar.model.document.TextDocument;


@Repository
public interface TextDocumentRepository extends AbstractDocumentRepository {
    
    List<TextDocument> findByEmployee(EmployeeEntity employee);
}
