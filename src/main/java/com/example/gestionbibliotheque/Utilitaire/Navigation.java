package com.example.gestionbibliotheque.Utilitaire;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Navigation {
    public static void loadViewrr(String fxml, StackPane stackPane) {
        try {
            Parent view = FXMLLoader.load(
                    Navigation.class.getResource("/com/example/gestionbibliotheque/" + fxml)
            );
            stackPane.getChildren().clear();
            stackPane.getChildren().setAll(view);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadView(String fxml, StackPane stackPane) {
        if (stackPane == null) {
            System.err.println("Erreur : Le StackPane est nul. Vérifiez l'injection @FXML.");
            return;
        }

        try {
            // On charge le nouveau fichier FXML
            FXMLLoader loader = new FXMLLoader(Navigation.class.getResource("/com/example/gestionbibliotheque/" + fxml));
            Parent view = loader.load();

            // 1. On vide proprement le StackPane
            stackPane.getChildren().clear();

            // 2. On ajoute la nouvelle vue
            stackPane.getChildren().add(view);

            // 3. (Optionnel) Forcer la vue à prendre toute la place du StackPane
            if (view instanceof Region region) {
                region.prefWidthProperty().bind(stackPane.widthProperty());
                region.prefHeightProperty().bind(stackPane.heightProperty());
            }

        } catch (IOException e) {
            System.err.println("Erreur de chargement du fichier FXML : " + fxml);
            throw new RuntimeException(e);
        }
    }
}
