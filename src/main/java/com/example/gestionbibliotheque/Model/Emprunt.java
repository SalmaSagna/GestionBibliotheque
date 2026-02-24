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
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDate dateEmprunt;
    @Column(nullable = false)
    private LocalDate dateRetour;
    @OneToOne
    @JoinColumn(name = "idEtudiant", nullable = false)
    private Etudiant etudiant;
    @OneToOne
    @JoinColumn(name = "idLivre", nullable = false)
    private Livre livre;
}
