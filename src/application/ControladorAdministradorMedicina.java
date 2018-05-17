package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import implementacion.Diagnostico;
import implementacion.Examen;
import implementacion.Medicamento;
import implementacion.Presentacion;
import implementacion.TipoSangre;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import utilerias.GestorConexiones;
import utilerias.Mensaje;

public class ControladorAdministradorMedicina  implements Initializable{
	private Main main;
	private GestorConexiones conexion;

	//TabPane
	@FXML private TabPane tbPanel;
	@FXML private Tab tabPresentacion;
	@FXML private Tab tabMedicamento;
	@FXML private Tab tabExamen;
	@FXML private Tab tabDiagnostico;
	@FXML private Tab tabTipoSangre;

	//Presentacion Medicamentos
	@FXML private TextField txtPresentacion;
	@FXML private Button btnGuardarPresentacion;
	@FXML private Button btnActualizarPresentacion;
	@FXML private Button btnEliminarPresentacion;
	@FXML private TableView<Presentacion> tblPresentacion;
	@FXML private TableColumn<Presentacion,Number> clmncodigoPresentacion;
	@FXML private TableColumn<Presentacion,String> clmnpresentacion;

	private ObservableList<Presentacion> listaPresentacion;

	//Medicamentos
	@FXML private TextField txtMedicamento;
	@FXML private Button btnGuardarMedicamento;
	@FXML private Button btnActualizarMedicamento;
	@FXML private Button btnEliminarMedicamento;
	@FXML private TableView<Medicamento> tblMedicamento;
	@FXML private TableColumn<Medicamento,Number> clmncodigoMedicamento;
	@FXML private TableColumn<Medicamento,String> clmnnombreMedicamento;
	@FXML private TableColumn<Medicamento,Presentacion> clmnpresentacionM;
	@FXML private ComboBox<Presentacion> cboPresentacion;

	private ObservableList<Medicamento> listaMedicamentos;

	//Examenes
	@FXML private TextField txtExamen;
	@FXML private Button btnGuardarExamen;
	@FXML private Button btnActualizarExamen;
	@FXML private Button btnEliminarExamen;
	@FXML private TableView<Examen> tblExamen;
	@FXML private TableColumn<Examen,Number> clmncodigoExamen;
	@FXML private TableColumn<Examen,String> clmnnombreExamen;

	private ObservableList<Examen> listaExamenes;

	//Diagnostico
	@FXML private TextField txtDiagnostico;
	@FXML private Button btnGuardarDiagnostico;
	@FXML private Button btnActualizarDiagnostico;
	@FXML private Button btnEliminarDiagnostico;
	@FXML private TableView<Diagnostico> tblDiagnostico;
	@FXML private TableColumn<Diagnostico,Number> clmncodigoDiagnostico;
	@FXML private TableColumn<Diagnostico,String> clmnnombreDiagnostico;

	private ObservableList<Diagnostico> listaDiagnosticos;

	//Tipos de sangre
	@FXML private TextField txtTipoSangre;
	@FXML private Button btnGuardarTipoSangre;
	@FXML private Button btnActualizarTipoSangre;
	@FXML private Button btnEliminarTipoSangre;
	@FXML private TableView<TipoSangre> tblTipoSangre;
	@FXML private TableColumn<TipoSangre,Number> clmncodigoTipoSangre;
	@FXML private TableColumn<TipoSangre,String> clmnnombreTipoSangre;

