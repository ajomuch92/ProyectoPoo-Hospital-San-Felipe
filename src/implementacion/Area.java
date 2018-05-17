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

public class Area {
	private IntegerProperty codigoArea;
	private StringProperty nombreArea;

	public Area(int codigoArea, String nombreArea) {
		this.codigoArea = new SimpleIntegerProperty(codigoArea);
		this.nombreArea = new SimpleStringProperty(nombreArea);
	}

	public Area(){

	}

	//Metodos atributo: codigoArea
	public int getCodigoArea() {
		return codigoArea.get();
	}
	public void setCodigoArea(int codigoArea) {
		this.codigoArea = new SimpleIntegerProperty(codigoArea);
	}
	public IntegerProperty CodigoAreaProperty() {
		return codigoArea;
	}
	//Metodos atributo: nombreArea
	public String getNombreArea() {
		return nombreArea.get();
	}
	public void setNombreArea(String nombreArea) {
		this.nombreArea = new SimpleStringProperty(nombreArea);
	}
	public StringProperty NombreAreaProperty() {
		return nombreArea;
	}

	public String toString(){
		return nombreArea.get();
	}

	public static void llenar(Connection conexion, ObservableList<Area> lista){
		try {
			Statement inst=conexion.createStatement();
			ResultSet resultados=inst.executeQuery("SELECT codigo_area, nombre_area FROM tbl_area");
			while(resultados.next())
				lista.add(new Area(
						resultados.getInt("codigo_area"),
						resultados.getString("nombre_area")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int guardar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement(
					"INSERT INTO tbl_area(codigo_area, nombre_area) "
					+ "VALUES (?,?)");
			inst.setInt(1, codigoArea.get());
			inst.setString(2, nombreArea.get());
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
					"DELETE FROM tbl_area WHERE ?");
			inst.setInt(1, codigoArea.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int actualizar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("UPDATE tbl_area SET "
					+ "nombre_area=? "
					+ "WHERE ?");
			inst.setInt(2, codigoArea.get());
			inst.setString(1, nombreArea.get());
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
			ResultSet resultados=inst.executeQuery("SELECT MAX(codigo_area) AS id FROM tbl_area");
			resultados.next();
			return resultados.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
