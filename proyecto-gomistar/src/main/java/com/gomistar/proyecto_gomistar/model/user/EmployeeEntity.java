package com.gomistar.proyecto_gomistar.model.user;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gomistar.proyecto_gomistar.model.user.document.AbstractDocument;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employee")
public class EmployeeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "is_active", columnDefinition = "TINYINT(1)")
    private boolean isActive;

    @OneToMany(mappedBy = "employee", targetEntity = AbstractDocument.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AbstractDocument> documents; 


    public void addDocument(AbstractDocument pDocument) {
        pDocument.setEmployee(this);
        this.documents.add(pDocument);
    }

    public void removeDocument(AbstractDocument pDocument) {
        pDocument.setEmployee(null);
        this.documents.remove(pDocument);
    }
}