	private ObservableList<TipoSangre> listaTipoSangre;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listaPresentacion=FXCollections.observableArrayList();
		listaMedicamentos=FXCollections.observableArrayList();
		listaExamenes=FXCollections.observableArrayList();
		listaDiagnosticos=FXCollections.observableArrayList();
		listaTipoSangre=FXCollections.observableArrayList();
		conexion=new GestorConexiones();
		conexion.establecerConexion();
		tblDiagnostico.setItems(listaDiagnosticos);
		tblPresentacion.setItems(listaPresentacion);
		tblMedicamento.setItems(listaMedicamentos);
		tblExamen.setItems(listaExamenes);
		tblTipoSangre.setItems(listaTipoSangre);
		cboPresentacion.setItems(listaPresentacion);
		enlazarColumnas();
		llenarTablas();
		agregarListeners();
		conexion.cerrarConexion();
	}

	public void setMain(Main main, int i){
		this.main=main;
		switch(i){
			case 1:
				tbPanel.getSelectionModel().select(tabExamen);
				tabPresentacion.setDisable(true);
				tabMedicamento.setDisable(true);
				tabDiagnostico.setDisable(true);
				tabTipoSangre.setDisable(true);
				break;
			case 2:
				tbPanel.getSelectionModel().select(tabDiagnostico);
				tabPresentacion.setDisable(true);
				tabMedicamento.setDisable(true);
				tabExamen.setDisable(true);
				tabTipoSangre.setDisable(true);
				break;
			case 3:
				tbPanel.getSelectionModel().select(tabMedicamento);
				tabPresentacion.setDisable(false);
				tabMedicamento.setDisable(false);
				tabDiagnostico.setDisable(true);
				tabExamen.setDisable(true);
				tabTipoSangre.setDisable(true);
				break;
			case 4:
				tbPanel.getSelectionModel().select(tabTipoSangre);
				tabDiagnostico.setDisable(true);
				tabExamen.setDisable(true);
				tabMedicamento.setDisable(true);
				tabPresentacion.setDisable(true);
				break;
		}
	}

	@FXML
	public void salir(){
		main.principal(5);
	}

	public void enlazarColumnas(){
		clmncodigoPresentacion.setCellValueFactory(
				 new PropertyValueFactory<Presentacion,Number>("codigoPresentacion")
				);
		clmnpresentacion.setCellValueFactory(
				 new PropertyValueFactory<Presentacion,String>("presentacion")
				);
		clmncodigoMedicamento.setCellValueFactory(
						 new PropertyValueFactory<Medicamento,Number>("codigoMedicamento")
						);
		clmnnombreMedicamento.setCellValueFactory(
						 new PropertyValueFactory<Medicamento,String>("nombreMedicamento")
						);
		clmnpresentacionM.setCellValueFactory(
				 new PropertyValueFactory<Medicamento,Presentacion>("presentacion")
				);
		clmncodigoExamen.setCellValueFactory(
				 new PropertyValueFactory<Examen,Number>("codigoExamen")
				);
		clmnnombreExamen.setCellValueFactory(
				 new PropertyValueFactory<Examen,String>("nombreExamen")
				);
		clmncodigoDiagnostico.setCellValueFactory(
				 new PropertyValueFactory<Diagnostico,Number>("codigoDiagnostico")
				);
		clmnnombreDiagnostico.setCellValueFactory(
				 new PropertyValueFactory<Diagnostico,String>("nombreDiagnostico")
				);
		clmncodigoTipoSangre.setCellValueFactory(
				 new PropertyValueFactory<TipoSangre,Number>("codigoTipoSangre")
				);
		clmnnombreTipoSangre.setCellValueFactory(
				 new PropertyValueFactory<TipoSangre,String>("nombreTipoSangre")
				);

	}

	public void llenarTablas(){
		Presentacion.llenarLista(conexion.getConexion(), listaPresentacion);
		Medicamento.llenarLista(conexion.getConexion(), listaMedicamentos);
		Examen.llenar(conexion.getConexion(), listaExamenes);
		Diagnostico.llenar(conexion.getConexion(), listaDiagnosticos);
		TipoSangre.llenar(conexion.getConexion(), listaTipoSangre);
	}

	public void agregarListeners(){
		tblPresentacion.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Presentacion>() {
					@Override
					public void changed(
							ObservableValue<? extends Presentacion> arg0,
							Presentacion valorAnterior,
							Presentacion valorNuevo) {
						if (valorNuevo!=null){
							txtPresentacion.setText(valorNuevo.getPresentacion());
							btnActualizarPresentacion.setDisable(false);
							btnEliminarPresentacion.setDisable(false);
							btnGuardarPresentacion.setDisable(true);
						}
					}
				}
		);
		tblMedicamento.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Medicamento>() {
					@Override
					public void changed(
							ObservableValue<? extends Medicamento> arg0,
							Medicamento valorAnterior,
							Medicamento valorNuevo) {
						if (valorNuevo!=null){
							txtMedicamento.setText(valorNuevo.getNombreMedicamento());
							cboPresentacion.getSelectionModel().select(valorNuevo.getPresentacion());
							btnActualizarMedicamento.setDisable(false);
							btnEliminarMedicamento.setDisable(false);
							btnGuardarMedicamento.setDisable(true);
						}
					}
				}
		);
		tblExamen.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Examen>() {
					@Override
					public void changed(
							ObservableValue<? extends Examen> arg0,
							Examen valorAnterior,
							Examen valorNuevo) {
						if (valorNuevo!=null){
							txtExamen.setText(valorNuevo.getNombreExamen());
							btnActualizarExamen.setDisable(false);
							btnEliminarExamen.setDisable(false);
							btnGuardarExamen.setDisable(true);
						}
					}
				}
		);
		tblDiagnostico.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Diagnostico>() {
					@Override
					public void changed(
							ObservableValue<? extends Diagnostico> arg0,
							Diagnostico valorAnterior,
							Diagnostico valorNuevo) {
						if (valorNuevo!=null){
							txtDiagnostico.setText(valorNuevo.getNombreDiagnostico());
							btnActualizarDiagnostico.setDisable(false);
							btnEliminarDiagnostico.setDisable(false);
							btnGuardarDiagnostico.setDisable(true);
						}
					}
				}
		);
		tblTipoSangre.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<TipoSangre>() {
					@Override
					public void changed(
							ObservableValue<? extends TipoSangre> arg0,
							TipoSangre valorAnterior,
							TipoSangre valorNuevo) {
						if (valorNuevo!=null){
							txtTipoSangre.setText(valorNuevo.getNombreTipoSangre());
							btnActualizarTipoSangre.setDisable(false);
							btnEliminarTipoSangre.setDisable(false);
							btnGuardarTipoSangre.setDisable(true);
						}
					}
				}
		);
	}

	@FXML
	public void guardarPresentacion(){
		if(txtPresentacion.getText().trim().equals("")){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar", "El nombre de la presentación está vacío");
			return;
		}
		conexion.establecerConexion();
		int c=Presentacion.obtenerUltimo(conexion.getConexion())+1;
		Presentacion p=new Presentacion(c,txtPresentacion.getText().trim());
		int r=p.guardar(conexion.getConexion());
		if(r>0){
			listaPresentacion.add(p);
			listaMedicamentos.clear();
			Medicamento.llenarLista(conexion.getConexion(), listaMedicamentos);
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito al guardar", "La presentación ha sido guardada exitosamente");
		}
		else{
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar", "No se ha podigo guardar la presentación");
		}
		conexion.cerrarConexion();
		limpiarPresentacion();
	}

	@FXML
	public void actualizarPresentacion(){
		if(txtPresentacion.getText().trim().equals("")){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al actualizar", "El nombre de la presentación está vacío");
			return;
		}
		conexion.establecerConexion();
		Presentacion p=new Presentacion(tblPresentacion.getSelectionModel().getSelectedItem().getCodigoPresentacion(),txtPresentacion.getText());
		int r=p.actualizar(conexion.getConexion());
		if(r>0){
			listaPresentacion.set(tblPresentacion.getSelectionModel().getSelectedIndex(), p);
			listaMedicamentos.clear();
			Medicamento.llenarLista(conexion.getConexion(), listaMedicamentos);
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito al actualizar", "La presentación ha sido actualizada exitosamente");
		}
		else{
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al actualizar", "No se ha podigo actualizar la presentación");
		}
		conexion.cerrarConexion();
		limpiarPresentacion();
	}

	@FXML
	public void eliminarPresentacion(){
		if(tblPresentacion.getSelectionModel().getSelectedItem()==null){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar", "No hay ningún elemento seleccionado");
			return;
		}
		conexion.establecerConexion();
		int r=tblPresentacion.getSelectionModel().getSelectedItem().eliminar(conexion.getConexion());
		if(r>0){
			listaPresentacion.remove(tblPresentacion.getSelectionModel().getSelectedIndex());
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito al eliminar", "La presentación ha sido eliminada exitosamente");
		}
		else{
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar", "No se ha podigo eliminar la presentación");
		}
		conexion.cerrarConexion();
	}

	@FXML
	public void limpiarPresentacion(){
		txtPresentacion.setText("");
		tblPresentacion.getSelectionModel().select(null);
		btnGuardarPresentacion.setDisable(false);
		btnActualizarPresentacion.setDisable(true);
		btnEliminarPresentacion.setDisable(true);
	}

	@FXML
	public void guardarMedicamento(){
		ArrayList<String> errores=validarCamposMedicamentos();
		if(errores.size()>0){
			String s="";
			for(int i=0;i<errores.size();i++)
				s+=errores.get(i)+"\n";
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar", s);
			return;
		}
		conexion.establecerConexion();
		int c=Medicamento.obtenerUltimo(conexion.getConexion())+1;
		Medicamento p=new Medicamento(c,txtMedicamento.getText().trim(),cboPresentacion.getSelectionModel().getSelectedItem());
		int r=p.guardar(conexion.getConexion());
		if(r>0){
			listaMedicamentos.add(p);
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito al guardar", "La presentación ha sido guardada exitosamente");
		}
		else{
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar", "No se ha podigo guardar la presentación");
		}
		conexion.cerrarConexion();
		limpiarMedicamentos();
	}

	public ArrayList<String> validarCamposMedicamentos(){
		ArrayList<String> errores=new ArrayList<>();
		if(txtMedicamento.getText().trim().equals("")){
			errores.add("El nombre del medicamento está vacío");
		}
		if(cboPresentacion.getSelectionModel().getSelectedItem()==null){
			errores.add("El tipo de presentación está vacío");
		}
		return errores;
	}

	@FXML
	public void actualizarMedicamento(){
		ArrayList<String> errores=validarCamposMedicamentos();
		if(errores.size()>0){
			String s="";
			for(int i=0;i<errores.size();i++)
				s+=errores.get(i)+"\n";
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar", s);
			return;
		}
		conexion.establecerConexion();
		int c=tblMedicamento.getSelectionModel().getSelectedItem().getCodigoMedicamento();
		Medicamento p=new Medicamento(c,txtMedicamento.getText().trim(),cboPresentacion.getSelectionModel().getSelectedItem());
		int r=p.actualizar(conexion.getConexion());
		if(r>0){
			listaMedicamentos.set(tblMedicamento.getSelectionModel().getSelectedIndex(), p);
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito al actualizar", "El medicamento ha sido guardada exitosamente");
		}
		else{
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al actualizar", "No se ha podigo actualizar el medicamento");
		}
		conexion.cerrarConexion();
		limpiarMedicamentos();

	}

	@FXML
	public void eliminarMedicamento(){
		if(tblMedicamento.getSelectionModel().getSelectedItem()==null){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar", "No hay ningún medicamento seleccionado");
			return;
		}
		conexion.establecerConexion();
		int r=tblMedicamento.getSelectionModel().getSelectedItem().eliminar(conexion.getConexion());
		if(r>0){
			listaMedicamentos.remove(tblMedicamento.getSelectionModel().getSelectedIndex());
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito al eliminar", "El medicamento ha sido eliminado exitosamente");
		}
		else{
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar", "No se ha podigo eliminar el medicamento");
		}
		conexion.cerrarConexion();
		limpiarMedicamentos();
	}

	@FXML
	public void limpiarMedicamentos() {
		txtMedicamento.setText("");
		cboPresentacion.getSelectionModel().select(null);
		tblMedicamento.getSelectionModel().select(null);
		btnGuardarMedicamento.setDisable(false);
		btnActualizarMedicamento.setDisable(true);
		btnEliminarMedicamento.setDisable(true);
	}

	@FXML
	public void guardarExamen(){
		if(txtExamen.getText().trim().equals("")){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar", "El nombre del examen está vacío");
			return;
		}
		conexion.establecerConexion();
		int c=Examen.obtenerUltimo(conexion.getConexion())+1;
		Examen p=new Examen(c,txtExamen.getText().trim());
		int r=p.guardar(conexion.getConexion());
		if(r>0){
			listaExamenes.add(p);
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito al guardar", "El examen ha sido guardado exitosamente");
		}
		else{
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar", "No se ha podigo guardar el examen");
		}
		conexion.cerrarConexion();
		limpiarExamen();
	}

	@FXML
	public void actualizarExamen(){
		if(txtExamen.getText().trim().equals("")){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al actualizar", "El nombre del examen está vacío");
			return;
		}
		conexion.establecerConexion();
		Examen p=new Examen(tblExamen.getSelectionModel().getSelectedItem().getCodigoExamen(),txtExamen.getText());
		int r=p.actualizar(conexion.getConexion());
		if(r>0){
			listaExamenes.set(tblExamen.getSelectionModel().getSelectedIndex(), p);
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito al actualizar", "El examen ha sido actualizado exitosamente");
		}
		else{
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al actualizar", "No se ha podigo actualizar el examen");
		}
		conexion.cerrarConexion();
		limpiarExamen();
	}

	@FXML
	public void eliminarExamen(){
		if(tblExamen.getSelectionModel().getSelectedItem()==null){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar", "No hay ningún elemento seleccionado");
			return;
		}
		conexion.establecerConexion();
		int r=tblExamen.getSelectionModel().getSelectedItem().eliminar(conexion.getConexion());
		if(r>0){
			listaExamenes.remove(tblExamen.getSelectionModel().getSelectedIndex());
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito al eliminar", "El examen ha sido eliminado exitosamente");
			limpiarExamen();
		}
		else{
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar", "No se ha podigo eliminar el examen");
		}
		conexion.cerrarConexion();
	}

	@FXML
	public void limpiarExamen(){
		txtExamen.setText("");
		tblExamen.getSelectionModel().select(null);
		btnGuardarExamen.setDisable(false);
		btnActualizarExamen.setDisable(true);
		btnEliminarExamen.setDisable(true);
	}

	@FXML
	public void guardarDiagnostico(){
		if(txtDiagnostico.getText().trim().equals("")){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar", "El nombre del diagnóstico está vacío");
			return;
		}
		conexion.establecerConexion();
		int c=Diagnostico.obtenerUltimo(conexion.getConexion())+1;
		Diagnostico p=new Diagnostico(c,txtDiagnostico.getText().trim());
		int r=p.guardar(conexion.getConexion());
		if(r>0){
			listaDiagnosticos.add(p);
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito al guardar", "El diagnóstico ha sido guardado exitosamente");
		}
		else{
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar", "No se ha podigo guardar el diagnóstico");
		}
		conexion.cerrarConexion();
		limpiarDiagnostico();
	}

	@FXML
	public void actualizarDiagnostico(){
		if(txtDiagnostico.getText().trim().equals("")){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al actualizar", "El nombre del Diagnostico está vacío");
			return;
		}
		conexion.establecerConexion();
		Diagnostico p=new Diagnostico(tblDiagnostico.getSelectionModel().getSelectedItem().getCodigoDiagnostico(),txtDiagnostico.getText());
		int r=p.actualizar(conexion.getConexion());
		if(r>0){
			listaDiagnosticos.set(tblDiagnostico.getSelectionModel().getSelectedIndex(), p);
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito al actualizar", "El diagnostico ha sido actualizado exitosamente");
		}
		else{
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al actualizar", "No se ha podigo actualizar el diagnostico");
		}
		conexion.cerrarConexion();
		limpiarDiagnostico();
	}

	@FXML
	public void eliminarDiagnostico(){
		if(tblDiagnostico.getSelectionModel().getSelectedItem()==null){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar", "No hay ningún elemento seleccionado");
			return;
		}
		conexion.establecerConexion();
		int r=tblDiagnostico.getSelectionModel().getSelectedItem().eliminar(conexion.getConexion());
		if(r>0){
			listaDiagnosticos.remove(tblDiagnostico.getSelectionModel().getSelectedIndex());
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito al eliminar", "El diagnostico ha sido eliminado exitosamente");
			limpiarDiagnostico();
		}
		else{
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar", "No se ha podigo eliminar el diagnostico");
		}
		conexion.cerrarConexion();
	}

	@FXML
	public void limpiarDiagnostico(){
		txtDiagnostico.setText("");
		tblDiagnostico.getSelectionModel().select(null);
		btnGuardarDiagnostico.setDisable(false);
		btnActualizarDiagnostico.setDisable(true);
		btnEliminarDiagnostico.setDisable(true);
	}

	@FXML
	public void guardarTipoSangre(){
		if(txtTipoSangre.getText().trim().equals("")){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar", "El nombre del tipo de sangre está vacío");
			return;
		}
		conexion.establecerConexion();
		int c=TipoSangre.obtenerUltimo(conexion.getConexion())+1;
		TipoSangre p=new TipoSangre(c,txtTipoSangre.getText().trim());
		int r=p.guardar(conexion.getConexion());
		if(r>0){
			listaTipoSangre.add(p);
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito al guardar", "El tipo de sangre ha sido guardado exitosamente");
		}
		else{
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al guardar", "No se ha podigo guardar el tipo de sangre");
		}
		conexion.cerrarConexion();
		limpiarTipoSangre();
	}

	@FXML
	public void actualizarTipoSangre(){
		if(txtTipoSangre.getText().trim().equals("")){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al actualizar", "El nombre del tipo de sangre está vacío");
			return;
		}
		conexion.establecerConexion();
		TipoSangre p=new TipoSangre(tblTipoSangre.getSelectionModel().getSelectedItem().getCodigoTipoSangre(),txtTipoSangre.getText());
		int r=p.actualizar(conexion.getConexion());
		if(r>0){
			listaTipoSangre.set(tblTipoSangre.getSelectionModel().getSelectedIndex(), p);
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito al actualizar", "El tipo de sangre ha sido actualizado exitosamente");
		}
		else{
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al actualizar", "No se ha podigo actualizar el tipo de sangre");
		}
		conexion.cerrarConexion();
		limpiarTipoSangre();
	}

	@FXML
	public void eliminarTipoSangre(){
		if(tblTipoSangre.getSelectionModel().getSelectedItem()==null){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar", "No hay ningún elemento seleccionado");
			return;
		}
		conexion.establecerConexion();
		int r=tblTipoSangre.getSelectionModel().getSelectedItem().eliminar(conexion.getConexion());
		if(r>0){
			listaTipoSangre.remove(tblTipoSangre.getSelectionModel().getSelectedIndex());
			Mensaje.mostrarMensaje(AlertType.INFORMATION, "Exito", "Exito al eliminar", "El tipo de sangre ha sido eliminado exitosamente");
			limpiarTipoSangre();
		}
		else{
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al eliminar", "No se ha podigo eliminar el tipo de sangre");
		}
		conexion.cerrarConexion();
	}

	@FXML
	public void limpiarTipoSangre(){
		txtTipoSangre.setText("");
		tblTipoSangre.getSelectionModel().select(null);
		btnGuardarTipoSangre.setDisable(false);
		btnActualizarTipoSangre.setDisable(true);
		btnEliminarTipoSangre.setDisable(true);
	}

}
