package com.example.gestionbibliotheque.DAO;

import com.example.gestionbibliotheque.Config.FactoryJPA;
import com.example.gestionbibliotheque.Model.Emprunt;
import com.example.gestionbibliotheque.Model.Livre;

import javax.persistence.EntityManager;
import java.util.List;

public class EmpruntDAO {
    EntityManager em = FactoryJPA.getEntityManager();

    public void addEmprunt(Livre l){
        em.getTransaction().begin();
        em.persist(l);
        em.getTransaction().commit();
    }

    public List<Emprunt> getAllEmprunts(){
        return em.createQuery("SELECT e FROM Emprunt e " +
                        "JOIN FETCH e.etudiant " +//Avec FETCH, il force le chargement dans la première requête parce que LAZY ne charge qu'à l'appelle du get
                        "JOIN FETCH e.livre",
                Emprunt.class
        ).getResultList();
    }
}
