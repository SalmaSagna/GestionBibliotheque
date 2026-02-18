package com.example.gestionbibliotheque;

import com.example.gestionbibliotheque.Utilitaire.Navigation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private StackPane stackPane;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void showListEtudiant(){
        Navigation.loadView("listEtudiant.fxml",stackPane);
    }

    @FXML
    public void showListLivre(){
        Navigation.loadView("listLivre.fxml",stackPane);
    }

    @FXML
    public void showListEmprunt(){
        Navigation.loadView("listEmprunt.fxml",stackPane);
    }
}