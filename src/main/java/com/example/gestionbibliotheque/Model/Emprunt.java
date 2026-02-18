package com.example.gestionbibliotheque.Model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Emprunt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;
    @ManyToOne
    @JoinColumn(name = "idEtudiant", nullable = false)
    private Etudiant etudiant;
    @OneToOne
    @JoinColumn(name = "idLivre", nullable = false)
    private Livre livre;
}
