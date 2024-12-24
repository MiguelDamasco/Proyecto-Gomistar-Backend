package com.gomistar.proyecto_gomistar.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gomistar.proyecto_gomistar.model.ship.AbstractShip;

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
import jakarta.persistence.OneToOne;
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

    @Column(name = "is_confirmed", columnDefinition = "TINYINT(1)")
    private boolean isConfirmed;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), 
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

    @OneToOne(targetEntity = EmployeeEntity.class, cascade = CascadeType.ALL)
    private EmployeeEntity employee;

    @OneToMany(mappedBy = "users", targetEntity = ConfirmationTokenEntity.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ConfirmationTokenEntity> tokens;

    @ManyToOne(targetEntity = AbstractShip.class)
    @JoinColumn(name = "ship_id")
    @JsonBackReference
    private AbstractShip ship;

    public void addToken(ConfirmationTokenEntity pToken) {
        this.tokens.add(pToken);
    }

    public List<ConfirmationTokenEntity> getTokens() {
        return this.tokens;
    }

}
