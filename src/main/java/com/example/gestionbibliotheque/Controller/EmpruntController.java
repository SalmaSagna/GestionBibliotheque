package com.example.gestionbibliotheque.Controller;

import com.example.gestionbibliotheque.DAO.EmpruntDAO;
import com.example.gestionbibliotheque.Model.Emprunt;
import com.example.gestionbibliotheque.Model.Etudiant;
import com.example.gestionbibliotheque.Utilitaire.Navigation;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.util.List;

public class EmpruntController {
    EmpruntDAO dbEmprunt = new EmpruntDAO();

    @FXML
    private TableView<Emprunt> tableEmprunt;

    @FXML
    private TableColumn<Emprunt,Integer> colId;

    @FXML
    private TableColumn<Emprunt,String> colISBN;

    @FXML
    private TableColumn<Emprunt,String> colTitre;

    @FXML
    private TableColumn<Emprunt,String> colAuteur;

    @FXML
    private TableColumn<Emprunt,String> colEtudiant;

    @FXML
    private TableColumn<Emprunt,Integer> colDateEmprunt;

    @FXML
    private TableColumn<Emprunt,Integer> colDateRetour;

    @FXML
    private StackPane stackPane;

    @FXML
    private void initialize(){
        if(tableEmprunt!=null){
            List<Emprunt> emprunts = dbEmprunt.getAllEmprunts();
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colISBN.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getLivre().getIsbn()));
            colTitre.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getLivre().getTitre()));
            colAuteur.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getLivre().getAuteur()));
            colEtudiant.setCellValueFactory(cellData -> {
                Etudiant e = cellData.getValue().getEtudiant();
                return new SimpleStringProperty(e.getPrenom() + " " + e.getNom());
            });
            colDateEmprunt.setCellValueFactory(new PropertyValueFactory<>("dateEmprunt"));
            colDateRetour.setCellValueFactory(new PropertyValueFactory<>("dateRetour"));
            tableEmprunt.setItems(FXCollections.observableList(emprunts));
        }
    }
}
