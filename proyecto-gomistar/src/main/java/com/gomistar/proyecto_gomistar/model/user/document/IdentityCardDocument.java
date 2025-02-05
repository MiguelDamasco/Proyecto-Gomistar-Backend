package com.gomistar.proyecto_gomistar.model.user.document;

import java.time.LocalDate;

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
    
    private String image;
    
    private String name;

    private String lastname;

    private String nationality;

    @Column(name = "identity_number")
    private String identityNumber;
    
    private LocalDate birthday;

    @Column(name = "expedition_date")
    private LocalDate expeditionDate;

    @Column(name = "expiration_date")
    private LocalDate expirationData;

}
