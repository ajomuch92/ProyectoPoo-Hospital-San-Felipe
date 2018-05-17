package application;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;
import implementacion.Cita;
import implementacion.Medico;
import implementacion.Paciente;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import utilerias.GestorConexiones;
import utilerias.Mensaje;

public class ControladorCita implements Initializable {
	private Main main;
	private Paciente paciente;
	private GestorConexiones conexion;

	@FXML private TableView<Cita> tblCita;
	@FXML private TableColumn<Cita,Number> clmncodigoCita;
	@FXML private TableColumn<Cita,String> clmnhora;
	@FXML private TableColumn<Cita,Date> clmnfecha;
	@FXML private TableColumn<Cita,Paciente> clmnpaciente;
	@FXML private TableColumn<Cita,Medico> clmnmedico;
	@FXML private TableColumn<Cita,String> clmnestado;
	@FXML private ComboBox<Paciente> cboPacientes;
	@FXML private ComboBox<Medico> cboMedico;
	@FXML private DatePicker dtpFecha;
	@FXML private TextField txtHora;
	@FXML private Button btnGuardar;
	@FXML private Button btnActualizar;
	@FXML private Button btnEliminar;

	private ObservableList<Cita> listaCitas;
	private ObservableList<Paciente> listaPacientes;
	private ObservableList<Medico> listaMedicos;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		conexion=new GestorConexiones();
		conexion.establecerConexion();
		enlazarColumnas();
		listaCitas=FXCollections.observableArrayList();
		listaPacientes=FXCollections.observableArrayList();
		listaMedicos=FXCollections.observableArrayList();
		Cita.llenar(conexion.getConexion(), listaCitas,0);
		Paciente.llenar(conexion.getConexion(), listaPacientes);
		Medico.Llenar(listaMedicos, conexion.getConexion());
		tblCita.setItems(listaCitas);
		cboPacientes.setItems(listaPacientes);
		cboMedico.setItems(listaMedicos);
		agregarListeners();
		conexion.cerrarConexion();
	}

	public void setMain(Main main){
		this.main=main;
	}

	public void agregarListeners(){
		tblCita.getSelectionModel().selectedItemProperty().addListener(
				new  ChangeListener<Cita>() {

					@Override
					public void changed(
							ObservableValue<? extends Cita> arg0,
							Cita valorAnterior,
							Cita valorNuevo)
					{
						if (valorNuevo!=null && valorNuevo.getEstado().trim().equals("PENDIENTE")){
							txtHora.setText(valorNuevo.getHora());
							cboMedico.getSelectionModel().select(valorNuevo.getMedico());
							cboPacientes.getSelectionModel().select(valorNuevo.getPaciente());
							dtpFecha.setValue(valorNuevo.getFecha().toLocalDate());
							btnGuardar.setDisable(true);
							btnEliminar.setDisable(false);
							btnActualizar.setDisable(false);

						}
						else{
							btnGuardar.setDisable(false);
							btnEliminar.setDisable(true);
							btnActualizar.setDisable(true);
						}

					}

				}
		);
	}

	public void enlazarColumnas(){
		clmncodigoCita.setCellValueFactory(
			 new PropertyValueFactory<Cita,Number>("codigoCita")
		);
		clmnhora.setCellValueFactory(
			 new PropertyValueFactory<Cita,String>("hora")
		);
		clmnfecha.setCellValueFactory(
			 new PropertyValueFactory<Cita,Date>("fecha")
		);
		clmnpaciente.setCellValueFactory(
			 new PropertyValueFactory<Cita,Paciente>("paciente")
		);
		clmnmedico.setCellValueFactory(
			 new PropertyValueFactory<Cita,Medico>("medico")
		);
		clmnestado.setCellValueFactory(
			 new PropertyValueFactory<Cita,String>("estado")
		);
	}

	public void setPaciente(Paciente paciente){
		this.paciente=paciente;
		if(this.paciente!=null){
			cboPacientes.getSelectionModel().select(paciente);
		}
	}

	@FXML
	public void regresar(){
		limpiar();
		main.principal(6);
	}

	@FXML
	public void limpiar(){
		cboPacientes.getSelectionModel().select(null);
		cboMedico.getSelectionModel().select(null);
		tblCita.getSelectionModel().select(null);
		btnGuardar.setDisable(false);
		btnActualizar.setDisable(true);
		btnEliminar.setDisable(true);
		dtpFecha.setValue(null);
		txtHora.setText("");
		cboPacientes.requestFocus();
	}

	@FXML
	public void guardar(){
		ArrayList<String> errores=validar();
		if(errores.size()>0){
			String c="";
			for(int i=0;i<errores.size();i++)
				c+=errores.get(i)+"\n";
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar", c);
			return;
		}
		conexion.establecerConexion();
		int n=Cita.obtenerUltimo(conexion.getConexion())+1;
		Cita ci=new Cita(n,txtHora.getText().trim(),
				Date.valueOf(dtpFecha.getValue()),
				cboPacientes.getSelectionModel().getSelectedItem(),
				cboMedico.getSelectionModel().getSelectedItem(),
				"PENDIENTE"
				);
		int r=ci.guardar(conexion.getConexion());
		if(r>0){
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Guardar cita", "Exito al guardar la cita");
			listaCitas.add(ci);
			limpiar();
		}
		else
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Guardar la cita", "Error al guardar la cita");
		conexion.cerrarConexion();
	}

	@FXML
	public void actualizar(){
		ArrayList<String> errores=validar();
		if(errores.size()>0){
			String c="";
			for(int i=0;i<errores.size();i++)
				c+=errores.get(i)+"\n";
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar", c);
			return;
		}
		conexion.establecerConexion();
		int n=tblCita.getSelectionModel().getSelectedItem().getCodigoCita();
		Cita ci=new Cita(n,txtHora.getText().trim(),
				Date.valueOf(dtpFecha.getValue()),
				cboPacientes.getSelectionModel().getSelectedItem(),
				cboMedico.getSelectionModel().getSelectedItem(),
				"PENDIENTE"
				);
		int r=ci.actualizar(conexion.getConexion());
		if(r>0){
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Actualizar cita", "Exito al actualizar la cita");
			listaCitas.set(tblCita.getSelectionModel().getSelectedIndex(), ci);
			limpiar();
		}
		else
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Actualizar la cita", "Error al actualizar la cita");
		conexion.cerrarConexion();
	}

	@FXML
	public void eliminar(){
		if(tblCita.getSelectionModel().getSelectedItem()==null){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar", "No hay ninguna cita seleccionada");
			return;
		}
		conexion.establecerConexion();
		int r=tblCita.getSelectionModel().getSelectedItem().eliminar(conexion.getConexion());
		if(r>0){
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Guardar cita", "Exito al guardar la cita");
			listaCitas.remove(tblCita.getSelectionModel().getSelectedIndex());
			limpiar();
		}
		else
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Guardar la cita", "Error al guardar la cita");
		conexion.cerrarConexion();
	}

	public ArrayList<String> validar(){
		ArrayList<String> errores=new ArrayList<>();
		if(cboPacientes.getSelectionModel().getSelectedItem()==null)
			errores.add("El nombre del paciente está vacío");
		if(cboMedico.getSelectionModel().getSelectedItem()==null)
			errores.add("El nombre del médico está vacío");
		if(dtpFecha.getValue()==null)
			errores.add("La fecha está vacía");
		if(txtHora.getText().trim().equals(""))
			errores.add("La hora de la cita está vacía");
		return errores;
	}

}
