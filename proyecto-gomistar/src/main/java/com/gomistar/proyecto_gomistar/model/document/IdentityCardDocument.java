package com.gomistar.proyecto_gomistar.model.document;

import com.gomistar.proyecto_gomistar.model.AbstractDocument;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "identity_card")
public class IdentityCardDocument extends AbstractDocument {
    
    private String name;

    private String lastname;

    private String nationality;

    @Column(name = "identity_number")
    private String identityNumber;

    @Column(name = "birth_place")
    private String birthPlace;
    
    private String birthday;

    @Column(name = "expedition_date")
    private String expeditionDate;

    @Column(name = "expiration_date")
    private String expirationData;

}
