package tn.esprit.tpsi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    private String nom;

    private String ville;

    private String adresse;

    private String telephone;

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Vehicule> vehicules = new ArrayList<>();

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Employe> employes = new ArrayList<>();
}
