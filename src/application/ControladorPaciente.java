package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import implementacion.Cita;
import implementacion.Historial;
import implementacion.Paciente;
import implementacion.TipoSangre;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import utilerias.GestorConexiones;
import utilerias.Mensaje;

public class ControladorPaciente implements Initializable{
	private Main main;
	private GestorConexiones conexion;

	@FXML private TextField txtIdentidad;
	@FXML private TextField txtNombre;
	@FXML private TextField txtApellido;
	@FXML private DatePicker dtpFecha;
	@FXML private ComboBox<String> cboGenero;
	@FXML private ComboBox<TipoSangre> cboTipoSangre;
	@FXML private TextArea txtDireccion;
	@FXML private TextField txtFijo;
	@FXML private TextField txtCelular;
	@FXML private TextField txtPadre;
	@FXML private TextField txtMadre;
	@FXML private TableView<Paciente> tblPaciente;
	@FXML private TableColumn<Paciente,Number> clmnnumeroExpediente;
	@FXML private TableColumn<Paciente,String> clmnidentidad;
	@FXML private TableColumn<Paciente,String> clmnnombre;
	@FXML private TableColumn<Paciente,String> clmnapellido;
	@FXML private Button btnGuardar;
	@FXML private Button btnEliminar;
	@FXML private Button btnActualizar;
	@FXML private Button btnNuevo;

	@FXML private LineChart<String, Integer> graficoPacientes;
	private ObservableList<XYChart.Data<String, Integer>> listaPacientesAtendidos;
	private Series<String, Integer> serie1;

	@FXML private LineChart<String, Integer> graficoEnfermedades;
	private ObservableList<XYChart.Data<String, Integer>> listaEnfermedades;
	private Series<String, Integer> serie2;

	@FXML private LineChart<String, Integer> graficoMedico;
	private ObservableList<XYChart.Data<String, Integer>> listaPacientesxMedico;
	private Series<String, Integer> serie3;

	private ObservableList<Paciente> listaPacientes;
	private ObservableList<String> listaGenero;
	private ObservableList<TipoSangre> listaTipoSangre;

	public void setMain(Main main){
		this.main=main;
	}

	@FXML
	public void volver(){
		main.principal(3);
	}

	@FXML
	public void cita(){
		if(tblPaciente.getSelectionModel().getSelectedItem()!=null)
			main.cita(tblPaciente.getSelectionModel().getSelectedItem(),1);
		else
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al crear cita", "No ha seleccionado ningún paciente");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		listaPacientes=FXCollections.observableArrayList();
		listaGenero=FXCollections.observableArrayList();
		listaTipoSangre=FXCollections.observableArrayList();
		listaPacientesAtendidos=FXCollections.observableArrayList();
		listaEnfermedades=FXCollections.observableArrayList();
		listaPacientesxMedico=FXCollections.observableArrayList();
		conexion=new GestorConexiones();
		conexion.establecerConexion();

		//Graficos
		Historial.llenarInformacionGraficoPacientesAtendidos(conexion.getConexion(), listaPacientesAtendidos);
		serie1 = new Series<String, Integer>();
		serie1.setName("Pacientes Atendidos");
		serie1.setData(listaPacientesAtendidos);
		graficoPacientes.getData().add(serie1);

		Historial.llenarInformacionGraficoEnfermedades(conexion.getConexion(), listaEnfermedades);
		serie2 = new Series<String, Integer>();
		serie2.setName("Enfermedades");
		serie2.setData(listaEnfermedades);
		graficoEnfermedades.getData().add(serie2);

		Historial.llenarInformacionGraficoPacientesxMedico(conexion.getConexion(), listaPacientesxMedico);
		serie3 = new Series<String, Integer>();
		serie3.setName("Pacientes x Medico");
		serie3.setData(listaPacientesxMedico);
		graficoMedico.getData().add(serie3);

		listaGenero.add("Femenino");
		listaGenero.add("Masculino");


		tblPaciente.setItems(listaPacientes);
		cboGenero.setItems(listaGenero);
		cboTipoSangre.setItems(listaTipoSangre);
		enlazarTabla();
		llenarTabla();

		tblPaciente.getSelectionModel().selectedItemProperty().addListener(
				new  ChangeListener<Paciente>() {

					@Override
					public void changed(
							ObservableValue<? extends Paciente> arg0,
							Paciente valorAnterior,
							Paciente valorNuevo) {
						if (valorNuevo!=null){
							txtIdentidad.setText(valorNuevo.getIdentidad());
							txtNombre.setText(valorNuevo.getNombre());
							txtApellido.setText(valorNuevo.getApellido());
							dtpFecha.setValue(valorNuevo.getFechaNacimiento().toLocalDate());
							cboGenero.getSelectionModel().select(valorNuevo.getGenero());
							cboTipoSangre.getSelectionModel().select(valorNuevo.getTipoSangre());
							txtDireccion.setText(valorNuevo.getDireccion());
							txtFijo.setText(valorNuevo.getTelefono());
							txtCelular.setText(valorNuevo.getCelular());
							txtPadre.setText(valorNuevo.getNombrePadre());
							txtMadre.setText(valorNuevo.getNombreMadre());
							btnGuardar.setDisable(true);
							btnEliminar.setDisable(false);
							btnActualizar.setDisable(false);
						}

					}

				}
		);
		conexion.cerrarConexion();
	}

