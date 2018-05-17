package application;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import implementacion.Cita;
import implementacion.Diagnostico;
import implementacion.Empleado;
import implementacion.Enfermero;
import implementacion.Examen;
import implementacion.Historial;
import implementacion.Medicamento;
import implementacion.Medico;
import implementacion.Preclinica;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import utilerias.GestorConexiones;
import utilerias.Mensaje;

public class ControladorAtencionMedica implements Initializable {
	private Main main;
	private Empleado empleado;
	private GestorConexiones conexion;

	@FXML private TableColumn<Historial,Number> clmncodigoHistorial;
	@FXML private TableColumn<Historial,Date> clmnfecha;
	@FXML private TableColumn<Historial,Cita> clmncita;
	@FXML private TableColumn<Historial,Medico> clmnmedico;
	@FXML private TableColumn<Historial,Enfermero> clmnenfermero;
	@FXML private TableColumn<Historial,Examen> clmnexamen;
	@FXML private TableColumn<Historial,Medicamento> clmnmedicamento;
	@FXML private TableColumn<Historial,Diagnostico> clmndiagnostico;
	@FXML private TableColumn<Historial,String> clmnobservacion;
	@FXML private TableView<Historial> tblHistorial;
	@FXML private Label lblNombre;
	@FXML private Label lblTemperatura;
	@FXML private Label lblPresion;
	@FXML private Label lblAltura;
	@FXML private Label lblPeso;
	@FXML private ListView<Preclinica> lvwPreclinica;
	@FXML private ComboBox<Examen> cboExamen;
	@FXML private ComboBox<Diagnostico> cboDiagnosticos;
	@FXML private ComboBox<Medicamento> cboMedicamento;
	@FXML private Button btnGuardar;
	@FXML private Button btnRemitir;
	@FXML private TextArea txtObservacion;

	@FXML private LineChart<String, Integer> graficoLinea;
	private ObservableList<XYChart.Data<String, Integer>> listaPacientesxMedico;
	private Series<String, Integer> serie;

	private ObservableList<Historial> listaHistorial;
	private ObservableList<Examen> listaExamenes;
	private ObservableList<Diagnostico> listaDiagnosticos;
	private ObservableList<Medicamento> listaMedicamentos;
	private ObservableList<Preclinica> listaPreclinica;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		conexion=new GestorConexiones();
		listaPacientesxMedico=FXCollections.observableArrayList();
		listaHistorial=FXCollections.observableArrayList();
		listaExamenes=FXCollections.observableArrayList();
		listaDiagnosticos=FXCollections.observableArrayList();
		listaMedicamentos=FXCollections.observableArrayList();
		listaPreclinica=FXCollections.observableArrayList();
		conexion.establecerConexion();
		Examen.llenar(conexion.getConexion(), listaExamenes);
		Diagnostico.llenar(conexion.getConexion(), listaDiagnosticos);
		Medicamento.llenarLista(conexion.getConexion(), listaMedicamentos);
		Historial.llenar(conexion.getConexion(), listaHistorial);
		agregarListeners();
		enlazarColumnas();
		tblHistorial.setItems(listaHistorial);
		cboExamen.setItems(listaExamenes);
		cboDiagnosticos.setItems(listaDiagnosticos);
		cboMedicamento.setItems(listaMedicamentos);
		lvwPreclinica.setItems(listaPreclinica);

		serie = new Series<String, Integer>();
		serie.setName("Pacientes x Medico");
		serie.setData(listaPacientesxMedico);
		graficoLinea.getData().add(serie);

