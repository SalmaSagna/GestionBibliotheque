package com.example.gestionbibliotheque.Controller;

import com.example.gestionbibliotheque.DAO.EmpruntDAO;
import com.example.gestionbibliotheque.DAO.EtudiantDAO;
import com.example.gestionbibliotheque.Model.Etudiant;
import com.example.gestionbibliotheque.Utilitaire.Navigation;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.util.List;

public class EtudiantController {
    EtudiantDAO dbEtudiant = new EtudiantDAO();

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
    private void initialize(){
        if(tableEtudiant!=null) {
            List<Etudiant> etudiants = dbEtudiant.getAllStudents();
            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colMatricule.setCellValueFactory(new PropertyValueFactory<>("matricule"));
            colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            tableEtudiant.setItems(FXCollections.observableList(etudiants));
        }
    }

    @FXML
    private void addStudent(){
        if (txtMatricule!=null && txtPrenom!=null && txtNom!=null && txtEmail!=null){
            Etudiant e = new Etudiant();
            e.setMatricule(txtMatricule.getText());
            e.setPrenom(txtPrenom.getText());
            e.setNom(txtNom.getText());
            e.setEmail(txtEmail.getText());
            dbEtudiant.addStudent(e);
            txtMatricule.clear();
            txtPrenom.clear();
            txtNom.clear();
            txtEmail.clear();
            initialize();
        }
        else {
            message.setText("Remplir tout les champs");
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
            e.setMatricule(txtMatricule.getText());
            e.setPrenom(txtPrenom.getText());
            e.setNom(txtNom.getText());
            e.setEmail(txtEmail.getText());
            dbEtudiant.updateStudent(e);
            initialize();
        }
    }

    @FXML
    private void deleteStudentButton(){
        e = tableEtudiant.getSelectionModel().getSelectedItem();
        if (e!=null){
            dbEtudiant.deleteStudent(e);
            initialize();
        }
        else message.setText("Selectionner l'etudiant d'abord");
    }
}
