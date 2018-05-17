package application;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import implementacion.Cita;
import implementacion.Empleado;
import implementacion.Enfermero;
import implementacion.Preclinica;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import utilerias.GestorConexiones;
import utilerias.Mensaje;

public class ControladorPreclinica implements Initializable {
	private Main main;
	private GestorConexiones conexion;
	private Empleado empleado;

	@FXML private Button btnGuardar;
	@FXML private Button btnActualizar;
	@FXML private Button btnEliminar;

	@FXML private Label lblCita;
	@FXML private Label lblPaciente;
	@FXML private Label lblMedico;
	@FXML private Label lblHora;
	@FXML private Label lblFecha;

	@FXML private TextField txtTemperatura;
	@FXML private TextField txtPeso;
	@FXML private TextField txtAltura;
	@FXML private TextField txtPresion;

	@FXML private ComboBox<Enfermero> cboEnfermero;
	@FXML private ListView<Cita> lvwCitas;

	@FXML private TableView<Preclinica> tblPreclinica;

	@FXML private TableColumn<Preclinica,Cita> clmncita;
	@FXML private TableColumn<Preclinica,Enfermero> clmnenfermero;
	@FXML private TableColumn<Preclinica,Double> clmntemperatura;
	@FXML private TableColumn<Preclinica,Double> clmnpeso;
	@FXML private TableColumn<Preclinica,Double> clmnaltura;
	@FXML private TableColumn<Preclinica,String> clmnpresion;

