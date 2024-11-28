package com.gomistar.proyecto_gomistar.repository.document;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.gomistar.proyecto_gomistar.model.AbstractDocument;

@NoRepositoryBean
public interface AbstractDocumentRepository extends CrudRepository<AbstractDocument, Long> {
    
}