	public void enlazarTabla(){
		clmnnumeroExpediente.setCellValueFactory(
				 new PropertyValueFactory<Paciente,Number>("numeroExpediente")
				);
				clmnidentidad.setCellValueFactory(
				 new PropertyValueFactory<Paciente,String>("identidad")
				);
				clmnnombre.setCellValueFactory(
				 new PropertyValueFactory<Paciente,String>("nombre")
				);
				clmnapellido.setCellValueFactory(
				 new PropertyValueFactory<Paciente,String>("apellido")
				);
	}

	public void llenarTabla(){
		conexion.establecerConexion();
		Paciente.llenar(conexion.getConexion(), listaPacientes);
		TipoSangre.llenar(conexion.getConexion(), listaTipoSangre);
		conexion.cerrarConexion();
	}

	@FXML
	public void agregarTipoSangre(){
		main.administradorMedicina(4);
		listaTipoSangre.clear();
		conexion.establecerConexion();
		TipoSangre.llenar(conexion.getConexion(), listaTipoSangre);
		conexion.cerrarConexion();
	}

	@FXML
	public void guardar(){
		actualizarGraficos();
		ArrayList<String> errores=validarCampos();
		String c = "";
		if(errores.size()>0){
			for(int i=0;i<errores.size();i++)
				c+=errores.get(i)+"\n";
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al intentar guardar el paciente", c);
			return;
		}
		conexion.establecerConexion();
		int exp=Paciente.obtenerUltimo(conexion.getConexion())+1;
		Paciente p=new Paciente(txtIdentidad.getText(),
				txtNombre.getText(),
				txtApellido.getText(),
				cboGenero.getSelectionModel().getSelectedItem(),
				Date.valueOf(dtpFecha.getValue()),
				0,
				txtFijo.getText(),
				txtCelular.getText(),
				txtDireccion.getText(),
				exp,
				txtPadre.getText(),
				txtMadre.getText(),
				cboTipoSangre.getSelectionModel().getSelectedItem());
		int r=p.guardar(conexion.getConexion());
		if(r>0){
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Guardar paciente", "Exito al guardar el paciente");
			listaPacientes.add(p);
		}
		else
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al intentar guardar el paciente", "Error al guardar el paciente");
		conexion.cerrarConexion();
	}

