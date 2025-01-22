package com.gomistar.proyecto_gomistar.model.alert;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "document_alert")
public class DocumentAlertEntity extends AbstractAlert {
    
    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "id_user")
    @JsonBackReference
    private UserEntity user;

    private Byte type;
}
