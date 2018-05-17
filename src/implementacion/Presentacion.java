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

public class Presentacion{
	private IntegerProperty codigoPresentacion;
	private StringProperty presentacion;

	public Presentacion(int codigoPresentacion, String presentacion) {
		this.codigoPresentacion = new SimpleIntegerProperty(codigoPresentacion);
		this.presentacion = new SimpleStringProperty(presentacion);
	}

	public Presentacion(){

	}

	//Metodos atributo: codigoPresentacion
	public int getCodigoPresentacion() {
		return codigoPresentacion.get();
	}
	public void setCodigoPresentacion(int codigoPresentacion) {
		this.codigoPresentacion = new SimpleIntegerProperty(codigoPresentacion);
	}
	public IntegerProperty CodigoPresentacionProperty() {
		return codigoPresentacion;
	}
	//Metodos atributo: presentacion
	public String getPresentacion() {
		return presentacion.get();
	}
	public void setPresentacion(String presentacion) {
		this.presentacion = new SimpleStringProperty(presentacion);
	}
	public StringProperty PresentacionProperty() {
		return presentacion;
	}

	@Override
	public String toString(){
		return presentacion.get();
	}

	public static void llenarLista(Connection conexion, ObservableList<Presentacion> lista){
		try {
			Statement inst=conexion.createStatement();
			ResultSet resultado=inst.executeQuery("SELECT codigo_presentacion, presentacion FROM tbl_presentacion");
			while(resultado.next()){
				lista.add(new Presentacion(
						resultado.getInt("codigo_presentacion"),
						resultado.getString("presentacion")
						)
				);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int guardar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("INSERT INTO tbl_presentacion("
					+ "codigo_presentacion, "
					+ "presentacion) "
					+ "VALUES (?,?)");
			inst.setInt(1, codigoPresentacion.get());
			inst.setString(2, presentacion.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}

	public int actualizar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("UPDATE tbl_presentacion SET "
					+ "presentacion=? "
					+ "WHERE codigo_presentacion=?");
			inst.setInt(2, codigoPresentacion.get());
			inst.setString(1, presentacion.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int eliminar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("DELETE FROM tbl_presentacion WHERE codigo_presentacion=?");
			inst.setInt(1, codigoPresentacion.get());
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
			ResultSet resultados=inst.executeQuery("SELECT MAX(codigo_presentacion) AS id FROM tbl_presentacion");
			resultados.next();
			return resultados.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
