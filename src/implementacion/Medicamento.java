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

public class Medicamento{
	private IntegerProperty codigoMedicamento;
	private StringProperty nombreMedicamento;
	private Presentacion presentacion;

	public Medicamento(int codigoMedicamento, String nombreMedicamento, Presentacion presentacion) {
		this.codigoMedicamento = new SimpleIntegerProperty(codigoMedicamento);
		this.nombreMedicamento = new SimpleStringProperty(nombreMedicamento);
		this.presentacion = presentacion;
	}

	public Medicamento(){

	}

	//Metodos atributo: codigoMedicamento
	public int getCodigoMedicamento() {
		return codigoMedicamento.get();
	}
	public void setCodigoMedicamento(int codigoMedicamento) {
		this.codigoMedicamento = new SimpleIntegerProperty(codigoMedicamento);
	}
	public IntegerProperty CodigoMedicamentoProperty() {
		return codigoMedicamento;
	}
	//Metodos atributo: nombreMedicamento
	public String getNombreMedicamento() {
		return nombreMedicamento.get();
	}
	public void setNombreMedicamento(String nombreMedicamento) {
		this.nombreMedicamento = new SimpleStringProperty(nombreMedicamento);
	}
	public StringProperty NombreMedicamentoProperty() {
		return nombreMedicamento;
	}
	//Metodos atributo: presentacion
	public Presentacion getPresentacion() {
		return presentacion;
	}
	public void setPresentacion(Presentacion presentacion) {
		this.presentacion = presentacion;
	}

	@Override
	public String toString(){
		return nombreMedicamento.get()+"("+presentacion.toString()+")";
	}

	public static void llenarLista(Connection conexion,ObservableList<Medicamento> lista){
		try {
			Statement inst=conexion.createStatement();
			ResultSet resultados=inst.executeQuery("SELECT "
					+ "a.codigo_medicamento, "
					+ "a.nombre_medicamento, "
					+ "a.codigo_presentacion, "
					+ "b.presentacion "
					+ "FROM tbl_medicamentos a "
					+ "INNER JOIN tbl_presentacion b "
					+ "ON (a.codigo_presentacion=b.codigo_presentacion) ");
			while(resultados.next())
				lista.add(
						new Medicamento(
								resultados.getInt("codigo_medicamento"),
								resultados.getString("nombre_medicamento"),
								new Presentacion(
										resultados.getInt("codigo_presentacion"),
										resultados.getString("presentacion")
										)
								)
						);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int guardar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("INSERT INTO tbl_medicamentos("
					+ "codigo_medicamento, "
					+ "nombre_medicamento, "
					+ "codigo_presentacion) "
					+ "VALUES (?,?,?)");
			inst.setInt(1, codigoMedicamento.get());
			inst.setString(2, nombreMedicamento.get());
			inst.setInt(3, presentacion.getCodigoPresentacion());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int actualizar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("UPDATE tbl_medicamentos SET "
					+ "nombre_medicamento=?, "
					+ "codigo_presentacion=? "
					+ "WHERE codigo_medicamento=?");
			inst.setString(1, nombreMedicamento.get());
			inst.setInt(2, presentacion.getCodigoPresentacion());
			inst.setInt(3, codigoMedicamento.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int eliminar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("DELETE FROM tbl_medicamentos WHERE codigo_medicamento=?");
			inst.setInt(1, codigoMedicamento.get());
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
			ResultSet resultados=inst.executeQuery("SELECT MAX(codigo_medicamento) AS id FROM tbl_medicamentos");
			resultados.next();
			return resultados.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}