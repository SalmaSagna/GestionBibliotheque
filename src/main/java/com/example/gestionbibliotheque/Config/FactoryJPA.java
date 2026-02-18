package com.example.gestionbibliotheque.Config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FactoryJPA {
    public static EntityManagerFactory emf;
    public FactoryJPA(){};

    public static EntityManagerFactory getEmf(){
        if(emf==null) emf = Persistence.createEntityManagerFactory("GestionBibliotheque");
        return emf;
    }

    public static EntityManager getEntityManager(){
        return getEmf().createEntityManager();
    }
}
