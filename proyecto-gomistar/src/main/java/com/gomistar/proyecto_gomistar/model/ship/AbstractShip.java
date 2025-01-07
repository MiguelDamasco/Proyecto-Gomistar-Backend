package com.gomistar.proyecto_gomistar.model.ship;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.gomistar.proyecto_gomistar.model.ship.document.AbstractDocumentShip;
import com.gomistar.proyecto_gomistar.model.user.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "ship")
public abstract class AbstractShip {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "ship", targetEntity = UserEntity.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, orphanRemoval = false)
    @JsonManagedReference
    private List<UserEntity> userList = new ArrayList<>();

    @OneToMany(mappedBy = "abstractShip", targetEntity = AbstractDocumentShip.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AbstractDocumentShip> documentList = new ArrayList<>();

    public void addUser(UserEntity pUser) {
        pUser.setShip(this);
        this.userList.add(pUser);
    }

    public void removeUser(UserEntity pUser) {
        pUser.setShip(null);
        this.userList.remove(pUser);
    }

    public void removeAllUsers() {
        if (userList == null || userList.isEmpty()) {
            return;
        }

        for (UserEntity user : new ArrayList<>(userList)) {
            this.removeUser(user);
        }
    }

    public void addDocument(AbstractDocumentShip pDocument) {
        pDocument.setAbstractShip(this);
        this.documentList.add(pDocument);
    }

    public void removeDocument(AbstractDocumentShip pDocument) {
        pDocument.setAbstractShip(null);
        this.documentList.remove(pDocument);
    }

    public void removeAllDocuments() {
        if(documentList == null || documentList.isEmpty()) {
            return;
        }

        for(AbstractDocumentShip document : new ArrayList<>(documentList)) {
            this.removeDocument(document);
        }
    }


}
