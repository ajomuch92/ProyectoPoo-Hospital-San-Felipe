package implementacion;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

public class Enfermero {
	private IntegerProperty numeroColegioEnfermeria;
	private Empleado empleado;

	public Enfermero(Empleado empleado, int numeroColegioEnfermeria) {
		this.empleado = empleado;
		this.numeroColegioEnfermeria = new SimpleIntegerProperty(numeroColegioEnfermeria);

	}

	public Enfermero(){

	}

	public int getNumeroColegioEnfermeria() {
		return numeroColegioEnfermeria.get();
	}

	public void setNumeroColegioEnfermeria(int numeroColegioEnfermeria) {
		this.numeroColegioEnfermeria = new SimpleIntegerProperty(numeroColegioEnfermeria);
	}

	public IntegerProperty NumeroColegioEnfermeriaProperty() {
		return numeroColegioEnfermeria;
	}

	public static void llenar(ObservableList<Enfermero> ListaEnfermero, Connection conexion) {
		try {
			Statement statement = conexion.createStatement();
			ResultSet resultado = statement.executeQuery("SELECT "
					+ "B.numero_empleado, "
					+ "B.nombre,"
					+ "B.apellido "
					+ "FROM tbl_enfermeros A "
					+ "INNER JOIN tbl_empleados B "
					+ "ON(A.numero_empleado=B.numero_empleado)");
			while (resultado.next()) {
				Empleado e=new Empleado();
				e.setNumeroEmpleado(resultado.getInt("numero_empleado"));
				e.setNombre(resultado.getString("nombre"));
				e.setApellido(resultado.getString("apellido"));
				Enfermero en=new Enfermero();
				en.setEmpleado(e);
				ListaEnfermero.add(en);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public int Guardar(Connection conexion) {
		try {
			PreparedStatement instruccion = conexion.prepareStatement(
					"INSERT INTO tbl_enfermeros (numero_empleado, numero_colegio_enfermeria) VALUES (?, ?);");

			instruccion.setInt(1, empleado.getNumeroEmpleado());
			instruccion.setInt(2, numeroColegioEnfermeria.get());

			return instruccion.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
			return 0;
		}
	}

	public int eliminar(Connection conexion){
		try {
			PreparedStatement instruccion=conexion.prepareStatement("DELETE FROM tbl_enfermeros WHERE numero_empleado=?");
			instruccion.setInt(1, empleado.getNumeroEmpleado());
			return instruccion.executeUpdate();
		} catch (SQLException e) {
					e.printStackTrace();
					return 0;

		}

	}

	public int actualizar(Connection conexion){
		try {
			PreparedStatement instruccion=conexion.prepareStatement("UPDATE tbl_enfermeros SET numero_colegio_enfermeria=? WHERE numero_empleado=?");
			instruccion.setInt(1, numeroColegioEnfermeria.get());
			instruccion.setInt(2, empleado.getNumeroEmpleado());
			return instruccion.executeUpdate();
		} catch (SQLException e) {
					e.printStackTrace();
					return 0;

		}
	}

	public void buscar(Connection conexion, int filtro){
		try {
			Statement statement = conexion.createStatement();
			ResultSet resultado = statement.executeQuery
					(" SELECT "
							+ "A.numero_empleado, "
							+ "A.numero_colegio_enfermeria "
							+ "FROM tbl_enfermeros A "
							+ "WHERE numero_empleado="+String.valueOf(filtro)
				);
			resultado.first();
			setNumeroColegioEnfermeria(resultado.getInt("numero_colegio_enfermeria"));

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	public String toString() {
		return empleado.toString();
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

}
