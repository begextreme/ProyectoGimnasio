/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;

public class UsuarioLogIn {

    public boolean validarLogin(String usuario, String contraseña) {

        Clases.Conexion objetoConexion = new Clases.Conexion();
        Connection conexion = objetoConexion.estableceConexion();

        if (conexion == null) {
            System.out.println("No se pudo establecer conexión con la Base de Datos.");
            return false;
        }

        String sql = "select * from usuarios where username = ? and password = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, contraseña);

            ResultSet rs = stmt.executeQuery();

            boolean existe = rs.next();

            rs.close();
            conexion.close();

            return existe;

        } catch (SQLException e) {
            miAlerta("Error", "Error en login: " + e.toString());
            return false;
        } finally {
            objetoConexion.cerrarConexion();
        }

    } 
    
    private void miAlerta(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

} 