	@FXML
	public void actualizar(){
		actualizarGraficos();
		ArrayList<String> errores=validarCampos();
		String c = "";
		if(errores.size()>0){
			for(int i=0;i<errores.size();i++)
				c+=errores.get(i)+"\n";
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al intentar guardar el paciente", c);
			return;
		}

		conexion.establecerConexion();
		Paciente p=new Paciente(txtIdentidad.getText(),
				txtNombre.getText(),
				txtApellido.getText(),
				cboGenero.getSelectionModel().getSelectedItem(),
				Date.valueOf(dtpFecha.getValue()),
				0,
				txtFijo.getText(),
				txtCelular.getText(),
				txtDireccion.getText(),
				tblPaciente.getSelectionModel().getSelectedItem().getNumeroExpediente(),
				txtPadre.getText(),
				txtMadre.getText(),
				cboTipoSangre.getSelectionModel().getSelectedItem());
		int r=p.actualizar(conexion.getConexion());
		if(r>0){
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Modificar paciente", "Exito al modificar el paciente");
			listaPacientes.set(tblPaciente.getSelectionModel().getSelectedIndex(), p);
			limpiar();
		}
		else
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al intentar modificar el paciente", "Error al modificar el paciente");
		conexion.cerrarConexion();
	}

	@FXML
	public void eliminar(){
		actualizarGraficos();
		if (tblPaciente.getSelectionModel().getSelectedItem() == null){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar paciente", "No se ha seleccionado ningun paciente");
			return;
		}

		conexion.establecerConexion();
		int resultado = tblPaciente.getSelectionModel().getSelectedItem().eliminar(conexion.getConexion());
		conexion.cerrarConexion();
		if (resultado <=0){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar registro", "No se elimino el registro");
		} else {
			listaPacientes.remove(tblPaciente.getSelectionModel().getSelectedIndex());
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito", "Registro eliminado correctamente");
			limpiar();
		}
	}

	@FXML
	public void limpiar(){
		txtIdentidad.setText("");
		txtNombre.setText("");
		txtApellido.setText("");
		txtDireccion.setText("");
		txtFijo.setText("");
		txtCelular.setText("");
		txtPadre.setText("");
		txtMadre.setText("");
		cboGenero.getSelectionModel().select(null);
		cboTipoSangre.getSelectionModel().select(null);
		tblPaciente.getSelectionModel().select(null);
		btnEliminar.setDisable(true);
		btnActualizar.setDisable(true);
		btnGuardar.setDisable(false);
		dtpFecha.setValue(null);
		actualizarGraficos();
	}

	public void actualizarGraficos(){
		conexion.establecerConexion();
		listaPacientesAtendidos.clear();
		Historial.llenarInformacionGraficoPacientesAtendidos(conexion.getConexion(), listaPacientesAtendidos);
		listaEnfermedades.clear();
		Historial.llenarInformacionGraficoEnfermedades(conexion.getConexion(), listaEnfermedades);
		listaPacientesxMedico.clear();
		Historial.llenarInformacionGraficoPacientesxMedico(conexion.getConexion(), listaPacientesxMedico);
		conexion.cerrarConexion();
	}

	public ArrayList<String> validarCampos(){
		ArrayList<String> errores=new ArrayList<String>();
		if(txtIdentidad.getText().trim().equals(""))
			errores.add("El campo identidad está vacío");
		if(txtNombre.getText().trim().equals(""))
			errores.add("El campo nombre está vacío");
		if(txtApellido.getText().trim().equals(""))
			errores.add("El campo apellido está vacío");
		if(txtDireccion.getText().trim().equals(""))
			errores.add("El campo direccion está vacío");
		if(txtFijo.getText().trim().equals(""))
			errores.add("El campo teléfono fijo está vacío");
		if(txtCelular.getText().trim().equals(""))
			errores.add("El campo teléfono celular está vacío");
		if(txtPadre.getText().trim().equals(""))
			errores.add("El campo nombre del padre está vacío");
		if(txtMadre.getText().trim().equals(""))
			errores.add("El campo nombre de la madre está vacío");
		if(cboGenero.getSelectionModel().getSelectedItem()==null)
			errores.add("El campo género está vacío");
		if(cboTipoSangre.getSelectionModel().getSelectedItem()==null)
			errores.add("El campo tipo de sangre está vacío");
		String patron = "[0-9]{4}-[0-9]{4}-[0-9]{5}";
		Pattern pattern = Pattern.compile(patron);
		Matcher matcher = pattern.matcher(txtIdentidad.getText());
		if (!matcher.matches())
			errores.add("No cumple con el patron de la identidad");
		return errores;
	}

