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
@Table(name = "tetanus_vaccine_certificate")
public class TetanusVaccineCertificateEntity extends AbstractDocument {
    
    private String image;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;
}
