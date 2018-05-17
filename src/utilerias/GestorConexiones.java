package utilerias;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestorConexiones {
	private String url = "jdbc:mysql://localhost/db_hospital"; //jdbc:mysql://direccion_ip/nombre_base_de_datos
	private Connection conexion;


	public Connection getConexion(){
		return this.conexion;
	}

	public void establecerConexion(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conexion = DriverManager.getConnection(url, "root", "");//Si pasa de esta linea se pudo conectar correctamente
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void cerrarConexion(){
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}