package com.example.gestionbibliotheque.Model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String auteur;
    private String titre;
    private String isbn;
    @OneToOne(mappedBy = "livre",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Emprunt emprunt;
}
