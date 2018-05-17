package implementacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Examen {
	private IntegerProperty codigoExamen;
	private StringProperty nombreExamen;

	public Examen(int codigoExamen, String nombreExamen) {
		this.codigoExamen = new SimpleIntegerProperty(codigoExamen);
		this.nombreExamen = new SimpleStringProperty(nombreExamen);
	}

	public Examen(){

	}

	//Metodos atributo: codigoExamen
	public int getCodigoExamen() {
		return codigoExamen.get();
	}
	public void setCodigoExamen(int codigoExamen) {
		this.codigoExamen = new SimpleIntegerProperty(codigoExamen);
	}
	public IntegerProperty CodigoExamenProperty() {
		return codigoExamen;
	}
	//Metodos atributo: nombreExamen
	public String getNombreExamen() {
		return nombreExamen.get();
	}
	public void setNombreExamen(String nombreExamen) {
		this.nombreExamen = new SimpleStringProperty(nombreExamen);
	}
	public StringProperty NombreExamenProperty() {
		return nombreExamen;
	}

	public String toString(){
		return nombreExamen.get();
	}

	public static void llenar(Connection conexion, ObservableList<Examen> lista){
		try {
			Statement inst=conexion.createStatement();
			ResultSet resultados=inst.executeQuery("SELECT codigo_examen, nombre_examen FROM tbl_examenes");
			while(resultados.next())
				lista.add(new Examen(
						resultados.getInt("codigo_examen"),
						resultados.getString("nombre_examen")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int guardar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement(
					"INSERT INTO tbl_examenes"
					+ "(codigo_examen, nombre_examen) "
					+ "VALUES (?,?)");
			inst.setInt(1, codigoExamen.get());
			inst.setString(2, nombreExamen.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int eliminar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement(
					"DELETE FROM tbl_examenes WHERE codigo_examen=?");
			inst.setInt(1, codigoExamen.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int actualizar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("UPDATE tbl_examenes SET "
					+ "nombre_examen=? "
					+ "WHERE codigo_examen=?");
			inst.setInt(2, codigoExamen.get());
			inst.setString(1, nombreExamen.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public static int obtenerUltimo(Connection conexion){
		try {
			Statement inst=conexion.createStatement();
			ResultSet resultados=inst.executeQuery("SELECT MAX(codigo_examen) AS id FROM tbl_examenes");
			resultados.next();
			return resultados.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
