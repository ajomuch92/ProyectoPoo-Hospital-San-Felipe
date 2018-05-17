package application;

import java.net.URL;
import java.util.ResourceBundle;
import implementacion.Empleado;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utilerias.GestorConexiones;
import utilerias.Mensaje;

public class ControladorPrincipal implements Initializable{
	private Main main;

	@FXML private TextField txtUsuario;
	@FXML private PasswordField txtContrasenia;

	private GestorConexiones conexion;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		conexion=new GestorConexiones();
		conexion.establecerConexion();
		conexion.cerrarConexion();
	}

	public void setMain(Main main){
		this.main=main;
	}

	@FXML
	public void iniciarSesion(){
		Empleado usuario = new Empleado();
		try{
			usuario.setNumeroEmpleado(Integer.valueOf(txtUsuario.getText().trim()));
		}catch(NumberFormatException e){
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al iniciar sesion", "No se ingresó un número en el campo codigo usuario");
			return;
		}
		usuario.setContrasena(txtContrasenia.getText().trim());
		conexion.establecerConexion();
		if (usuario.autenticar(conexion.getConexion())){
			limpiar();
			switch(usuario.getArea().getCodigoArea()){
				case 1:
					main.paciente();
					break;
				case 2:
					main.preclinica(usuario);
					break;
				case 3:
					main.atencionMedica(usuario);
					break;
				case 4:
					main.empleado();
					break;
				case 5:
					main.administradorMedicina(0);
					break;
			}
		}
		else{
			Mensaje.mostrarMensaje(AlertType.ERROR, "Error", "Error al iniciar sesion", "Usuario/Password incorrectos");
		}

		conexion.cerrarConexion();

	}

	public void limpiar(){
		txtUsuario.setText("");
		txtContrasenia.setText("");
	}

	@FXML
	public void salir(){
		System.exit(0);
	}
}
