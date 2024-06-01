/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.MenuItem;

/**
 * FXML Controller class
 *
 * @author Soporte
 */
public class PantallaInicioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
    }

    @FXML
    private void clickadmin(ActionEvent event) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/libreriaumg/PantallaAdministracion.fxml"));
            Parent root = loader.load();
            Stage stagen = new Stage();
            stagen.setScene(new Scene(root));
            stagen.setTitle("BIBLIOTECA UMG");
            stagen.show();
            
            
            MenuItem menuItem = (MenuItem) event.getSource();
            Stage stage = (Stage) menuItem.getParentPopup().getOwnerWindow();

            stage.close();
            

        } catch (IOException e) {
            Logger.getLogger(PantallaInicioController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

}
