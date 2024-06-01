/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Soporte
 */
public class LoginController implements Initializable {

    Connection conn;

    @FXML
    private TextField txtusuariol;
    @FXML
    private PasswordField txtpassl;
    @FXML
    private Button btningresol;
    @FXML
    private Button btncrearcuental;
    @FXML
    private Button btnsalirl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void click(ActionEvent event) {
        Ingreso();

    }

    public void Ingreso() {
        conn = ConexionDB.getConnection();

        String user = txtusuariol.getText();
        String contra = txtpassl.getText();

        PreparedStatement st = null;
        ResultSet resul = null;

        try {
            String sql = "SELECT carne, contrasena FROM usuario WHERE carne = ? AND contrasena = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, user);
            st.setString(2, contra);

            resul = st.executeQuery();

            if (resul.next()) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/libreriaumg/Pantallainicio.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("BIBLIOTECA UMG");
                stage.show();

                Stage ventanaactual = (Stage) txtusuariol.getScene().getWindow();
                ventanaactual.close();

            } else {

                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error de Autenticación");
                alert.setHeaderText("Error");
                alert.setContentText("Intenta ingresar tus credenciales.");
                alert.showAndWait();

            }

            st.close();
            resul.close();

        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @FXML
    private void clickCC(ActionEvent event) {

    }

    @FXML
    private void clickS(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }
}
