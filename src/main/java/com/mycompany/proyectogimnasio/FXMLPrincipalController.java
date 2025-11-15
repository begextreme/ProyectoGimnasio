/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.proyectogimnasio;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author begex
 */
public class FXMLPrincipalController implements Initializable {
    
@FXML
private ComboBox<String> cbsexo = new ComboBox<>();
private File archivoSeleccionado;
@FXML
private TextField nombreimagen; 
@FXML
private ImageView vistaimagen;
@FXML
private TextField txtnombre;
@FXML
private TextField txtapellido;
@FXML
private TextField txtedad;
@FXML
private DatePicker datefechapago;
@FXML
private TableView<Object[]> tablaclientes;
@FXML
private TextField txtid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        /*Clases.Conexion objetoConexion = new Clases.Conexion();
       objetoConexion.estableceConexion();*/
        
        Clases.Clientes objetoClientes = new Clases.Clientes();
        objetoClientes.MostrarSexoCombo(cbsexo);
        objetoClientes.mostrarClientes(tablaclientes);
        
        txtid.setDisable(true);
        nombreimagen.setDisable(true);
        
    }
    
@FXML
    
private void abrirSelectorArchivos (ActionEvent event){

    FileChooser SelectorArchivos = new FileChooser();
    
    SelectorArchivos.setTitle("Seleccionar Imagen");
    
    SelectorArchivos.getExtensionFilters().addAll(
            
            new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.gif")
    );
    
    archivoSeleccionado =  SelectorArchivos.showOpenDialog(null);
    
    if (archivoSeleccionado!=null){
        
        nombreimagen.setText("Imagen Seleccionada: "+ archivoSeleccionado.getName());
        
        try {
            Image image = new Image(archivoSeleccionado.toURI().toString());
            vistaimagen.setImage(image);
        } catch (Exception e) {
            nombreimagen.setText("Error al cargar imagen");
        }

}
        
else
    {
        nombreimagen.setText("Error de seleccion");
    }
    
    
}
    
@FXML
private void guardarCliente(ActionEvent event){
    
    Clases.Clientes objetoClientes = new Clases.Clientes();
    objetoClientes.agregarClientes(txtnombre, txtapellido, cbsexo, txtedad, datefechapago, archivoSeleccionado);
    tablaclientes.getColumns().clear();
    tablaclientes.getItems().clear();
    objetoClientes.mostrarClientes(tablaclientes);
    objetoClientes.limpiarCampos(txtid, txtnombre, txtapellido, cbsexo, txtedad, datefechapago);

    
}

@FXML
private void seleccionarClientes (MouseEvent event){
    
    Clases.Clientes objetoClientes = new Clases.Clientes();
    objetoClientes.seleccionarClientes(tablaclientes, txtid, txtnombre, txtapellido, cbsexo, txtedad, datefechapago, vistaimagen, archivoSeleccionado);
    
}

@FXML
private void modificarCliente(ActionEvent event){
    
    Clases.Clientes objetoClientes = new Clases.Clientes();
    objetoClientes.modificarClientes(txtid, txtnombre, txtapellido, cbsexo, txtedad, datefechapago, archivoSeleccionado);
    tablaclientes.getColumns().clear();
    tablaclientes.getItems().clear();
    objetoClientes.mostrarClientes(tablaclientes);
    objetoClientes.limpiarCampos(txtid, txtnombre, txtapellido, cbsexo, txtedad, datefechapago);

    
}

@FXML
private void eliminarCliente(ActionEvent event){
    
    Clases.Clientes objetoClientes = new Clases.Clientes();
    objetoClientes.eliminarClientes(txtid);
    tablaclientes.getColumns().clear();
    tablaclientes.getItems().clear();
    objetoClientes.mostrarClientes(tablaclientes);
    objetoClientes.limpiarCampos(txtid, txtnombre, txtapellido, cbsexo, txtedad, datefechapago);
    
}



    
}