		conexion.cerrarConexion();
	}

	public void enlazarColumnas(){
		clmncodigoHistorial.setCellValueFactory(
		new PropertyValueFactory<Historial,Number>("codigoHistorial")
		);
		clmnfecha.setCellValueFactory(
		new PropertyValueFactory<Historial,Date>("fecha")
		);
		clmncita.setCellValueFactory(
		new PropertyValueFactory<Historial,Cita>("cita")
		);
		clmnmedico.setCellValueFactory(
		new PropertyValueFactory<Historial,Medico>("medico")
		);
		clmnenfermero.setCellValueFactory(
		new PropertyValueFactory<Historial,Enfermero>("enfermero")
		);
		clmnexamen.setCellValueFactory(
			new PropertyValueFactory<Historial,Examen>("examen")
		);
		clmnmedicamento.setCellValueFactory(
			 new PropertyValueFactory<Historial,Medicamento>("medicamento")
		);
		clmndiagnostico.setCellValueFactory(
			 new PropertyValueFactory<Historial,Diagnostico>("diagnostico")
		);
		clmnobservacion.setCellValueFactory(
			new PropertyValueFactory<Historial,String>("observacion")
		);
	}

	public void agregarListeners(){
		lvwPreclinica.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Preclinica>() {
					@Override
					public void changed(
							ObservableValue<? extends Preclinica> arg0,
							Preclinica valorAnterior,
							Preclinica valorNuevo) {
						if (valorNuevo!=null){
							lblNombre.setText(valorNuevo.getCita().getPaciente().toString());
							lblTemperatura.setText(String.valueOf(valorNuevo.getTemperatura()));
							lblPresion.setText(valorNuevo.getPresion());
							lblAltura.setText(String.valueOf(valorNuevo.getAltura()));
							lblPeso.setText(String.valueOf(valorNuevo.getPeso()));
							btnRemitir.setDisable(false);
						}
					}
				}
		);
		tblHistorial.getSelectionModel().selectedItemProperty().addListener(
				new  ChangeListener<Historial>() {

					@Override
					public void changed(
							ObservableValue<? extends Historial> arg0,
							Historial valorAnterior,
							Historial valorNuevo) {
						if (valorNuevo!=null){
							lblNombre.setText(valorNuevo.getCita().getPaciente().toString());
							lblTemperatura.setText(String.valueOf(valorNuevo.getPreclinica().getTemperatura()));
							lblPresion.setText(valorNuevo.getPreclinica().getPresion());
							lblAltura.setText(String.valueOf(valorNuevo.getPreclinica().getAltura()));
							lblPeso.setText(String.valueOf(valorNuevo.getPreclinica().getPeso()));
							txtObservacion.setText(valorNuevo.getObservacion());
							cboDiagnosticos.getSelectionModel().select(valorNuevo.getDiagnostico());
							cboExamen.getSelectionModel().select(valorNuevo.getExamen());
							cboMedicamento.getSelectionModel().select(valorNuevo.getMedicamento());
							if(valorNuevo.getCita().getFecha().toLocalDate().equals(LocalDate.now())){
								btnGuardar.setDisable(true);
								btnRemitir.setDisable(false);
							}
						}
					}
				}
		);
	}

	public void setMain(Main main, Empleado empleado){
		this.main=main;
		this.empleado=empleado;
		Preclinica p=new Preclinica();
		conexion.establecerConexion();
		p.llenar(conexion.getConexion(), listaPreclinica, this.empleado.getNumeroEmpleado());
		Historial.llenarInformacionGrafico(conexion.getConexion(), listaPacientesxMedico, this.empleado.getNumeroEmpleado());
		conexion.cerrarConexion();
	}

	@FXML
	public void volver(){
		main.principal(1);
	}

	@FXML
	public void agregarExamen(){
		main.administradorMedicina(1);
		actualizar();
	}

	@FXML
	public void agregarDiagnostico(){
		main.administradorMedicina(2);
		actualizar();
	}

	@FXML
	public void agregarMedicamento(){
		main.administradorMedicina(3);
		actualizar();
	}

	public void actualizar(){
		conexion.establecerConexion();
		listaMedicamentos.clear();
		Medicamento.llenarLista(conexion.getConexion(), listaMedicamentos);
		listaDiagnosticos.clear();
		Diagnostico.llenar(conexion.getConexion(), listaDiagnosticos);
		listaExamenes.clear();
		Examen.llenar(conexion.getConexion(), listaExamenes);
		conexion.cerrarConexion();

	}

	@FXML
	public void limpiar(){
		lblNombre.setText("");
		lblTemperatura.setText("");
		lblPresion.setText("");
		lblAltura.setText("");
		lblPeso.setText("");
		cboExamen.getSelectionModel().select(null);
		cboDiagnosticos.getSelectionModel().select(null);
		cboMedicamento.getSelectionModel().select(null);
		txtObservacion.setText("");
		lvwPreclinica.getSelectionModel().select(null);
		tblHistorial.getSelectionModel().select(null);
		btnRemitir.setDisable(true);
	}

	public ArrayList<String> validar(){
		ArrayList<String> errores=new ArrayList<>();
		if(cboDiagnosticos.getSelectionModel().getSelectedItem()==null)
			errores.add("El campo diagnóstico está vacío");
		else{
			if(cboMedicamento.getSelectionModel().getSelectedItem()==null)
				errores.add("No se puede seleccionar un diagnóstico sin su respectivo medicamento");
		}
		return errores;
	}


	@FXML
	public void guardar(){
		if(lvwPreclinica.getSelectionModel().getSelectedItem()==null){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar", "No ha seleccionado ningún paciente");
			return;
		}
		ArrayList<String> errores=validar();
		if(errores.size()>0){
			String c="";
			for(int i=0;i<errores.size();i++)
				c+=errores.get(i)+"\n";
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar", c);
			return;
		}
		conexion.establecerConexion();
		int cod=Historial.obtenerUltimo(conexion.getConexion())+1;
		Historial h=new Historial(
				cod,
				lvwPreclinica.getSelectionModel().getSelectedItem(),
				cboExamen.getSelectionModel().getSelectedItem(),
				cboMedicamento.getSelectionModel().getSelectedItem(),
				cboDiagnosticos.getSelectionModel().getSelectedItem(),
				lvwPreclinica.getSelectionModel().getSelectedItem().getCita(),
				lvwPreclinica.getSelectionModel().getSelectedItem().getCita().getMedico(),
				lvwPreclinica.getSelectionModel().getSelectedItem().getEnfermero(),
				Date.valueOf(LocalDate.now()),
				txtObservacion.getText().trim()
				);
		int r=h.guardar(conexion.getConexion());
		if(r>0){
			listaPacientesxMedico.clear();
			listaPreclinica.clear();
			Preclinica p=new Preclinica();
			p.llenar(conexion.getConexion(), listaPreclinica, this.empleado.getNumeroEmpleado());
			Historial.llenarInformacionGrafico(conexion.getConexion(), listaPacientesxMedico, this.empleado.getNumeroEmpleado());
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Guardar en historial", "Exito al guardar el paciente");
			listaHistorial.add(h);
		}
		else
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al intentar guardar en el historial", "Error al guardar el paciente");
		conexion.cerrarConexion();
	}

	@FXML
	public void cita(){
		if(lvwPreclinica.getSelectionModel().getSelectedItem()!=null)
			main.cita(lvwPreclinica.getSelectionModel().getSelectedItem().getCita().getPaciente(),2);
	}
}
