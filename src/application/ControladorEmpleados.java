package application;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import implementacion.Area;
import implementacion.Empleado;
import implementacion.Enfermero;
import implementacion.Especialidad;
import implementacion.Medico;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import utilerias.GestorConexiones;
import utilerias.Mensaje;

public class ControladorEmpleados implements Initializable{
	@FXML private Button bntGuardar;
	@FXML private Button bntEliminar;
	@FXML private Button bntNuevo;
	@FXML private Button bntActualizar;
	@FXML private Button bntsalir;

	private ObservableList<Empleado> ListaEmpleado;

	@FXML private PasswordField txtContrasena;

	@FXML private TextField txtIdentidad;
	@FXML private TextField txtNombre;
	@FXML private TextField txtApellido;
	@FXML private DatePicker dtpFecha;
    @FXML private TextField txtEdad;
    @FXML private TextArea txtDireccion;
	@FXML private TextField txtFijo;
	@FXML private TextField txtCelular;

	@FXML private TextField horario;

	@FXML private ComboBox<Area> cboArea;
	@FXML private ComboBox<String> cboGenero;

	private Main main;
	private GestorConexiones conexion;

	@FXML private TextField txtArea;
	@FXML private Button btnGuardarArea;
	@FXML private Button btnEliminarArea;
	@FXML private Button btnActualizarArea;
	@FXML private TableView<Area> tblArea;
	@FXML private TableColumn<Area,Number> clmncodigoArea;
	@FXML private TableColumn<Area,String> clmnnombreArea;
	@FXML private TableView<Empleado> tblInformacion;
	@FXML private TableColumn<Empleado,String> clmnidentidad;
	@FXML private TableColumn<Empleado,String> clmnnombre;
	@FXML private TableColumn<Empleado,Area> clmnarea;
	@FXML private TableColumn<Empleado,Number> clmnnumeroEmpleado;
	@FXML private TableColumn<Empleado,String> clmnapellido;

	@FXML private TabPane tbpEmpleado;
	@FXML private Tab tbMedico;
	@FXML private Tab tbEnfermero;
	@FXML private TextField txtNoColegioMedico;
	@FXML private TextField txtConsultorio;
	@FXML private ComboBox<Especialidad> cboEspecialidad;
	@FXML private TextField txtNoColegioEnfermeria;
	@FXML private CheckBox chkEditar;

	private ObservableList<Especialidad> listaEspecialidad;
	private ObservableList<Area> listaAreas;
	private ObservableList<String> listaGeneros;

	@FXML private TableView<Especialidad> tblEspecialidad;
    @FXML private TableColumn<Especialidad,String> clmnnombreEspecialidad;
    @FXML private TableColumn<Especialidad,String> clmncodigoEspecialidad;

	@FXML private TextField txtnombreEspecialidad;
	@FXML private TextField txtDescripcion;

	@FXML private Button bntGuardarEspecialidad;
	@FXML private Button bntEliminarEspecialidad;
	@FXML private Button bntNuevoEspecialidad;
	@FXML private Button bntActualizarEspecialidad;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		listaAreas=FXCollections.observableArrayList();
		ListaEmpleado =FXCollections.observableArrayList();
		listaEspecialidad=FXCollections.observableArrayList();
		listaGeneros=FXCollections.observableArrayList();
		listaGeneros.add("Femenino");
		listaGeneros.add("Masculino");
		cboGenero.setItems(listaGeneros);
		conexion = new GestorConexiones();
		conexion.establecerConexion();
		cboArea.setItems(listaAreas);
		tblArea.setItems(listaAreas);
		tblEspecialidad.setItems(listaEspecialidad);
		cboEspecialidad.setItems(listaEspecialidad);
		clmnidentidad.setCellValueFactory(
		 new PropertyValueFactory<Empleado,String>("identidad")
		);
		clmnnombre.setCellValueFactory(
		 new PropertyValueFactory<Empleado,String>("nombre")
		);

		clmnarea.setCellValueFactory(
		 new PropertyValueFactory<Empleado,Area>("area")
		);
		clmncodigoArea.setCellValueFactory(
			 new PropertyValueFactory<Area,Number>("codigoArea")
		);
		clmnnombreArea.setCellValueFactory(
				new PropertyValueFactory<Area,String>("nombreArea")
		);
		clmnnumeroEmpleado.setCellValueFactory(
			 new PropertyValueFactory<Empleado,Number>("numeroEmpleado")
		);
		clmnapellido.setCellValueFactory(
			 new PropertyValueFactory<Empleado,String>("apellido")
		);
		clmnnombreEspecialidad.setCellValueFactory(
				 new PropertyValueFactory<Especialidad,String>("nombreEspecialidad")
		);
		clmncodigoEspecialidad.setCellValueFactory(
				 new PropertyValueFactory<Especialidad,String>("codigoEspecialidad")
		);


