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

public class TipoSangre{
	private IntegerProperty codigoTipoSangre;
	private StringProperty nombreTipoSangre;

	public TipoSangre(int codigoTipoSangre, String nombreTipoSangre) {
		this.codigoTipoSangre = new SimpleIntegerProperty(codigoTipoSangre);
		this.nombreTipoSangre = new SimpleStringProperty(nombreTipoSangre);
	}

	public TipoSangre(){

	}

	//Metodos atributo: codigoTipoSangre
	public int getCodigoTipoSangre() {
		return codigoTipoSangre.get();
	}
	public void setCodigoTipoSangre(int codigoTipoSangre) {
		this.codigoTipoSangre = new SimpleIntegerProperty(codigoTipoSangre);
	}
	public IntegerProperty CodigoTipoSangreProperty() {
		return codigoTipoSangre;
	}
	//Metodos atributo: nombreTipoSangre
	public String getNombreTipoSangre() {
		return nombreTipoSangre.get();
	}
	public void setNombreTipoSangre(String nombreTipoSangre) {
		this.nombreTipoSangre = new SimpleStringProperty(nombreTipoSangre);
	}
	public StringProperty NombreTipoSangreProperty() {
		return nombreTipoSangre;
	}

	@Override
	public String toString(){
		return nombreTipoSangre.get();
	}

	public static void llenar(Connection conexion, ObservableList<TipoSangre> lista){
		try {
			Statement inst=conexion.createStatement();
			ResultSet resultado=inst.executeQuery("SELECT codigo_tipo_sangre, tipo_sangre FROM tbl_tipos_sangre");
			while(resultado.next()){
				lista.add(new TipoSangre(
						resultado.getInt("codigo_tipo_sangre"),
						resultado.getString("tipo_sangre")
						));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public int guardar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("INSERT INTO tbl_tipos_sangre("
					+ "codigo_tipo_sangre, "
					+ "tipo_sangre) "
					+ "VALUES (?,?)");
			inst.setInt(1, codigoTipoSangre.get());
			inst.setString(2, nombreTipoSangre.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int eliminar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("DELETE FROM tbl_tipos_sangre WHERE codigo_tipo_sangre=?");
			inst.setInt(1, codigoTipoSangre.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int actualizar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("UPDATE tbl_tipos_sangre SET "
					+ "tipo_sangre=? "
					+ "WHERE "
					+ "codigo_tipo_sangre=?");
			inst.setString(1, nombreTipoSangre.get());
			inst.setInt(2, codigoTipoSangre.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int obtenerUltimo(Connection conexion){
		try {
			Statement inst=conexion.createStatement();
			ResultSet resultados=inst.executeQuery("SELECT MAX(codigo_tipo_sangre) AS id FROM tbl_tipos_sangre");
			resultados.next();
			return resultados.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
