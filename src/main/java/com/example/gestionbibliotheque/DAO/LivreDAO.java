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

    public void updateBook(Livre l){
        em.getTransaction().begin();
        em.merge(l);
        em.getTransaction().commit();
    }

    public List<Livre> getDisponibleBooks(){
        return em.createQuery("SELECT l FROM Livre l "+
                        "WHERE l.id NOT IN (SELECT e.livre.id FROM Emprunt e)",Livre.class)
                .getResultList();
    }

    public List<Livre> getNonDisponibleBooks(){
        return em.createQuery("SELECT l FROM Livre l "+
                        "WHERE l.id IN (SELECT e.livre.id FROM Emprunt e)",Livre.class)
                .getResultList();
    }
}
