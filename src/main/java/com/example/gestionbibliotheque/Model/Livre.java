package com.example.gestionbibliotheque.Model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Livre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String auteur;
    @Column(nullable = false)
    private String titre;
    @Column(nullable = false)
    private String isbn;
    @OneToOne(mappedBy = "livre",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Emprunt emprunt;
}
