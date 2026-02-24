package com.example.gestionbibliotheque.Controller;

import com.example.gestionbibliotheque.DAO.EmpruntDAO;
import com.example.gestionbibliotheque.DAO.EtudiantDAO;
import com.example.gestionbibliotheque.Model.Etudiant;
import com.example.gestionbibliotheque.Utilitaire.Navigation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.util.List;

public class EtudiantController {
    EtudiantDAO dbEtudiant = new EtudiantDAO();

    EmpruntDAO dbEmprunt = new EmpruntDAO();

    @FXML
    private TableView<Etudiant> tableEtudiant;

    @FXML
    private TableColumn<Etudiant,Integer> colId;

    @FXML
    private TableColumn<Etudiant,String> colMatricule;

    @FXML
    private TableColumn<Etudiant,String> colPrenom;

    @FXML
    private TableColumn<Etudiant,String> colNom;

    @FXML
    private TableColumn<Etudiant,String> colEmail;

    @FXML
    private StackPane stackPane;

    @FXML
    private TextField txtMatricule;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label message;

    private Etudiant e;

    @FXML
    private ComboBox<Etudiant> comboEtudiant;

    @FXML
    ObservableList<Etudiant> etudiants;


    @FXML
    private void initialize(){
        if(tableEtudiant!=null) {
            etudiants = FXCollections.observableList(dbEtudiant.getAllStudents());
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colMatricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
            colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tableEtudiant.setItems(etudiants);
        }
    }

    private void refreshTable(){
        etudiants.clear();
        etudiants.addAll(dbEtudiant.getAllStudents());
        tableEtudiant.setItems(etudiants);
    }

    private void clearInput(){
        txtMatricule.clear();
        txtPrenom.clear();
        txtNom.clear();
        txtEmail.clear();
    }

    @FXML
    private void addStudent(){
        if (!txtMatricule.getText().isBlank() &&
                !txtPrenom.getText().isBlank() &&
                !txtNom.getText().isBlank() &&
                !txtEmail.getText().isBlank()){
            if(txtEmail.getText().matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")) {
                Etudiant e = new Etudiant();
                e.setMatricule(txtMatricule.getText());
                e.setPrenom(txtPrenom.getText());
                e.setNom(txtNom.getText());
                e.setEmail(txtEmail.getText());
                dbEtudiant.addStudent(e);
                refreshTable();
                clearInput();
            }
            else {
                String titre = "Attention";
                String message = "Email invalide";
                Alert.AlertType type = Alert.AlertType.WARNING;
                showAlert(titre,message,type);
            }
        }
        else {
            String titre = "Attention";
            String message = "Remplir tout les champs d'abord d'abord";
            Alert.AlertType type = Alert.AlertType.WARNING;
            showAlert(titre,message,type);
        }
    }

    @FXML
    private void getselectedStudent(){
        e = tableEtudiant.getSelectionModel().getSelectedItem();
        if (e!=null){
            txtMatricule.setText(e.getMatricule());
            txtPrenom.setText(e.getPrenom());
            txtNom.setText(e.getNom());
            txtEmail.setText(e.getEmail());
        }
    }

    @FXML
    private void updateStudent(){
        if (e!=null){
            if (!txtMatricule.getText().isBlank() && !txtPrenom.getText().isBlank() && !txtNom.getText().isBlank() && !txtEmail.getText().isBlank()) {
                e.setMatricule(txtMatricule.getText());
                e.setPrenom(txtPrenom.getText());
                e.setNom(txtNom.getText());
                e.setEmail(txtEmail.getText());
                dbEtudiant.updateStudent(e);
                refreshTable();
                clearInput();
                e = null;
            }
            else {
                String titre = "Attention";
                String message = "Remplir tout les champs d'abord d'abord";
                Alert.AlertType type = Alert.AlertType.WARNING;
                showAlert(titre,message,type);
            }
        }
        else {
            String titre = "Attention";
            String message = "Selectionner l'etudiant d'abord";
            Alert.AlertType type = Alert.AlertType.WARNING;
            showAlert(titre,message,type);
        }
    }

    @FXML
    private void deleteStudentButton(){
        e = tableEtudiant.getSelectionModel().getSelectedItem();
        if (e!=null){
            if (dbEmprunt.getEmpruntsByStudent(e.getId()).isEmpty()) {
                dbEtudiant.deleteStudent(e);
                refreshTable();
                clearInput();
                e = null;
            }
            else {
                String titre = "Attention";
                String message = "Impossible, cet etudiant a deja effectué un emprunt";
                Alert.AlertType type = Alert.AlertType.WARNING;
                showAlert(titre,message,type);
            }
        }
        else {
            String titre = "Attention";
            String message = "Selectionner l'etudiant d'abord";
            Alert.AlertType type = Alert.AlertType.WARNING;
            showAlert(titre,message,type);
        }
    }

    private void showAlert(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);//type peut etre WARNING ou INFORMATION ou ERROR
        alert.setTitle(titre);
        alert.setHeaderText(null); // On enlève l'entête
        alert.setContentText(message);
        alert.showAndWait();
    }
}
