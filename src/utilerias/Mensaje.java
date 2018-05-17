package utilerias;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Mensaje {

	public static void mostrarMensaje(AlertType tipoMensaje, String titulo, String encabezado, String contenido){
		Alert mensaje = new Alert(tipoMensaje);
		mensaje.setHeaderText(encabezado);
		mensaje.setContentText(contenido);
		mensaje.setTitle(titulo);
		mensaje.show();
	}

}
