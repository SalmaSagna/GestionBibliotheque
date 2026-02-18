package com.example.gestionbibliotheque.DAO;

import com.example.gestionbibliotheque.Config.FactoryJPA;
import com.example.gestionbibliotheque.Model.Etudiant;
import com.example.gestionbibliotheque.Model.Livre;

import javax.persistence.EntityManager;
import java.util.List;

public class LivreDAO {
    EntityManager em = FactoryJPA.getEntityManager();

    public void addBook(Livre l){
        em.getTransaction().begin();
        em.persist(l);
        em.getTransaction().commit();
    }

    public List<Livre> getAllBooks(){
        return em.createQuery("FROM Livre",Livre.class).getResultList();
    }
}
