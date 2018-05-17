package implementacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Paciente extends Persona{
	private IntegerProperty numeroExpediente;
	private StringProperty nombrePadre;
	private StringProperty nombreMadre;
	private TipoSangre tipoSangre;


	public Paciente(String identidad, String nombre, String apellido, String genero, Date fechaNacimiento, int edad,
			String telefono, String celular, String direccion, int numeroExpediente, String nombrePadre, String nombreMadre,
			TipoSangre tipoSangre) {
		super(identidad, nombre, apellido, genero, fechaNacimiento, edad, telefono, celular, direccion);
		this.numeroExpediente = new SimpleIntegerProperty(numeroExpediente);
		this.nombrePadre = new SimpleStringProperty(nombrePadre);
		this.nombreMadre = new SimpleStringProperty(nombreMadre);
		this.tipoSangre=tipoSangre;
	}

	public Paciente(){
		super();
	}

	public int getNumeroExpediente() {
		return numeroExpediente.get();
	}
	public void setNumeroExpediente(int numeroExpediente) {
		this.numeroExpediente = new SimpleIntegerProperty(numeroExpediente);
	}
	public IntegerProperty NumeroExpedienteProperty() {
		return numeroExpediente;
	}
	//Metodos atributo: nombrePadre
	public String getNombrePadre() {
		return nombrePadre.get();
	}
	public void setNombrePadre(String nombrePadre) {
		this.nombrePadre = new SimpleStringProperty(nombrePadre);
	}
	public StringProperty NombrePadreProperty() {
		return nombrePadre;
	}
	//Metodos atributo: nombreMadre
	public String getNombreMadre() {
		return nombreMadre.get();
	}
	public void setNombreMadre(String nombreMadre) {
		this.nombreMadre = new SimpleStringProperty(nombreMadre);
	}
	public StringProperty NombreMadreProperty() {
		return nombreMadre;
	}
	//Metodos atributo: tipoSangre
	public TipoSangre getTipoSangre() {
		return tipoSangre;
	}
	public void setTipoSangre(TipoSangre tipoSangre) {
		this.tipoSangre = tipoSangre;
	}


	@Override
	public String toString() {
		return super.toString();
	}

	public static void llenar(Connection conexion, ObservableList<Paciente> lista){
		try {
			Statement inst=conexion.createStatement();
			ResultSet resultado=inst.executeQuery("SELECT "
					+ "a.numero_expediente, "
					+ "a.numero_identidad, "
					+ "a.nombre, "
					+ "a.apellido, "
					+ "a.genero, "
					+ "a.direccion, "
					+ "a.fecha_nacimiento, "
					+ "a.telefono_fijo, "
					+ "a.telefono_celular, "
					+ "a.nombre_padre, "
					+ "a.nombre_madre, "
					+ "a.codigo_tipo_sangre, "
					+ "b.tipo_sangre "
					+ "FROM tbl_pacientes a "
					+ "INNER JOIN tbl_tipos_sangre b "
					+ "ON (a.codigo_tipo_sangre=b.codigo_tipo_sangre)");
			while(resultado.next()){
				lista.add(new Paciente(
						resultado.getString("numero_identidad"),
						resultado.getString("nombre"),
						resultado.getString("apellido"),
						resultado.getString("genero"),
						resultado.getDate("fecha_nacimiento"),
						0,
						resultado.getString("telefono_fijo"),
						resultado.getString("telefono_celular"),
						resultado.getString("direccion"),
						resultado.getInt("numero_expediente"),
						resultado.getString("nombre_padre"),
						resultado.getString("nombre_madre"),
						new TipoSangre(
								resultado.getInt("codigo_tipo_sangre"),
								resultado.getString("tipo_sangre")
								)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int obtenerUltimo(Connection conexion){
		try {
			Statement inst=conexion.createStatement();
			ResultSet resultados=inst.executeQuery("SELECT MAX(numero_expediente) AS id FROM tbl_pacientes");
			resultados.next();
			return resultados.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int guardar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("INSERT INTO tbl_pacientes"
					+ "(numero_expediente, "
					+ "numero_identidad, "
					+ "nombre, "
					+ "apellido, "
					+ "genero, "
					+ "direccion, "
					+ "fecha_nacimiento, "
					+ "telefono_fijo, "
					+ "telefono_celular, "
					+ "nombre_padre, "
					+ "nombre_madre, "
					+ "codigo_tipo_sangre) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
			inst.setInt(1, numeroExpediente.get());
			inst.setString(2, super.getIdentidad());
			inst.setString(3, super.getNombre());
			inst.setString(4, super.getApellido());
			inst.setString(5, super.getGenero());
			inst.setString(6, super.getDireccion());
			inst.setDate(7, super.getFechaNacimiento());
			inst.setString(8, super.getTelefono());
			inst.setString(9, super.getCelular());
			inst.setString(10, nombrePadre.get());
			inst.setString(11, nombreMadre.get());
			inst.setInt(12, tipoSangre.getCodigoTipoSangre());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int actualizar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("UPDATE tbl_pacientes SET "
					+ "numero_identidad=?,"
					+ "nombre=?,"
					+ "apellido=?,"
					+ "genero=?,"
					+ "direccion=?,"
					+ "fecha_nacimiento=?,"
					+ "telefono_fijo=?,"
					+ "telefono_celular=?,"
					+ "nombre_padre=?,"
					+ "nombre_madre=?,"
					+ "codigo_tipo_sangre=? "
					+ "WHERE numero_expediente=?");
			inst.setString(1, super.getIdentidad());
			inst.setString(2, super.getNombre());
			inst.setString(3, super.getApellido());
			inst.setString(4, super.getGenero());
			inst.setString(5, super.getDireccion());
			inst.setDate(6, super.getFechaNacimiento());
			inst.setString(7, super.getTelefono());
			inst.setString(8, super.getCelular());
			inst.setString(9, nombrePadre.get());
			inst.setString(10, nombreMadre.get());
			inst.setInt(11, tipoSangre.getCodigoTipoSangre());
			inst.setInt(12, numeroExpediente.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int eliminar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("DELETE FROM tbl_pacientes WHERE numero_expediente=?");
			inst.setInt(1, numeroExpediente.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

}
