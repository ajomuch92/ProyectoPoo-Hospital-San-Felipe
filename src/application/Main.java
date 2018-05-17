package application;

import implementacion.Empleado;
import implementacion.Paciente;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Main extends Application {
	//Controladores
	private ControladorAdministrador controladorAdministrador;
	private ControladorAtencionMedica controladorAtencionMedica;
	private ControladorCita controladorCita;
	private ControladorEnfermeros controladorEnfermeros;
	private ControladorMedico controladorMedico;
	private ControladorPaciente controladorPaciente;
	private ControladorPreclinica controladorPreclinica;
	private ControladorPrincipal controladorPrincipal;
	private ControladorAdministradorMedicina controladorAdministradorMedicina;
	private ControladorEmpleados controladorEmpleados;

	//Formulario
	private Stage formularioPrincipal;
	private Stage formularioAdministrador;
	private Stage formularioAtencionMedica;
	private Stage formularioCita;
	private Stage formularioEnfermeros;
	private Stage formularioMedico;
	private Stage formularioPaciente;
	private Stage formularioPreclinica;
	private Stage formularioAdministradorMedicina;
	private Stage formularioEmpleados;

	//Escenas
	private Scene escenaMedico;
	private Scene escenaEnfermeros;

	@Override
	public void start(Stage primaryStage) {
		formularioPrincipal=primaryStage;
		try {
			FXMLLoader loader=new FXMLLoader(getClass().getResource("Principal.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			controladorPrincipal=loader.getController();
			controladorPrincipal.setMain(this);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Iniciar sesion");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../imagenes/Documents-Caduceus-icon.png")));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void atencionMedica(Empleado empleado){
		if(formularioAtencionMedica==null){
			try {
				formularioAtencionMedica=new Stage();
				FXMLLoader loader=new FXMLLoader(getClass().getResource("VistaAtencionMedica.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				Scene scene = new Scene(root);
				controladorAtencionMedica=loader.getController();
				controladorAtencionMedica.setMain(this, empleado);
				formularioAtencionMedica.setScene(scene);
				formularioAtencionMedica.getIcons().add(new Image(getClass().getResourceAsStream("../imagenes/People-Doctor-Male-icon.png")));
				formularioAtencionMedica.setTitle(empleado.toString());
				formularioAtencionMedica.setResizable(false);
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
		formularioPrincipal.hide();
		formularioAtencionMedica.show();
	}

	public void cita(Paciente paciente, int i){
		Stage temp;
		if(i==1)
			temp=formularioPaciente;
		else
			temp=formularioAtencionMedica;
		if(formularioCita==null){
			try {
				formularioCita=new Stage();
				formularioCita.initOwner(temp);
				formularioCita.initModality(Modality.WINDOW_MODAL);
				FXMLLoader loader=new FXMLLoader(getClass().getResource("VistaCita.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				Scene scene = new Scene(root);
				controladorCita=loader.getController();
				controladorCita.setMain(this);
				controladorCita.setPaciente(paciente);
				formularioCita.setScene(scene);
				formularioCita.getIcons().add(new Image(getClass().getResourceAsStream("../imagenes/Medical-invoice-information-icon.png")));
				formularioCita.setResizable(false);
				formularioCita.setTitle("Crear citas");
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
		formularioCita.show();
	}

	public void enfermero(){
		if(formularioEnfermeros==null){
			try {
				formularioEnfermeros=new Stage();
				FXMLLoader loader=new FXMLLoader(getClass().getResource("VistaEnfermeros.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				Scene scene = new Scene(root);
				controladorEnfermeros=loader.getController();
				controladorEnfermeros.setMain(this);
				formularioEnfermeros.setScene(scene);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		formularioPrincipal.hide();
		formularioEnfermeros.show();
	}

	public void medico(){
		if(formularioMedico==null){
			try {
				formularioMedico=new Stage();
				FXMLLoader loader=new FXMLLoader(getClass().getResource("VistaMedico.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				Scene scene = new Scene(root);
				controladorMedico=loader.getController();
				controladorMedico.setMain(this);
				formularioMedico.setScene(scene);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		formularioPrincipal.hide();
		formularioMedico.show();
	}

	public void paciente(){
		if(formularioPaciente==null){
			try {
				formularioPaciente=new Stage();
				FXMLLoader loader=new FXMLLoader(getClass().getResource("VistaPaciente.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				Scene scene = new Scene(root);
				controladorPaciente=loader.getController();
				controladorPaciente.setMain(this);
				formularioPaciente.setScene(scene);
				formularioPaciente.getIcons().add(new Image(getClass().getResourceAsStream("../imagenes/Patients-icon.png")));
				formularioPaciente.setResizable(false);
				formularioPaciente.setTitle("Crear pacientes");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		formularioPrincipal.hide();
		formularioPaciente.show();
	}

	public void preclinica(Empleado empleado){
		if(formularioPreclinica==null){
			try {
				formularioPreclinica=new Stage();
				FXMLLoader loader=new FXMLLoader(getClass().getResource("VistaPreclinica.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				Scene scene = new Scene(root);
				controladorPreclinica=loader.getController();
				controladorPreclinica.setMain(this,empleado);
				formularioPreclinica.setScene(scene);
				formularioPreclinica.getIcons().add(new Image(getClass().getResourceAsStream("../imagenes/Patients-icon.png")));
				formularioPreclinica.setResizable(false);
				formularioPreclinica.setTitle("Preclinica");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		formularioPrincipal.hide();
		formularioPreclinica.show();
	}

	public void administrador(){
		if(formularioAdministrador==null){
			try {
				formularioAdministrador=new Stage();
				FXMLLoader loader=new FXMLLoader(getClass().getResource("VistaAdministrador.fxml"));
				BorderPane root = (BorderPane)loader.load();
				Scene scene = new Scene(root);
				controladorAdministrador=loader.getController();
				controladorAdministrador.setMain(this);
				formularioAdministrador.setScene(scene);
				formularioAdministrador.getIcons().add(new Image(getClass().getResourceAsStream("../imagenes/health_care_shield.ico")));
				formularioAdministrador.setResizable(false);
				formularioAdministrador.setTitle("Administrador de empleados");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		formularioPrincipal.hide();
		formularioAdministrador.show();
	}

	public void administradorMedicina(int i){
		if(formularioAdministradorMedicina==null){
			try {
				formularioAdministradorMedicina=new Stage();
				if(i>0 && i<4){
					formularioAdministradorMedicina.initOwner(formularioAtencionMedica);
					formularioAdministradorMedicina.initModality(Modality.WINDOW_MODAL);
				}
				if(i==4){
					formularioAdministradorMedicina.initOwner(formularioPaciente);
					formularioAdministradorMedicina.initModality(Modality.WINDOW_MODAL);
				}
				FXMLLoader loader=new FXMLLoader(getClass().getResource("VistaAdministradorMedicina.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				Scene scene = new Scene(root);
				controladorAdministradorMedicina=loader.getController();
				controladorAdministradorMedicina.setMain(this,i);
				formularioAdministradorMedicina.setScene(scene);
				formularioAdministradorMedicina.setResizable(false);
				formularioAdministradorMedicina.setTitle("Administrador Medicina");
				formularioAdministradorMedicina.getIcons().add(new Image(getClass().getResourceAsStream("../imagenes/Documents-Caduceus-icon.png")));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		formularioPrincipal.hide();
		if(i>0)
			formularioAdministradorMedicina.showAndWait();
		else
			formularioAdministradorMedicina.show();
	}

	public void empleado(){
		if(formularioEmpleados==null){
			try {
				formularioEmpleados=new Stage();
				FXMLLoader loader=new FXMLLoader(getClass().getResource("VistaEmpleados.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				Scene scene = new Scene(root);
				formularioEmpleados.setScene(scene);
				controladorEmpleados=loader.getController();
				controladorEmpleados.setMain(this);
				formularioEmpleados.getIcons().add(new Image(getClass().getResourceAsStream("../imagenes/Documents-Caduceus-icon.png")));
				formularioEmpleados.setResizable(false);
				formularioEmpleados.setTitle("Empleados");
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		formularioPrincipal.hide();
		formularioEmpleados.show();
	}

	public void principal(int i){
		int c=0;
		switch(i){
			case 1:
				formularioAtencionMedica.hide();
				formularioAtencionMedica=null;
				break;
			case 2:
				formularioPreclinica.hide();
				formularioPreclinica=null;
				break;
			case 3:
				formularioPaciente.hide();
				formularioPaciente=null;
				break;
			case 4:
				formularioEmpleados.hide();
				formularioEmpleados=null;
				break;
			case 5:
				if(formularioAdministradorMedicina.getOwner()!=null)
					c=1;
				formularioAdministradorMedicina.hide();
				formularioAdministradorMedicina=null;
				break;
			case 6:
				formularioCita.hide();
				formularioCita=null;
				return;
		}
		if(c==0)
			formularioPrincipal.show();
	}

	public void cambiarMedico(){
		System.out.println("Cambiar medicos");
		if (formularioMedico==null){

			try{
				formularioMedico=new Stage();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("VistaMedico.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				escenaMedico = new Scene(root);
				formularioMedico.setScene(escenaMedico);
				controladorMedico=loader.getController();
				controladorMedico.setMain(this);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		formularioMedico.show();
	}

	public void cambiarEnfermero(){
		if (escenaEnfermeros==null){
			try{
				formularioEnfermeros=new Stage();
				FXMLLoader loader = new FXMLLoader(getClass().getResource("VistaEnfermeros.fxml"));
				AnchorPane root = (AnchorPane)loader.load();
				escenaEnfermeros = new Scene(root);
				controladorEnfermeros=loader.getController();
				controladorEnfermeros.setMain(this);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		formularioEnfermeros.setScene(escenaEnfermeros);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