	private ObservableList<Cita> listaCitas;
	private ObservableList<Preclinica> listaPreclinica;
	private ObservableList<Enfermero> listaEnfermeros;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		conexion=new GestorConexiones();
		listaCitas=FXCollections.observableArrayList();
		listaPreclinica=FXCollections.observableArrayList();
		listaEnfermeros=FXCollections.observableArrayList();
		conexion.establecerConexion();
		enlazarColumnas();
		agregarListener();
		Cita.llenar(conexion.getConexion(), listaCitas, 1);
		Enfermero.llenar(listaEnfermeros, conexion.getConexion());
		lvwCitas.setItems(listaCitas);
		tblPreclinica.setItems(listaPreclinica);
		cboEnfermero.setItems(listaEnfermeros);
		conexion.cerrarConexion();

	}

	public void setMain(Main main, Empleado empleado){
		this.main=main;
		this.empleado=empleado;
		Preclinica pre=new Preclinica();
		conexion.establecerConexion();
		pre.llenar(conexion.getConexion(), listaPreclinica);
		conexion.cerrarConexion();
	}

	public void enlazarColumnas(){
		clmncita.setCellValueFactory(
			 new PropertyValueFactory<Preclinica,Cita>("cita")
		);
		clmnenfermero.setCellValueFactory(
			 new PropertyValueFactory<Preclinica,Enfermero>("enfermero")
		);
		clmntemperatura.setCellValueFactory(
			 new PropertyValueFactory<Preclinica,Double>("temperatura")
		);
		clmnpeso.setCellValueFactory(
			 new PropertyValueFactory<Preclinica,Double>("peso")
		);
		clmnaltura.setCellValueFactory(
			 new PropertyValueFactory<Preclinica,Double>("altura")
		);
		clmnpresion.setCellValueFactory(
			 new PropertyValueFactory<Preclinica,String>("presion")
		);
	}

	public void agregarListener(){
		tblPreclinica.getSelectionModel().selectedItemProperty().addListener(
				new  ChangeListener<Preclinica>() {

					@Override
					public void changed(
							ObservableValue<? extends Preclinica> arg0,
							Preclinica valorAnterior,
							Preclinica valorNuevo) {
						if (valorNuevo!=null){
							lblCita.setText(String.valueOf(valorNuevo.getCita().getCodigoCita()));
							lblPaciente.setText(valorNuevo.getCita().getPaciente().toString());
							lblMedico.setText(valorNuevo.getCita().getMedico().toString());
							lblHora.setText(valorNuevo.getCita().getHora());
							lblFecha.setText(valorNuevo.getCita().getFecha().toString());
							txtTemperatura.setText(String.valueOf(valorNuevo.getTemperatura()));
							txtPeso.setText(String.valueOf(valorNuevo.getPeso()));
							txtAltura.setText(String.valueOf(valorNuevo.getAltura()));
							txtPresion.setText(valorNuevo.getPresion());
							cboEnfermero.getSelectionModel().select(valorNuevo.getEnfermero());
							if(valorNuevo.getCita().getFecha().toLocalDate().equals(LocalDate.now())){
								btnGuardar.setDisable(true);
								btnEliminar.setDisable(false);
								btnActualizar.setDisable(false);
							}
						}
					}
				}
		);
		lvwCitas.getSelectionModel().selectedItemProperty().addListener(
				new  ChangeListener<Cita>() {

					@Override
					public void changed(
							ObservableValue<? extends Cita> arg0,
							Cita valorAnterior,
							Cita valorNuevo) {
						if (valorNuevo!=null){
							lblCita.setText(String.valueOf(valorNuevo.getCodigoCita()));
							lblPaciente.setText(valorNuevo.getPaciente().toString());
							lblMedico.setText(valorNuevo.getMedico().toString());
							lblHora.setText(valorNuevo.getHora());
							lblFecha.setText(valorNuevo.getFecha().toString());
							btnGuardar.setDisable(false);
						}

					}

				}
		);

	}

	@FXML
	public void limpiar(){
		lvwCitas.getSelectionModel().select(null);
		tblPreclinica.getSelectionModel().select(null);
		lblCita.setText("");
		lblPaciente.setText("");
		lblCita.setText("");
		lblPaciente.setText("");
		lblMedico.setText("");
		lblHora.setText("");
		lblFecha.setText("");
		txtTemperatura.setText("");
		txtPeso.setText("");
		txtAltura.setText("");
		txtPresion.setText("");
		cboEnfermero.getSelectionModel().select(null);
		btnGuardar.setDisable(true);
		btnEliminar.setDisable(true);
		btnActualizar.setDisable(true);
	}

	@FXML
	public void volver(){
		main.principal(2);
	}

	@FXML
	public void guardar(){
		ArrayList<String> errores=validar();
		String c = "";
		if(errores.size()>0){
			for(int i=0;i<errores.size();i++)
				c+=errores.get(i)+"\n";
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al intentar guardar preclinica", c);
			return;
		}
		conexion.establecerConexion();
		Preclinica p=new Preclinica(
				lvwCitas.getSelectionModel().getSelectedItem(),
				cboEnfermero.getSelectionModel().getSelectedItem(),
				Double.valueOf(txtTemperatura.getText()),
				Double.valueOf(txtPeso.getText()),
				Double.valueOf(txtAltura.getText()),
				txtPresion.getText()
				);
		int r=p.guardar(conexion.getConexion());
		if(r>0){
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Guardar preclinica", "Exito al guardar el preclinica");
			Cita.cambiarEstado(conexion.getConexion(), p.getCita().getCodigoCita());
			listaCitas.clear();
			Cita.llenar(conexion.getConexion(), listaCitas, 1);
			listaPreclinica.add(p);
		}
		else
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al intentar guardar preclinica", "Error al guardar preclinica");
		conexion.cerrarConexion();
	}

	@FXML
	public void actualizar(){
		ArrayList<String> errores=validar();
		String c = "";
		if(errores.size()>0){
			for(int i=0;i<errores.size();i++)
				c+=errores.get(i)+"\n";
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al intentar guardar preclinica", c);
			return;
		}
		conexion.establecerConexion();
		Preclinica p=new Preclinica(
				tblPreclinica.getSelectionModel().getSelectedItem().getCita(),
				cboEnfermero.getSelectionModel().getSelectedItem(),
				Double.valueOf(txtTemperatura.getText()),
				Double.valueOf(txtPeso.getText()),
				Double.valueOf(txtAltura.getText()),
				txtPresion.getText()
				);
		int r=p.actualizar(conexion.getConexion());
		if(r>0){
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Actualizar preclinica", "Exito al actualizar el preclinica");
			listaPreclinica.set(tblPreclinica.getSelectionModel().getSelectedIndex(), p);
		}
		else
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al intentar actualizar preclinica", "Error al actualizar preclinica");
		conexion.cerrarConexion();
	}

	@FXML
	public void eliminar(){
		if(tblPreclinica.getSelectionModel().getSelectedItem()==null){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al intentar eliminar preclinica", "No tiene seleccionado ninguna preclinica");
			return;
		}
		conexion.establecerConexion();
		int r=tblPreclinica.getSelectionModel().getSelectedItem().eliminar(conexion.getConexion());
		if(r>0){
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Actualizar preclinica", "Exito al actualizar el preclinica");
			listaPreclinica.remove(tblPreclinica.getSelectionModel().getSelectedIndex());
		}
		else
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al intentar actualizar preclinica", "Error al actualizar preclinica");
		conexion.cerrarConexion();
	}

	public ArrayList<String> validar(){
		ArrayList<String> errores=new ArrayList<String>();
		if(txtTemperatura.getText().trim().equals(""))
			errores.add("El campo de temperatura está vacío");
		else{
			try{
				Double.parseDouble(txtTemperatura.getText().trim());
			}catch(NumberFormatException e){
				errores.add("El campo temperatura no es un número");
			}
		}
		if(txtPeso.getText().trim().equals(""))
			errores.add("El campo de peso está vacío");
		else{
			try{
				Double.parseDouble(txtPeso.getText().trim());
			}catch(NumberFormatException e){
				errores.add("El campo peso no es un número");
			}
		}
		if(txtAltura.getText().trim().equals(""))
			errores.add("El campo de altura está vacío");
		else{
			try{
				Double.parseDouble(txtAltura.getText().trim());
			}catch(NumberFormatException e){
				errores.add("El campo altura no es un número");
			}
		}
		if(txtPresion.getText().trim().equals(""))
			errores.add("El campo de altura está vacío");
		if(cboEnfermero.getSelectionModel().getSelectedItem()==null)
			errores.add("El campo de enfermero está vacío");
		return errores;
	}
}
