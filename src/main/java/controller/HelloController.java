package controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import ucr.lab.HelloApplication;

import java.io.IOException;

public class HelloController {
    @FXML
    private Text txtMessage;
    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;

    @FXML
    public void initialize() {
        // Inicialización del controlador
        txtMessage.setText("Laboratory No. 11");
    }

    private void load(String form) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(form));
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            showCompactError("Error", "No se pudo cargar la vista: " + form +
                    "\nError: " + e.getMessage());
        }
    }

    @FXML
    public void Home(ActionEvent actionEvent) {
        this.bp.setCenter(ap);
        this.txtMessage.setText("Laboratory No. 11");
    }


    @FXML
    public void matrixGraphView(ActionEvent actionEvent) {

        try {
            load("matrixGraph-view.fxml");
            txtMessage.setText("Cargando Matrix Graph...");
        } catch (Exception e) {
            showCompactError("Error", "Error cargando Matrix Graph: " + e.getMessage());
        }
    }

    @FXML
    public void listGraph(ActionEvent actionEvent) {

        try {
            load("matrixGraph-view.fxml");
            txtMessage.setText("Cargando Matrix Graph...");
        } catch (Exception e) {
            showCompactError("Error", "Error cargando Matrix Graph: " + e.getMessage());
        }
    }

    @FXML
    public void linkedGraph(ActionEvent actionEvent) {

        try {
            load("matrixGraph-view.fxml");
            txtMessage.setText("Cargando Matrix Graph...");
        } catch (Exception e) {
            showCompactError("Error", "Error cargando Matrix Graph: " + e.getMessage());
        }
    }


    @Deprecated
    public void Exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @Deprecated
    public void loadingOnMousePressed(Event event)  {
        this.txtMessage.setText("Estamos cargando tu vista...");
    }

    private void showCompactError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Configurar tamaño compacto
        alert.getDialogPane().setPrefSize(400, 250);
        alert.getDialogPane().setMaxWidth(400);
        alert.getDialogPane().setMaxHeight(250);

        alert.showAndWait();
    }


    @FXML
    public void exit(ActionEvent actionEvent) {
    }

    @FXML
    public void linkedOperations(ActionEvent actionEvent) {
    }

    @FXML
    public void listOperations(ActionEvent actionEvent) {
    }

    @FXML
    public void matrixOperations(ActionEvent actionEvent) {
    }
}