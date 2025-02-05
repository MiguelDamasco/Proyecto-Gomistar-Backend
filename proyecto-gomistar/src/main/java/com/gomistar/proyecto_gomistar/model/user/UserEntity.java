package com.gomistar.proyecto_gomistar.model.user;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gomistar.proyecto_gomistar.model.alert.DocumentAlertEntity;
import com.gomistar.proyecto_gomistar.model.email.ConfirmationTokenEntity;
import com.gomistar.proyecto_gomistar.model.role.RoleEntity;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;
import com.gomistar.proyecto_gomistar.model.user.document.AbstractDocument;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotBlank
    @Size(max = 80)
    private String email;

    @NotBlank
    @Size(max = 30)
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    @Size(max = 30)
    private String name;

    @NotBlank
    @Size(max = 30)
    private String lastname;

    private int amountAlerts;

    @Column(name = "is_confirmed", columnDefinition = "TINYINT(1)")
    private boolean isConfirmed;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

    @OneToMany(mappedBy = "users", targetEntity = ConfirmationTokenEntity.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ConfirmationTokenEntity> tokens;

    @ManyToOne(targetEntity = AbstractShip.class)
    @JoinColumn(name = "ship_id")
    @JsonBackReference
    private AbstractShip ship;

    @OneToMany(mappedBy = "user", targetEntity = AbstractDocument.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AbstractDocument> documents;

    @OneToMany(mappedBy = "user", targetEntity = DocumentAlertEntity.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DocumentAlertEntity> alertList;

    public void addToken(ConfirmationTokenEntity pToken) {
        this.tokens.add(pToken);
    }

    public List<ConfirmationTokenEntity> getTokens() {
        return this.tokens;
    }

    public void addDocument(AbstractDocument pDocument) {
        pDocument.setUser(this);
        this.documents.add(pDocument);
    }

    public void removeDocument(AbstractDocument pDocument) {
        pDocument.setUser(null);
        this.documents.remove(pDocument);
    }

    public void addAlert(DocumentAlertEntity pAlert) {
        pAlert.setUser(this);
        this.alertList.add(pAlert);
    }

    public void removeAlert(DocumentAlertEntity pAlert) {
        pAlert.setUser(null);
        this.alertList.remove(pAlert);
    }

}
