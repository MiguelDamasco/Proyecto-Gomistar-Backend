package com.gomistar.proyecto_gomistar.service.document;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gomistar.proyecto_gomistar.model.user.document.AbstractDocument;
import com.gomistar.proyecto_gomistar.repository.document.TextDocumentRepository;

@Service
public class DocumentService {
        
    private final TextDocumentRepository textDocumentRepository;

    public DocumentService(TextDocumentRepository pTextDocumentRepository) {
        this.textDocumentRepository = pTextDocumentRepository;
    }

    public AbstractDocument findDocumentById(Long id) {
        return this.textDocumentRepository.findById(id).orElse(null);
    }

    public List<AbstractDocument> findAll() {
        return (List<AbstractDocument>) this.textDocumentRepository.findAll();
    }

    public AbstractDocument save(AbstractDocument document) {
        return this.textDocumentRepository.save(document);
    }

    public boolean deleteDocument(Long id) {
        AbstractDocument myDocument = this.findDocumentById(id);
        if(myDocument != null) {
            this.textDocumentRepository.delete(myDocument);
            return true;
        }

        return false;
    }

}
