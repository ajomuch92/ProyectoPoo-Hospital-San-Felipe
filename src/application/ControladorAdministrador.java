package application;

import java.net.URL;
import java.util.ResourceBundle;

import implementacion.Area;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import utilerias.GestorConexiones;
import utilerias.Mensaje;

public class ControladorAdministrador implements Initializable{
	private Main main;
	private GestorConexiones conexion;

	@FXML private TextField txtArea;
	@FXML private Button btnGuardarArea;
	@FXML private Button btnEliminarArea;
	@FXML private Button btnActualizarArea;
	@FXML private TableView<Area> tblArea;
	@FXML private TableColumn<Area,Number> clmncodigoArea;
	@FXML private TableColumn<Area,String> clmnnombreArea;

	private ObservableList<Area> listaAreas;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listaAreas=FXCollections.observableArrayList();
		conexion=new GestorConexiones();
		tblArea.setItems(listaAreas);
		enlazarColumnas();
		llenarTablas();

		tblArea.getSelectionModel().selectedItemProperty().addListener(
				new  ChangeListener<Area>() {

					@Override
					public void changed(
							ObservableValue<? extends Area> arg0,
							Area valorAnterior,
							Area valorNuevo) {
						if (valorNuevo!=null){
							txtArea.setText(valorNuevo.getNombreArea());
							btnGuardarArea.setDisable(true);
							btnEliminarArea.setDisable(false);
							btnActualizarArea.setDisable(false);
						}

					}

				}
		);

	}

	@FXML
	public void guardarArea(){
		if(txtArea.getText().trim().equals("")){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar el área", "El nombre del área está vacío");
			return;
		}
		int c=1;
		if(listaAreas.size()>0)
			c=listaAreas.get(listaAreas.size()-1).getCodigoArea()+1;
		Area a=new Area(c,txtArea.getText().trim());
		conexion.establecerConexion();
		int r=a.guardar(conexion.getConexion());
		conexion.cerrarConexion();
		if(r>0){
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Guardar area", "Exito al guardar area");
			listaAreas.add(a);
		}
		else
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al intentar guardar el area", "Error al guardar el area");
	}

	@FXML
	public void actualizarArea(){
		if(txtArea.getText().trim().equals("")){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al modificar el área", "El nombre del área está vacío");
			return;
		}
		Area a=new Area(tblArea.getSelectionModel().getSelectedItem().getCodigoArea(),txtArea.getText().trim());
		conexion.establecerConexion();
		int r=a.actualizar(conexion.getConexion());
		conexion.cerrarConexion();
		if(r>0){
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Modificar area", "Exito al modificar area");
			listaAreas.set(tblArea.getSelectionModel().getSelectedIndex(), a);
			limpiarArea();
		}
		else
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al intentar modificar el area", "Error al modificar el area");
	}

	@FXML
	public void eliminarArea(){
		if(tblArea.getSelectionModel().getSelectedItem()==null){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar", "No hay ningún área seleccionada");
			return;
		}
		conexion.establecerConexion();
		System.out.println(tblArea.getSelectionModel().getSelectedItem().getCodigoArea());
		int r=tblArea.getSelectionModel().getSelectedItem().eliminar(conexion.getConexion());
		conexion.cerrarConexion();
		if(r>0){
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Eliminar area", "Exito al eliminar area");
			listaAreas.remove(tblArea.getSelectionModel().getSelectedIndex());
			limpiarArea();
		}
		else
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al intentar eliminar el area", "Error al eliminar el area");
	}

	@FXML
	public void limpiarArea(){
		txtArea.setText("");
		btnGuardarArea.setDisable(false);
		btnEliminarArea.setDisable(true);
		btnActualizarArea.setDisable(true);
		tblArea.getSelectionModel().select(null);
	}


	public void setMain(Main main){
		this.main=main;
	}

	@FXML
	public void volver(){
		main.principal(4);
	}

	@FXML
	public void mostrarMedico(){
		main.cambiarMedico();
	}

	@FXML
	public void mostrarEnfermero(){
		main.cambiarEnfermero();
	}

	public void enlazarColumnas(){
		clmncodigoArea.setCellValueFactory(
				 new PropertyValueFactory<Area,Number>("codigoArea")
				);
				clmnnombreArea.setCellValueFactory(
				 new PropertyValueFactory<Area,String>("nombreArea")
				);
	}

	public void llenarTablas(){
		conexion.establecerConexion();
		Area.llenar(conexion.getConexion(), listaAreas);
		conexion.cerrarConexion();
	}
}
