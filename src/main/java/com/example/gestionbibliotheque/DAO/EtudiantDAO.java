package com.example.gestionbibliotheque.DAO;

import com.example.gestionbibliotheque.Config.FactoryJPA;
import com.example.gestionbibliotheque.Model.Etudiant;

import javax.persistence.EntityManager;
import java.util.List;

public class EtudiantDAO {
    EntityManager em = FactoryJPA.getEntityManager();

    public void addStudent(Etudiant e){
        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();
    }

    public Etudiant getStudentById(int id){
        return em.find(Etudiant.class,id);
    }

    public void updateStudent(Etudiant e){
        em.getTransaction().begin();
        em.merge(e);
        em.getTransaction().commit();
    }

    public void deleteStudent(Etudiant e){
        em.getTransaction().begin();
        Etudiant et = em.merge(e);
        em.remove(et);
        em.getTransaction().commit();
    }

    public List<Etudiant> getAllStudents(){
        return em.createQuery("FROM Etudiant",Etudiant.class).getResultList();
    }
}
