package com.mycompany.proyectogimnasio;

import Clases.UsuarioLogIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    private UsuarioLogIn usuariologin = new UsuarioLogIn();

    @FXML
    private void onLogin(ActionEvent event) {

        String usuario = txtUsuario.getText();
        String contraseña = txtPassword.getText();

        if (usuario.isEmpty() || contraseña.isEmpty()) {
           
            miAlerta("Info", "Ingresa Usuario y Contraseña");
            
        }

        boolean valido = usuariologin.validarLogin(usuario, contraseña);

        if (valido) {
          
            miAlerta("Info", "Datos ingresados correctamente");
            
            try {
                
                App.setRoot("FXMLPrincipal");
                
            } catch (Exception e) {
                
                miAlerta("Error", "Error al abrir ventana: "+ e.toString());
                
            }
            
        } else {
            
            miAlerta("Error", "Usuario y Contraseña Incorrectos");
            
        }
    }
    
    private void miAlerta(String title, String content){
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
}


