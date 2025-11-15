/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import javafx.scene.control.Alert;
/**
 *
 * @author begex
 */
public class Conexion {
    
    Connection conectar = null;
    
    String usuario = "root";
    String contrasenia = "root";
    String bd = "gymdb";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    public Connection estableceConexion(){
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            
            conectar = DriverManager.getConnection(cadena, usuario, contrasenia);
            
            //miAlerta("Mensaje", "Base de datos conectada");
            
        } catch (Exception e) {
            
            miAlerta("Mensaje", "No se conecto a la base de datos"+e.toString());
            
        }
        
        return conectar;
       
    }
    
    
    private void miAlerta(String title, String content){
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    
    public void cerrarConexion(){
        
        try {
            
            if (conectar!= null && !conectar.isClosed()) {
                
                conectar.close();
                
                //miAlerta("Mensaje", "Conexion cerrada");
                
            }
            
        } catch (Exception e) {
            
            miAlerta("Mensaje", "Error al cerrar conexion"+e.toString());
            
        }
        
        
    }
    
}