		tblEspecialidad.setItems(listaEspecialidad);
		tblInformacion.setItems(ListaEmpleado);
		tblInformacion.getSelectionModel().selectedItemProperty().addListener(
				new  ChangeListener<Empleado>() {

					@Override
					public void changed(
							ObservableValue<? extends Empleado> arg0,
							Empleado valorAnterior,
							Empleado valorNuevo) {
						if (valorNuevo!=null){
							cboArea.getSelectionModel().select(valorNuevo.getArea());
							txtDireccion.setText(valorNuevo.getDireccion());
							txtNombre.setText(valorNuevo.getNombre());
							txtCelular.setText(valorNuevo.getCelular());
							txtFijo.setText(valorNuevo.getTelefono());
							horario.setText(valorNuevo.getHorario());
							cboGenero.getSelectionModel().select(valorNuevo.getGenero());
							txtApellido.setText(valorNuevo.getApellido());
							txtIdentidad.setText(String.valueOf(valorNuevo.getIdentidad()));
							txtEdad.setText(String.valueOf(valorNuevo.getEdad()));
							dtpFecha.setValue(valorNuevo.getFechaNacimiento().toLocalDate());
							txtContrasena.setPromptText("La contraseña no se mostrará");
							txtContrasena.setDisable(true);
							chkEditar.setDisable(false);
							conexion.establecerConexion();
							switch(valorNuevo.getArea().getCodigoArea()){
								case 2:
									tbpEmpleado.setDisable(false);
									tbEnfermero.setDisable(false);
									tbpEmpleado.getSelectionModel().select(tbEnfermero);
									Enfermero enf=new Enfermero();
									enf.buscar(conexion.getConexion(), valorNuevo.getNumeroEmpleado());
									txtNoColegioEnfermeria.setText(String.valueOf(enf.getNumeroColegioEnfermeria()));
									break;
								case 3:
									tbpEmpleado.setDisable(false);
									tbMedico.setDisable(false);
									tbpEmpleado.getSelectionModel().select(tbMedico);
									Medico med=new Medico();
									med.buscar(conexion.getConexion(), valorNuevo.getNumeroEmpleado());
									txtNoColegioMedico.setText(String.valueOf(med.getNumeroColegioMedico()));
									txtConsultorio.setText(String.valueOf(med.getConsultorio()));
									cboEspecialidad.getSelectionModel().select(med.getEspecialidad());
									break;
								default:
									tbpEmpleado.setDisable(true);
									tbEnfermero.setDisable(true);
									tbMedico.setDisable(true);
							}
							conexion.cerrarConexion();
							bntGuardar.setDisable(true);
							bntEliminar.setDisable(false);
							bntActualizar.setDisable(false);
						}

					}
					}
		);
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
		cboArea.getSelectionModel().selectedItemProperty().addListener(
				new  ChangeListener<Area>() {

					@Override
					public void changed(
							ObservableValue<? extends Area> arg0,
							Area valorAnterior,
							Area valorNuevo) {
						if (valorNuevo!=null){
							switch(valorNuevo.getCodigoArea()){
								case 2:
									tbpEmpleado.setDisable(false);
									tbEnfermero.setDisable(false);
									tbpEmpleado.getSelectionModel().select(tbEnfermero);
									break;
								case 3:
									tbpEmpleado.setDisable(false);
									tbMedico.setDisable(false);
									tbpEmpleado.getSelectionModel().select(tbMedico);
									break;
								default:
									tbpEmpleado.setDisable(true);
									tbEnfermero.setDisable(true);
									tbMedico.setDisable(true);
							}

						}

					}

				}
		);
		chkEditar.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		        txtContrasena.setDisable(!newValue);
		    }
		});
		tblEspecialidad.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Especialidad>() {
			@Override
			public void changed(ObservableValue<? extends Especialidad> arg0,
					Especialidad valorAnterior, Especialidad valorSeleccionado) {

				if (valorSeleccionado != null) {

					txtnombreEspecialidad.setText(valorSeleccionado.getNombreEspecialidad());
					txtDescripcion.setText(valorSeleccionado.getDescripcion());
                    bntActualizarEspecialidad.setDisable(false);
					bntGuardarEspecialidad.setDisable(true);
                    bntEliminarEspecialidad.setDisable(false);

				}

			}

		}


		);

		 Empleado.llenarInformacion(ListaEmpleado, conexion.getConexion());
		 Area.llenar(conexion.getConexion(), listaAreas);
		 Especialidad.llenarInformacion(conexion.getConexion(), listaEspecialidad);
		 conexion.cerrarConexion();
	}

	public void setMain(Main main){
		this.main=main;
	}
	@FXML
	public void GuardarArchivo() {
		ArrayList<String> errores = validarCampos();
		if (errores.size()>0){
			String strErrores="";
			for (int i = 0; i<errores.size();i++)
				strErrores += errores.get(i) + "\n";
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error de validacion", strErrores);
			return;
		}
		conexion.establecerConexion();
		int c=Empleado.obtenerUltimo(conexion.getConexion())+1;
		Empleado p =new  Empleado(
				c,
				txtIdentidad.getText(),
				txtNombre.getText(),
				txtApellido.getText(),
				cboGenero.getSelectionModel().getSelectedItem(),
				Date.valueOf(dtpFecha.getValue()),
				Integer.valueOf(txtEdad.getText()),
				txtFijo.getText(),
				txtCelular.getText(),
				txtDireccion.getText(),
				horario.getText(),
				cboArea.getSelectionModel().getSelectedItem(),
				txtContrasena.getText()
		);
		Empleado e=new Empleado();
		e.setNumeroEmpleado(c);
		e.setNombre(txtNombre.getText().trim());
		e.setApellido(txtApellido.getText().trim());
		int resultado = p.Guardar(conexion.getConexion());
		if (resultado <= 0) {
			Alert mensaje = new Alert(AlertType.ERROR);
			mensaje.setHeaderText("Error al guardar");
			mensaje.setContentText("No se pudo almacenar el registro");
			mensaje.setTitle("Error");
			mensaje.show();
		} else {
			switch(cboArea.getSelectionModel().getSelectedItem().getCodigoArea()){
				case 2:
					Enfermero enf=new Enfermero(e,Integer.valueOf(txtNoColegioEnfermeria.getText().trim()));
					enf.Guardar(conexion.getConexion());
					break;
				case 3:
					Medico m=new Medico(e,
							Integer.valueOf(txtNoColegioMedico.getText()),
							Integer.valueOf(txtConsultorio.getText()),
							cboEspecialidad.getSelectionModel().getSelectedItem());
					m.guardar(conexion.getConexion());
					break;
				default:
					break;
			}
			ListaEmpleado.add(p);
			Alert mensaje = new Alert(AlertType.INFORMATION);
			mensaje.setHeaderText("Exito");
			mensaje.setContentText("Registro almacenado correctamente");
			mensaje.setTitle("Exito");
			mensaje.show();
		}
		conexion.cerrarConexion();

	}


	public ArrayList<String> validarCampos(){
		ArrayList<String> errores = new ArrayList<String>();
		if (txtContrasena.getText().trim().equals("") && !txtContrasena.isDisable())
			errores.add("El contraseña  esta vacio");
		if (txtEdad.getText().trim().equals(""))
			errores.add("El campo edad esta vacio");
		else{
			try{
				Integer.valueOf(txtEdad.getText().trim());
			}catch(NumberFormatException e){
				errores.add("No se ingresó un número como edad");
			}
		}
		if (txtDireccion.getText().trim().equals(""))
			errores.add("El campo direccion esta vacio");
		if (txtNombre.getText().trim().equals(""))
			errores.add("El campo nombre invitados esta vacio");
		if (dtpFecha.getValue() == null)
			errores.add("El campo fecha esta vacio");
		if (cboArea.getSelectionModel().getSelectedItem() == null)
			errores.add("El campo area esta vacio");
		else{
			switch(cboArea.getSelectionModel().getSelectedItem().getCodigoArea()){
				case 2:
					if(txtNoColegioEnfermeria.getText().trim().equals(""))
						errores.add("El campo de colegio médico está vacío");
					break;
				case 3:
					if(txtNoColegioMedico.getText().trim().equals(""))
						errores.add("El campo de colegio médico está vacío");
					try{
						Integer.valueOf(txtConsultorio.getText().trim());
					}catch(NumberFormatException e){
						errores.add("No se ingresó un número como consultorio");
					}
					break;
			}
		}
		if (horario.getText().trim().equals(""))
			errores.add("El campo horario esta vacio");
		if (txtFijo.getText().trim().equals(""))
			errores.add("El campo telefono fijo esta vacio");
		if (txtCelular.getText().trim().equals(""))
			errores.add("El campo celular esta vacio");
		if(cboGenero.getSelectionModel().getSelectedItem()==null)
			errores.add("El campo género está vacío");
		String patron = "[0-9]{4}-[0-9]{4}-[0-9]{5}";
		Pattern pattern = Pattern.compile(patron);
		Matcher matcher = pattern.matcher(txtIdentidad.getText());
		if (!matcher.matches())
			errores.add("No cumple con el patron de la identidad");
		return errores;
	}


	@FXML
	public void Limpiar(){
		txtIdentidad.setText("");
		dtpFecha.setValue(null);
		txtDireccion.setText("");
		txtApellido.setText("");
		txtNombre.setText("");
		txtFijo.setText("");
		txtCelular.setText("");
		txtContrasena.setText("");
		cboGenero.getSelectionModel().select(null);
		horario.setText("");
		cboArea.getSelectionModel().select(null);
		txtEdad.setText("");
		tblInformacion.getSelectionModel().select(null);
		txtNoColegioEnfermeria.setText("");
		txtNoColegioMedico.setText("");
		txtConsultorio.setText("");
		cboEspecialidad.getSelectionModel().select(null);
		bntGuardar.setDisable(false);
		bntEliminar.setDisable(true);
		bntActualizar.setDisable(true);
		tbpEmpleado.setDisable(true);
		tbEnfermero.setDisable(true);
		tbMedico.setDisable(true);
		txtContrasena.setDisable(false);
		chkEditar.setDisable(true);
	}

	@FXML
	public void actualizarRegistro(){
		if (tblInformacion.getSelectionModel().getSelectedItem() == null){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al actualizar registro", "No se ha seleccionado ningun registro");
			return;
		}
		ArrayList<String> errores = validarCampos();
		if (errores.size()>0){
			String strErrores="";
			for (int i = 0; i<errores.size();i++)
				strErrores += errores.get(i) + "\n";
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error de validacion", strErrores);
			return;
		}
		conexion.establecerConexion();
		String pass="";
		if(chkEditar.isSelected())
			pass=txtContrasena.getText().trim();
		int c=tblInformacion.getSelectionModel().getSelectedItem().getNumeroEmpleado();
		Empleado e =
			new Empleado(c,
					txtIdentidad.getText(),
					txtNombre.getText(),
					txtApellido.getText(),
					cboGenero.getSelectionModel().getSelectedItem(),
					Date.valueOf(dtpFecha.getValue()),
					Integer.valueOf(txtEdad.getText()),
					txtFijo.getText(),
					txtCelular.getText(),
					txtDireccion.getText(),
					horario.getText(),
					cboArea.getSelectionModel().getSelectedItem(),
					pass
				);
		int resultado=0;
		if(pass.equals(""))
			resultado = e.actualizarRegistro(conexion.getConexion());
		else
			resultado = e.actualizarRegistroContraseña(conexion.getConexion());
		Empleado em=new Empleado();
		em.setNumeroEmpleado(c);
		em.setNombre(txtNombre.getText().trim());
		em.setApellido(txtApellido.getText().trim());
		switch(cboArea.getSelectionModel().getSelectedItem().getCodigoArea()){
			case 2:
				Enfermero enf=new Enfermero(em,Integer.valueOf(txtNoColegioEnfermeria.getText().trim()));
				enf.actualizar(conexion.getConexion());
				break;
			case 3:
				Medico m=new Medico(em,
						Integer.valueOf(txtNoColegioMedico.getText()),
						Integer.valueOf(txtConsultorio.getText()),
						cboEspecialidad.getSelectionModel().getSelectedItem());
				m.actualizar(conexion.getConexion());
				break;
		}
		conexion.cerrarConexion();
		if (resultado <=0){
			Alert mensaje = new Alert(AlertType.ERROR);
			mensaje.setHeaderText("Error al actualizar");
			mensaje.setContentText("No se actualizo el registro");
			mensaje.setTitle("Error");
			mensaje.show();
		} else {
			ListaEmpleado.set(tblInformacion.getSelectionModel().getSelectedIndex(),e);
			Alert mensaje = new Alert(AlertType.INFORMATION);
			mensaje.setHeaderText("Exito");
			mensaje.setContentText("Registro actualizado correctamente");
			mensaje.setTitle("Exito");
			mensaje.show();
		}
	}
	@FXML
	public void volver() {
		main.principal(4);
	}
	@FXML
	public void eliminar() {
		if (tblInformacion.getSelectionModel().getSelectedItem() == null) {
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar paciente",
					"No se ha seleccionado ningun paciente");
			return;
		}
		conexion.establecerConexion();
		int resultado = tblInformacion.getSelectionModel().getSelectedItem().eliminar(conexion.getConexion());
		conexion.cerrarConexion();
		if (resultado <= 0) {
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar registro", "No se elimino el registro");
		} else {
			ListaEmpleado.remove(tblInformacion.getSelectionModel().getSelectedIndex());
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito", "Registro eliminado correctamente");
			Limpiar();
		}
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

	@FXML
	public void limpiarEspecialidad(){
		txtnombreEspecialidad.setText("");
		txtDescripcion.setText("");
        bntGuardarEspecialidad.setDisable(false);
        bntEliminarEspecialidad.setDisable(true);
        bntActualizarEspecialidad.setDisable(true);
        tblEspecialidad.getSelectionModel().select(null);
	}

	@FXML
	public void guardarEspecialidad(){
		ArrayList<String> errores = validarCamposEspecialidad();
		if (errores.size()>0){
			String strErrores="";
			for (int i = 0; i<errores.size();i++)
				strErrores += errores.get(i) + "\n";
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error de validacion", strErrores);
			return;
		}
		conexion.establecerConexion();
		Especialidad p =new  Especialidad(Especialidad.obtenerUltimo(conexion.getConexion())+1,
				txtnombreEspecialidad.getText(),txtDescripcion.getText()
        );
		int resultado = p.guardarRegistro(conexion.getConexion());
		conexion.cerrarConexion();
		if (resultado <= 0) {
			Alert mensaje = new Alert(AlertType.ERROR);
			mensaje.setHeaderText("Error al guardar");
			mensaje.setContentText("No se pudo registrar especialidad");
			mensaje.setTitle("Error");
			mensaje.show();
		} else {
			listaEspecialidad.add(p);
			Alert mensaje = new Alert(AlertType.INFORMATION);
			mensaje.setHeaderText("Exito");
			mensaje.setContentText("Registro almacenado correctamente");
			mensaje.setTitle("Exito");
			mensaje.show();
		}
	}

	@FXML
	public void eliminarEspecialidad(){
		if (tblEspecialidad.getSelectionModel().getSelectedItem() == null) {
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar Especialidad",
					"No se ha seleccionado Alguna Especialidad");
			return;
		}

	         conexion.establecerConexion();
		     int resultado = tblEspecialidad.getSelectionModel().getSelectedItem().eliminar(conexion.getConexion());
		     conexion.cerrarConexion();
		if (resultado <= 0) {
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar registro", "No se elimino el registro");
		} else {
			listaEspecialidad.remove(tblEspecialidad.getSelectionModel().getSelectedIndex());
			Alert mensaje = new Alert(AlertType.INFORMATION);
			mensaje.setHeaderText("Exito");
			mensaje.setContentText("Registro eliminado correctamente");
			mensaje.setTitle("Exito");
			mensaje.show();
			limpiarEspecialidad();
		}
	}

	@FXML
	public void actualizarEspecialiad(){
		ArrayList<String> errores = validarCamposEspecialidad();
		if (errores.size()>0){
			String strErrores="";
			for (int i = 0; i<errores.size();i++)
				strErrores += errores.get(i) + "\n";
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error de validacion", strErrores);
			return;
		}

		Especialidad e =
				new Especialidad(
						tblEspecialidad.getSelectionModel().getSelectedItem().getCodigoEspecialidad(),
						txtnombreEspecialidad.getText(),
						txtDescripcion.getText()
				);
		conexion.establecerConexion();
		int resultado = e.actualizarRegistro(conexion.getConexion());
		conexion.cerrarConexion();
		if (resultado <=0){
			Alert mensaje = new Alert(AlertType.ERROR);
			mensaje.setHeaderText("Error al actualizar");
			mensaje.setContentText("No se actualizo el registro");
			mensaje.setTitle("Error");
			mensaje.show();
		} else {
			listaEspecialidad.set(tblEspecialidad.getSelectionModel().getSelectedIndex(),e);
			Alert mensaje = new Alert(AlertType.INFORMATION);
			mensaje.setHeaderText("Exito");
			mensaje.setContentText("Registro actualizado correctamente");
			mensaje.setTitle("Exito");
			mensaje.show();
		}
	}

	public ArrayList<String> validarCamposEspecialidad(){
		ArrayList<String> errores = new ArrayList<String>();

        if (txtnombreEspecialidad.getText().trim().equals(""))
			errores.add("El campo direccion esta vacio");
        if (txtDescripcion.getText().trim().equals(""))
			errores.add("El campo direccion esta vacio");

         return errores;
	}
}