	@FXML
	public void exportarExcel(){
		ArrayList<String> choices = new ArrayList<>();
		choices.add("Tabla pacientes");
		choices.add("Tabla citas");
		choices.add("Tabla historial");
		conexion.establecerConexion();
		ObservableList<Paciente> pacientes=FXCollections.observableArrayList();
		ObservableList<Cita> citas=FXCollections.observableArrayList();
		ObservableList<Historial> historial=FXCollections.observableArrayList();

		Historial.llenar(conexion.getConexion(), historial);
		Cita.llenar(conexion.getConexion(), citas,0);
		Paciente.llenar(conexion.getConexion(), pacientes);

		ChoiceDialog<String> dialog = new ChoiceDialog<>("Tabla...", choices);
		dialog.setTitle("Cuadro de elección");
		dialog.setHeaderText("Elija una opción");
		dialog.setContentText("Tabla a exportar a Excel(El archivo se guardará en el escritorio):");
		conexion.cerrarConexion();
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			String rutaArchivo = System.getProperty("user.home")+"/Desktop/"+LocalDate.now().toString()+".xls";
			File archivoXLS = new File(rutaArchivo);
			if(archivoXLS.exists())
				archivoXLS.delete();
			try {
				archivoXLS.createNewFile();
				HSSFWorkbook libro = new HSSFWorkbook();
				FileOutputStream archivo = new FileOutputStream(archivoXLS);
		        Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
				switch(dialog.getSelectedItem()){
		    	case "Tabla pacientes":
		    		Row filaEncabezado = hoja.createRow(0);
		    		Cell celdaExpediente = filaEncabezado.createCell(0);
		    		celdaExpediente.setCellValue("Expediente");
		    		Cell celdaIdentidad = filaEncabezado.createCell(1);
		    		celdaIdentidad.setCellValue("Identidad");
		    		Cell celdaNombre = filaEncabezado.createCell(2);
		    		celdaNombre.setCellValue("Nombre");
		    		Cell celdaApellido = filaEncabezado.createCell(3);
		    		celdaApellido.setCellValue("Apellido");
		    		Cell celdaFechaNac = filaEncabezado.createCell(4);
		    		celdaFechaNac.setCellValue("Fecha Nac");
		    		Cell celdaDireccion = filaEncabezado.createCell(5);
		    		celdaDireccion.setCellValue("Direccion");
		    		Cell celdaCelular = filaEncabezado.createCell(6);
		    		celdaCelular.setCellValue("Celular");
		    		Cell celdaPadre = filaEncabezado.createCell(7);
		    		celdaPadre.setCellValue("Padre");
		    		Cell celdaMadre = filaEncabezado.createCell(8);
		    		celdaMadre.setCellValue("Madre");
		    		Cell celdaSangre = filaEncabezado.createCell(9);
		    		celdaSangre.setCellValue("Tipo Sangre");
		    		for(int i=0;i<pacientes.size();i++){
		    			Row fila = hoja.createRow(i+1);
			    		celdaExpediente = fila.createCell(0);
			    		celdaExpediente.setCellValue(pacientes.get(i).getNumeroExpediente());
			    		celdaIdentidad = fila.createCell(1);
			    		celdaIdentidad.setCellValue(pacientes.get(i).getIdentidad());
			    		celdaNombre = fila.createCell(2);
			    		celdaNombre.setCellValue(pacientes.get(i).getNombre());
			    		celdaApellido = fila.createCell(3);
			    		celdaApellido.setCellValue(pacientes.get(i).getApellido());
			    		celdaFechaNac = fila.createCell(4);
			    		celdaFechaNac.setCellValue(pacientes.get(i).getFechaNacimiento());
			    		celdaDireccion = fila.createCell(5);
			    		celdaDireccion.setCellValue(pacientes.get(i).getDireccion());
			    		celdaCelular = fila.createCell(6);
			    		celdaCelular.setCellValue(pacientes.get(i).getCelular());
			    		celdaPadre = fila.createCell(7);
			    		celdaPadre.setCellValue(pacientes.get(i).getNombrePadre());
			    		celdaMadre = fila.createCell(8);
			    		celdaMadre.setCellValue(pacientes.get(i).getNombreMadre());
			    		celdaSangre = fila.createCell(9);
			    		celdaSangre.setCellValue(pacientes.get(i).getTipoSangre().getNombreTipoSangre());
		    		}
		    		break;
				case "Tabla citas":
					filaEncabezado = hoja.createRow(0);
		    		Cell celdaCita = filaEncabezado.createCell(0);
		    		celdaCita.setCellValue("Cita");
		    		Cell celdaFechaHora = filaEncabezado.createCell(1);
		    		celdaFechaHora.setCellValue("Hora/Fecha");
		    		celdaNombre = filaEncabezado.createCell(2);
		    		celdaNombre.setCellValue("Nombre");
		    		celdaApellido = filaEncabezado.createCell(3);
		    		celdaApellido.setCellValue("Apellido");
		    		Cell celdaMedico = filaEncabezado.createCell(4);
		    		celdaMedico.setCellValue("Medico");
		    		Cell celdaEstado= filaEncabezado.createCell(5);
		    		celdaEstado.setCellValue("Estado");
		    		for(int i=0;i<citas.size();i++){
		    			Row fila = hoja.createRow(i+1);
			    		celdaCita=fila.createCell(0);
			    		celdaCita.setCellValue(citas.get(i).getCodigoCita());
			    		celdaFechaHora=fila.createCell(1);
			    		celdaFechaHora.setCellValue(citas.get(i).getHora()+" "+citas.get(i).getFecha());
			    		celdaNombre=fila.createCell(2);
			    		celdaNombre.setCellValue(citas.get(i).getPaciente().getNombre());
			    		celdaApellido=fila.createCell(3);
			    		celdaApellido.setCellValue(citas.get(i).getPaciente().getApellido());
			    		celdaMedico=fila.createCell(4);
			    		celdaMedico.setCellValue(citas.get(i).getMedico().toString());
			    		celdaEstado=fila.createCell(5);
			    		celdaEstado.setCellValue(citas.get(i).getEstado());
		    		}
					break;
				case "Tabla historial":
					filaEncabezado = hoja.createRow(0);
		    		Cell cita=filaEncabezado.createCell(0);
		    		cita.setCellValue("No. de cita");
		    		Cell paciente=filaEncabezado.createCell(1);
		    		paciente.setCellValue("Paciente");
		    		Cell medico=filaEncabezado.createCell(2);
		    		medico.setCellValue("Médico");
		    		Cell fecha=filaEncabezado.createCell(3);
		    		fecha.setCellValue("Fecha");
		    		Cell diag=filaEncabezado.createCell(4);
		    		diag.setCellValue("Diagnóstico");
		    		Cell medicamento=filaEncabezado.createCell(5);
		    		medicamento.setCellValue("Medicamento");
		    		Cell examen=filaEncabezado.createCell(6);
		    		examen.setCellValue("Examen");
		    		for(int i=0;i<historial.size();i++){
		    			Row fila = hoja.createRow(i+1);
			    		cita=fila.createCell(0);
			    		cita.setCellValue(historial.get(i).getCita().getCodigoCita());
			    		paciente=fila.createCell(1);
			    		paciente.setCellValue(historial.get(i).getCita().getPaciente().toString());
			    		medico=fila.createCell(2);
			    		medico.setCellValue(historial.get(i).getCita().getMedico().toString());
			    		fecha=fila.createCell(3);
			    		fecha.setCellValue(historial.get(i).getFecha());
			    		diag=fila.createCell(4);
			    		diag.setCellValue(historial.get(i).getDiagnostico().toString());
			    		medicamento=fila.createCell(5);
			    		medicamento.setCellValue(historial.get(i).getMedicamento().toString());
			    		examen=fila.createCell(6);
			    		examen.setCellValue(historial.get(i).getExamen().toString());
		    		}

					break;

		    }
				libro.write(archivo);
		        archivo.close();
		        Desktop.getDesktop().open(archivoXLS);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
