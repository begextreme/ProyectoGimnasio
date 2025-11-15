/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author begex
 */
public class Clientes {
    
    public void MostrarSexoCombo(ComboBox<String> cbSexo){
        
        Clases.Conexion objetoConexion = new Clases.Conexion();
        
        cbSexo.getItems().clear();
        
        cbSexo.setValue("Seleccionar Sexo");
        
        String sql = "select * from sexo;";
        
        try {
            Statement st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {  
                
                int idSexo = rs.getInt("id");
                String nombreSexo = rs.getString("sexo");
                
                cbSexo.getItems().add(nombreSexo);
                
                cbSexo.getProperties().put(nombreSexo, idSexo);
            }
        } catch (Exception e) {
            
            miAlerta("Mensaje", "Error al mostrar sexo"+ e.toString());
            
        }
        
        finally{
            objetoConexion.cerrarConexion();
        }
        
        
    }
    
    public void agregarClientes(TextField nombre, TextField apellido, ComboBox<String> cbSexo, TextField edad, DatePicker fechapago, File foto){
        
        Conexion objetoConexion = new Conexion();
        
        String consulta= "insert into clientes (nombre,apellido,fksexo,edad,fechapago,foto) values (?,?,?,?,?,?);";
        
        
        try(FileInputStream fis = new FileInputStream(foto);
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta)){
            
            cs.setString(1, nombre.getText());
            cs.setString(2, apellido.getText());
            
            String sexoSeleccion = cbSexo.getSelectionModel().getSelectedItem();
            
            int idSexo = (int) cbSexo.getProperties().get(sexoSeleccion);
            
            cs.setInt(3, idSexo);
            
            cs.setInt(4, Integer.parseInt(edad.getText()));
            
            LocalDate fechaElejida = fechapago.getValue();
            Date fechaSql = Date.valueOf(fechaElejida);
            cs.setDate(5, fechaSql);
            
            cs.setBinaryStream(6, fis,(int) foto.length());
            
            cs.execute();
            
            miAlerta("Info", "Guardado correcto");
            
            
            
        } catch (Exception e) {
            
            miAlerta("Info", "Erro en el guardado: "+ e.toString());
            
        } finally {
            
            objetoConexion.cerrarConexion();
            
        }
    }
    
    public void mostrarClientes(TableView<Object[]> tablaClientes){
        
        Clases.Conexion objetoConexion = new Clases.Conexion();
        
        TableColumn<Object[],String> idColumna = new TableColumn<>("Id");
        TableColumn<Object[],String> nombreColumna = new TableColumn<>("Nombre");        
        TableColumn<Object[],String> apellidoColumna = new TableColumn<>("Apellido");       
        TableColumn<Object[],String> sexoColumna = new TableColumn<>("Sexo");
        TableColumn<Object[],String> edadColumna = new TableColumn<>("Edad");
        TableColumn<Object[],String> fechapagoColumna = new TableColumn<>("Fecha de Pago");
        TableColumn<Object[],String> fotoColumna = new TableColumn<>("Foto");
        
        idColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0].toString()));
        nombreColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1].toString()));
        apellidoColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2].toString()));
        sexoColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3].toString()));
        edadColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4].toString()));
        fechapagoColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[5].toString()));
        fotoColumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[6].toString()));
        
        tablaClientes.getColumns().addAll(idColumna, nombreColumna, apellidoColumna, sexoColumna, edadColumna, fechapagoColumna, fotoColumna);
        
        String sql = "select clientes.id, clientes.nombre, clientes.apellido, sexo.sexo, clientes.edad, clientes.fechapago, clientes.foto from clientes inner join sexo on clientes.fksexo = sexo.id;";
        
        try {
            
            Statement st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            while (rs.next()) {      
                
                java.sql.Date fechaSql = rs.getDate("fechapago");
                
                String fecha = (fechaSql != null) ? sdf.format(fechaSql) : null;
                
                byte[] imageBytes = rs.getBytes("foto");
                
                Image foto = null;
                
                if(imageBytes != null) {
                    
                    try {
                        
                        foto = new Image(new ByteArrayInputStream(imageBytes));
                        
                    } catch (Exception e) {
                        
                        miAlerta("Error", "Error al cargar imagen"+ e.toString());
                        
                    }
                }
                
                Object[] rowData = {
                    
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("sexo"),
                    rs.getString("edad"),
                    fecha,
                    foto,
                    
                };
                
                tablaClientes.getItems().add(rowData);
                
            }
            
        } catch (Exception e) {
            
            miAlerta("Error", "Error al guardar"+ e.toString());
            
        } finally {
            
            objetoConexion.cerrarConexion();
            
        }
        
    }
    
    public void seleccionarClientes(TableView<Object[]> tablaClientes, TextField id, TextField nombre, TextField apellido, ComboBox<String> cbSexo, TextField edad, DatePicker fechapago, ImageView vistaImagen, File foto){
        
        int fila = tablaClientes.getSelectionModel().getSelectedIndex();
        
        if (fila>=0) {
            
            Object[] filaSeleccionada = tablaClientes.getItems().get(fila);
            
            id.setText(filaSeleccionada[0].toString());
            nombre.setText(filaSeleccionada[1].toString());
            apellido.setText(filaSeleccionada[2].toString());
            
            cbSexo.getSelectionModel().select(filaSeleccionada[3].toString());
            
            edad.setText(filaSeleccionada[4].toString());
            
            String fechaString = filaSeleccionada[5].toString();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaLocal = LocalDate.parse(fechaString, formatter);
            fechapago.setValue(fechaLocal);
            
            Image imagen = (Image) filaSeleccionada[6];
            vistaImagen.setImage(imagen);
            
        }
        
    }
    
    public void modificarClientes(TextField id, TextField nombre, TextField apellido, ComboBox<String> cbSexo, TextField edad, DatePicker fechapago, File foto){
        
        Conexion objetoConexion = new Conexion();
        
        String consulta = "update clientes Set clientes.nombre=?, clientes.apellido=?, clientes.fksexo=?, clientes.edad=?, clientes.fechapago=?, clientes.foto=? where clientes.id=?";
        
        try {
            
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, nombre.getText());
            cs.setString(2, apellido.getText());
        
            String sexoSeleccionado = cbSexo.getSelectionModel().getSelectedItem();
            
            int idSexo = (int) cbSexo.getProperties().get(sexoSeleccionado);
            
            cs.setInt(3, idSexo);
            
            cs.setInt(4, Integer.parseInt(edad.getText()));
            
            LocalDate fechaSeleccionada = fechapago.getValue();
            Date fechaSql = Date.valueOf(fechaSeleccionada);
            cs.setDate(5, fechaSql);
            
            /*validacion para modificar varias cosas ademas de la foto*/
            
            if(foto != null){
                
                FileInputStream fis = new FileInputStream(foto);
                cs.setBinaryStream(6, fis,(int) foto.length());
                
            }
            
            else{
                
                String obtenerImagenActual = "select foto from clientes where id =?";
                
                try(PreparedStatement obtenerImagen = objetoConexion.estableceConexion().prepareStatement(obtenerImagenActual)) {
                    
                    obtenerImagen.setInt(1, Integer.parseInt(id.getText()));
                    ResultSet rs = obtenerImagen.executeQuery();
                    
                    if(rs.next()){
                        
                        Blob blob =rs.getBlob("foto");
                        
                        if(blob != null){
                            
                            cs.setBlob(6, blob);
                            
                        }
                        
                        else{
                            
                            cs.setNull(6, Types.BLOB);
                            
                        }
                        
                    }
                    
                } 
                
            }
            
            cs.setInt(7, Integer.parseInt(id.getText()));
            cs.execute();
            
            miAlerta("Info", "Modificado");
            
            
        } catch (Exception e) {
            
            miAlerta("Info", "No se pudo modificar,"+ e.toString());
            
        } finally {
            
            objetoConexion.cerrarConexion();
            
        }
        
        
    }
    
    
    public void eliminarClientes(TextField id){
        
        Conexion objetoConexion = new Conexion();
        
        String consulta = "delete from clientes where clientes.id=?";
        
        try {
            
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            
            cs.setInt(1, Integer.parseInt(id.getText()));
            
            cs.execute();
            
            miAlerta("Mensaje", "Se elimino el cliente");
            
        } catch (Exception e) {
            
            miAlerta("Error", "No se puedo eliminar el cliente: "+ e.toString());
            
        } finally {
            
            objetoConexion.cerrarConexion();
            
        }
        
    }
    
    public void limpiarCampos(TextField id, TextField nombre, TextField apellido, ComboBox<String> cbSexo, TextField edad, DatePicker fechapago){
        
        id.setText("");
        nombre.setText("");
        apellido.setText("");
        edad.setText("");
        
        fechapago.setValue(LocalDate.now());

        
    }
    
    private void miAlerta(String title, String content){
        Alert alert = new Alert (Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
}
