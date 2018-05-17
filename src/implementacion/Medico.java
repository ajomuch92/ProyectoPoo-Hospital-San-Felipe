package implementacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;


public class Medico  {
	private Especialidad especialidad;
	private IntegerProperty numeroColegioMedico;
	private IntegerProperty consultorio;
    private Empleado empleado;



	public Medico(Empleado empleado, int consultorio,int numeroColegioMedico,Especialidad especialidad) {
        this.empleado=empleado;
    	this.consultorio = new SimpleIntegerProperty(consultorio);
		this.especialidad = especialidad;
		this.numeroColegioMedico = new SimpleIntegerProperty(numeroColegioMedico);


	}

	public Medico(){

	}

	public Especialidad getEspecialidad() {
		return especialidad;
	}
	public void setEspecialidad(Especialidad especialidad) {
		this.especialidad = especialidad;
	}
	//Metodos atributo: numeroColegioMedico
	public int getNumeroColegioMedico() {
		return numeroColegioMedico.get();
	}
	public void setNumeroColegioMedico(int numeroColegioMedico) {
		this.numeroColegioMedico = new SimpleIntegerProperty(numeroColegioMedico);
	}
	public IntegerProperty NumeroColegioMedicoProperty() {
		return numeroColegioMedico;
	}
	//Metodos atributo: consultorio
	public int getConsultorio() {
		return consultorio.get();
	}
	public void setConsultorio(int consultorio) {
		this.consultorio = new SimpleIntegerProperty(consultorio);
	}
	public IntegerProperty ConsultorioProperty() {
		return consultorio;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}


	@Override
	public String toString() {
		return "Dr(a) :"+ empleado.toString();
	}


	public int guardar(Connection conexion)
	{
		try {
			PreparedStatement instruccion=conexion.prepareStatement(
					"INSERT INTO tbl_medico (numero_empleado, "+
			        "numero_colegio_medico, numero_consultorio, codigo_especialidad) VALUES (?, ?, ?, ?)"
							);
			instruccion.setInt(1, empleado.getNumeroEmpleado());
			instruccion.setInt(2, numeroColegioMedico.get());
			instruccion.setInt(3, consultorio.get());
			instruccion.setInt(4, especialidad.getCodigoEspecialidad());
			return instruccion.executeUpdate();
		} catch (SQLException e) {
					e.printStackTrace();
					return 0;

		}
	}

	public int eliminar(Connection conexion){
		try {
			PreparedStatement instruccion=conexion.prepareStatement("DELETE FROM tbl_empleados WHERE numero_empleado=?");
			instruccion.setInt(1, empleado.getNumeroEmpleado());
			return instruccion.executeUpdate();
		} catch (SQLException e) {
					e.printStackTrace();
					return 0;

		}

	}

	public int actualizar(Connection conexion){
		try {
			PreparedStatement instruccion=conexion.prepareStatement("UPDATE tbl_medico SET "
					+ "numero_colegio_medico=?, "
					+ "numero_consultorio=?, "
					+ "codigo_especialidad=? "
					+ "WHERE numero_empleado=?");
			instruccion.setInt(1, numeroColegioMedico.get());
			instruccion.setInt(2, consultorio.get());
			instruccion.setInt(3, especialidad.getCodigoEspecialidad());
			instruccion.setInt(4, empleado.getNumeroEmpleado());
			return instruccion.executeUpdate();
		} catch (SQLException e) {
					e.printStackTrace();
					return 0;

		}
	}

	public static void Llenar(ObservableList<Medico> Listamedica ,Connection conexion){
		try {
		Statement statement = conexion.createStatement();
		ResultSet resultado = statement.executeQuery
				(" SELECT B.numero_empleado,"+
						"B.numero_identidad, "+
						"B.nombre, "+
						"B.Apellido, "+
						"B.genero, "+
						"B.direccion ," +
						"B.fecha,B.contraseña,B.horario,B.telefono_fijo,B.telefono_celular,B.Edad, " +
						"C.nombre_area ,C.codigo_area, D.codigo_especialidad,D.nombre_especialidad, A.numero_consultorio,A.numero_colegio_medico "+
						"FROM tbl_medico A "
						+ "INNER JOIN tbl_empleados B "
						+ "ON(A.numero_empleado=B.numero_empleado) "
						+ "INNER JOIN tbl_area C "
						+ "ON(C.codigo_area=B.codigo_area) INNER JOIN tbl_especialidad D ON(D.codigo_especialidad=A.codigo_especialidad)"
			);

		while (resultado.next()) {
			Listamedica.add(new Medico(
					new Empleado(resultado.getInt("numero_empleado"),
							resultado.getString("numero_identidad"),
							resultado.getString("nombre"),
							resultado.getString("apellido"),
							resultado.getString("genero"),
	                        resultado.getDate("fecha"),
							resultado.getInt("Edad"),
							resultado.getString("telefono_fijo"),
	                        resultado.getString("telefono_celular"),
							resultado.getString("direccion"),
							resultado.getString("horario"),
							new Area(resultado.getInt("codigo_area"), resultado.getString("nombre_area")),
							resultado.getString("contraseña")),
				resultado.getInt("numero_consultorio"),resultado.getInt("numero_colegio_medico"),new Especialidad(resultado.getInt("codigo_especialidad"),resultado.getString("nombre_especialidad"),"")

			));

		}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void buscar(Connection conexion, int filtro){
		try {
			Statement statement = conexion.createStatement();
			ResultSet resultado = statement.executeQuery
					(" SELECT "
							+ "D.codigo_especialidad, "
							+ "D.nombre_especialidad, "
							+ "D.descripcion, "
							+ "A.numero_consultorio, "
							+ "A.numero_colegio_medico "
							+ "FROM tbl_medico A "
							+ "INNER JOIN tbl_empleados B "
							+ "ON(A.numero_empleado=B.numero_empleado) "
							+ "INNER JOIN tbl_especialidad D "
							+ "ON(D.codigo_especialidad=A.codigo_especialidad) "
							+ "WHERE A.numero_empleado="+String.valueOf(filtro)
				);
			resultado.first();
			setConsultorio(resultado.getInt("numero_consultorio"));
			setNumeroColegioMedico(resultado.getInt("numero_consultorio"));
			setEspecialidad(new Especialidad(resultado.getInt("codigo_especialidad"),
					resultado.getString("nombre_especialidad"),
					resultado.getString("descripcion")
					));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
