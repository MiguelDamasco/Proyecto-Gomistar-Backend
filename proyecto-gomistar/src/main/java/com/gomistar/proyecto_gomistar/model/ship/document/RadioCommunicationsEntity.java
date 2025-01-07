package com.gomistar.proyecto_gomistar.model.ship.document;


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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "Radio_communications")
public class RadioCommunicationsEntity extends AbstractDocumentShip {
    
    private String image;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;
}
