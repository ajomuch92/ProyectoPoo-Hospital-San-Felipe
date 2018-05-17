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

public class Diagnostico {
	private IntegerProperty codigoDiagnostico;
	private StringProperty nombreDiagnostico;

	public Diagnostico(int codigoDiagnostico, String nombreDiagnostico) {
		this.codigoDiagnostico = new SimpleIntegerProperty(codigoDiagnostico);
		this.nombreDiagnostico = new SimpleStringProperty(nombreDiagnostico);
	}

	public Diagnostico(){

	}

	//Metodos atributo: codigoDiagnostico
	public int getCodigoDiagnostico() {
		return codigoDiagnostico.get();
	}
	public void setCodigoDiagnostico(int codigoDiagnostico) {
		this.codigoDiagnostico = new SimpleIntegerProperty(codigoDiagnostico);
	}
	public IntegerProperty CodigoDiagnosticoProperty() {
		return codigoDiagnostico;
	}
	//Metodos atributo: nombreDiagnostico
	public String getNombreDiagnostico() {
		return nombreDiagnostico.get();
	}
	public void setNombreDiagnostico(String nombreDiagnostico) {
		this.nombreDiagnostico = new SimpleStringProperty(nombreDiagnostico);
	}
	public StringProperty NombreDiagnosticoProperty() {
		return nombreDiagnostico;
	}

	@Override
	public String toString(){
		return nombreDiagnostico.get();
	}

	public static void llenar(Connection conexion, ObservableList<Diagnostico> lista){
		try {
			Statement inst=conexion.createStatement();
			ResultSet resultados=inst.executeQuery("SELECT id_diagnostico, nombre_diagnostico FROM tbl_diagnostico");
			while(resultados.next())
				lista.add(new Diagnostico(
						resultados.getInt("id_diagnostico"),
						resultados.getString("nombre_diagnostico")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int guardar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement(
					  "INSERT INTO tbl_diagnostico "
					+ "(id_diagnostico, nombre_diagnostico) "
					+ "VALUES (?,?)");
			inst.setInt(1, codigoDiagnostico.get());
			inst.setString(2, nombreDiagnostico.get());
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
					"DELETE FROM tbl_diagnostico WHERE id_diagnostico=?");
			inst.setInt(1, codigoDiagnostico.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int actualizar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("UPDATE tbl_diagnostico SET "
					+ "nombre_diagnostico=? "
					+ "WHERE id_diagnostico=?");
			inst.setInt(2, codigoDiagnostico.get());
			inst.setString(1, nombreDiagnostico.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int obtenerUltimo(Connection conexion){
		try {
			Statement inst=conexion.createStatement();
			ResultSet resultados=inst.executeQuery("SELECT MAX(id_diagnostico) AS id FROM tbl_diagnostico");
			resultados.next();
			return resultados.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
