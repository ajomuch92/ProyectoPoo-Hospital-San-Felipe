package implementacion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Empleado extends Persona {

	protected IntegerProperty numeroEmpleado;
	private StringProperty horario;
	protected Area area;
	protected StringProperty contrasena;

	public Empleado( int numeroEmpleado,String identidad, String nombre, String apellido,String genero, Date fechaNacimiento, int edad,
			String telefono, String celular, String direccion, String horario, Area area,String contrasena) {
		super(identidad, nombre, apellido,genero,fechaNacimiento, edad, telefono, celular, direccion);
		this.numeroEmpleado = new SimpleIntegerProperty(numeroEmpleado);
		this.horario = new SimpleStringProperty(horario);
		this.area = area;
		this.contrasena=new SimpleStringProperty(contrasena);
	}

	public Empleado(){

	}

	public int getNumeroEmpleado() {
		return numeroEmpleado.get();
	}
	public void setNumeroEmpleado(int numeroEmpleado) {
		this.numeroEmpleado = new SimpleIntegerProperty(numeroEmpleado);
	}
	public IntegerProperty NumeroEmpleadoProperty() {
		return numeroEmpleado;
	}
	//Metodos atributo: horario
	public String getHorario() {
		return horario.get();
	}
	public void setHorario(String horario) {
		this.horario = new SimpleStringProperty(horario);
	}
	public StringProperty HorarioProperty() {
		return horario;
	}
	//Metodos atributo: area
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}

	//Metodos atributo: contrasena
	public String getContrasena() {
		return contrasena.get();
	}
	public void setContrasena(String contrasena) {
		this.contrasena = new SimpleStringProperty(contrasena);
	}
	public StringProperty ContrasenaProperty() {
		return contrasena;
	}

	public static void llenarInformacion(ObservableList<Empleado> ListaEmpleados,Connection conexion ){
		try {
		Statement statement =conexion.createStatement();
		ResultSet resultado =statement.executeQuery(
				"SELECT A.numero_empleado, "
				+ "A.numero_identidad, "
				+ "A.nombre, "
				+ "A.apellido, "
				+ "A.genero, "
				+ "A.direccion, "
				+ "A.fecha, "
				+ "A.telefono_fijo, "
				+ "A.telefono_celular, "
				+ "A.horario, "
				+ "A.contraseña, "
				+ "A.Edad, "
				+ "B.codigo_area, "
				+ "B.nombre_area "
				+ "FROM tbl_empleados A "
				+ "INNER JOIN tbl_area B "
				+ "ON(A.codigo_area= B.codigo_area) "

				);

		while(resultado.next()){
			ListaEmpleados.add(new Empleado(resultado.getInt("numero_empleado"),
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

				new Area(resultado.getInt("codigo_area"),resultado.getString("nombre_area")
						),resultado.getString("contraseña")
				)
				);


		}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public int Guardar(Connection conexion){
		try {
			PreparedStatement instruccion =conexion.prepareStatement(
			"INSERT INTO tbl_empleados " +
			"(numero_empleado, "+
			"numero_identidad, "+
			"nombre, "+
		    "apellido, " +
			"direccion, "+

			"fecha, "+
			"telefono_fijo, " +
			"telefono_celular, "+
			"horario, " +
			"codigo_area, "+
			"contraseña, "+
			"genero, "+
			"Edad) " +
			" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?, sha1(?), ?,?)"
			);

			instruccion.setInt(1, numeroEmpleado.get());
			instruccion.setString(2, super.getIdentidad());
            instruccion.setString(3, super.getNombre());
            instruccion.setString(4, super.getApellido());
            instruccion.setString(5,super.getDireccion());
            instruccion.setDate(6, super.getFechaNacimiento());
            instruccion.setString(7,super.getTelefono());
            instruccion.setString(8,super.getCelular());

            instruccion.setString(9,horario.get());
            instruccion.setInt(10,area.getCodigoArea());
            instruccion.setString(11,contrasena.get());
             instruccion.setString(12, super.getGenero());
             instruccion.setInt(13, super.getEdad());
             return instruccion.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public int eliminar (Connection conexion){
		try {
			PreparedStatement Instruccion=conexion.prepareStatement(
				"	DELETE FROM tbl_empleados " +
					"WHERE numero_empleado =?"
						);

			Instruccion.setInt(1, numeroEmpleado.get());
			return Instruccion.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}


	public int actualizarRegistro(Connection conexion){
		 try {
			PreparedStatement instruccion =
					conexion.prepareStatement(
							"UPDATE tbl_empleados SET  "
							+ "numero_identidad = ?,"
							+ " nombre = ?,"
							+ " apellido= ?, "
							+ "direccion = ?, "
							+ "fecha = ?, "
							+ "telefono_fijo= ?, "
							+ "telefono_celular = ?, "
							+ "horario = ?,"
							+ "codigo_area =?, "
							+ " Edad = ?, "
							+ " genero = ? "
							+ " WHERE numero_empleado = ?  "
					);

			instruccion.setString(1, super.getIdentidad());
            instruccion.setString(2, super.getNombre());
            instruccion.setString(3, super.getApellido());
            instruccion.setString(4,super.getDireccion());
            instruccion.setDate(5, super.getFechaNacimiento());
            instruccion.setString(6,super.getTelefono());
            instruccion.setString(7,super.getCelular());

            instruccion.setString(8,horario.get());
            instruccion.setInt(9,area.getCodigoArea());
            instruccion.setInt(10, super.getEdad());
            instruccion.setString(11, super.getGenero());
            instruccion.setInt(12, numeroEmpleado.get());
			return instruccion.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	 }

	public int actualizarRegistroContraseña(Connection conexion){
		 try {
				PreparedStatement instruccion =
						conexion.prepareStatement(
								"UPDATE tbl_empleados SET  "
								+ "numero_identidad = ?,"
								+ " nombre = ?,"
								+ " apellido= ?, "
								+ "direccion = ?, "
								+ "fecha = ?, "
								+ "telefono_fijo= ?, "
								+ "telefono_celular = ?, "
								+ "horario = ?,"
								+ "codigo_area =?, "
								+ "contraseña = sha1(?), "
								+ " Edad = ?, "
								+ " genero = ? "
								+ " WHERE numero_empleado = ?  "
						);

				instruccion.setString(1, super.getIdentidad());
	            instruccion.setString(2, super.getNombre());
	            instruccion.setString(3, super.getApellido());
	            instruccion.setString(4,super.getDireccion());
	            instruccion.setDate(5, super.getFechaNacimiento());
	            instruccion.setString(6,super.getTelefono());
	            instruccion.setString(7,super.getCelular());

	            instruccion.setString(8,horario.get());
	            instruccion.setInt(9,area.getCodigoArea());
	            instruccion.setString(10,contrasena.get());

	            instruccion.setInt(11, super.getEdad());
	            instruccion.setString(12, super.getGenero());
	            instruccion.setInt(13, numeroEmpleado.get());
				return instruccion.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
	}


	public boolean autenticar(Connection conexion){
		try {
			PreparedStatement instruccion =
					conexion.prepareStatement("SELECT numero_empleado, "
							+ "numero_identidad, "
							+ "nombre, "
							+ "apellido, "
							+ "direccion, "
							+ "fecha, "
							+ "telefono_fijo, "
							+ "telefono_celular, "
							+ "horario, "
							+ "codigo_area, "
							+ "contraseña "
							+ "FROM tbl_empleados "
							+ "WHERE numero_empleado=? "
							+ "AND contraseña=sha1(?)"
			);
			instruccion.setInt(1, numeroEmpleado.get());
			instruccion.setString(2, contrasena.get());
			ResultSet resultado = instruccion.executeQuery();
			if (resultado.first()){
				area=new Area();
				area.setCodigoArea(resultado.getInt("codigo_area"));
				super.setNombre(resultado.getString("nombre"));
				super.setApellido(resultado.getString("apellido"));
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static int obtenerUltimo(Connection conexion){
		try {
			Statement inst=conexion.createStatement();
			ResultSet resultados=inst.executeQuery("SELECT MAX(numero_empleado) AS id FROM tbl_empleados");
			resultados.next();
			return resultados.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
