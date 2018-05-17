package implementacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Preclinica{
	private Cita cita;
	private Enfermero enfermero;
	private DoubleProperty temperatura;
	private DoubleProperty peso;
	private DoubleProperty altura;
	private StringProperty presion;

	public Preclinica(Cita cita, Enfermero enfermero, Double temperatura,
			Double peso, Double altura, String presion) {
		this.cita = cita;
		this.enfermero = enfermero;
		this.temperatura = new SimpleDoubleProperty(temperatura);
		this.peso = new SimpleDoubleProperty(peso);
		this.altura = new SimpleDoubleProperty(altura);
		this.presion = new SimpleStringProperty(presion);
	}

	public Preclinica(){

	}

	//Metodos atributo: cita
	public Cita getCita() {
		return cita;
	}
	public void setCita(Cita cita) {
		this.cita = cita;
	}
	//Metodos atributo: enfermero
	public Enfermero getEnfermero() {
		return enfermero;
	}
	public void setEnfermero(Enfermero enfermero) {
		this.enfermero = enfermero;
	}
	//Metodos atributo: temperatura
	public Double getTemperatura() {
		return temperatura.get();
	}
	public void setTemperatura(Double temperatura) {
		this.temperatura = new SimpleDoubleProperty(temperatura);
	}
	public DoubleProperty TemperaturaProperty() {
		return temperatura;
	}
	//Metodos atributo: peso
	public Double getPeso() {
		return peso.get();
	}
	public void setPeso(Double peso) {
		this.peso = new SimpleDoubleProperty(peso);
	}
	public DoubleProperty PesoProperty() {
		return peso;
	}
	//Metodos atributo: altura
	public Double getAltura() {
		return altura.get();
	}
	public void setAltura(Double altura) {
		this.altura = new SimpleDoubleProperty(altura);
	}
	public DoubleProperty AlturaProperty() {
		return altura;
	}
	//Metodos atributo: presion
	public String getPresion() {
		return presion.get();
	}
	public void setPresion(String presion) {
		this.presion = new SimpleStringProperty(presion);
	}
	public StringProperty PresionProperty() {
		return presion;
	}

	@Override
	public String toString(){
		return cita.toString();
	}

	public void llenar(Connection conexion,ObservableList<Preclinica> lista, int filtro){
		try {
			PreparedStatement inst=conexion.prepareStatement("SELECT "
					+ "a.codigo_cita, "
					+ "a.temperatura, "
					+ "a.peso, a.altura, "
					+ "a.presion, "
					+ "a.numero_empleado_enfermero,"
					+ "b.fecha, "
					+ "b.hora, "
					+ "c.nombre nombrePaciente, "
					+ "c.apellido apellidoPaciente, "
					+ "d.numero_empleado, "
					+ "d.nombre, "
					+ "d.apellido "
					+ "FROM tbl_preclinica a "
					+ "INNER JOIN tbl_citas b "
					+ "ON(a.codigo_cita=b.codigo_cita) "
					+ "INNER JOIN tbl_pacientes c "
					+ "ON(c.numero_expediente=b.numero_expediente) "
					+ "INNER JOIN tbl_empleados d "
					+ "ON(b.numero_empleado=d.numero_empleado) "
					+ "WHERE b.numero_empleado=? AND b.fecha=CURRENT_DATE AND a.estado='FINALIZADO'");  //b.fecha=CURRENT_DATE "+ "AND
			inst.setInt(1, filtro);
			ResultSet resultado=inst.executeQuery();
			cita=new Cita();
			enfermero=new Enfermero();
			Paciente p;
			while(resultado.next()){
				Statement instT=conexion.createStatement();
				ResultSet temp=instT.executeQuery("select "
						+ "a.numero_empleado_enfermero, "
						+ "b.nombre, "
						+ "b.apellido "
						+ "from tbl_preclinica a "
						+ "INNER JOIN tbl_empleados b "
						+ "ON(a.numero_empleado_enfermero=b.numero_empleado) "
						+ "WHERE numero_empleado_enfermero="
						+ resultado.getInt("numero_empleado_enfermero"));
				temp.next();
				Empleado em=new Empleado();
				em.setNumeroEmpleado(resultado.getInt("numero_empleado_enfermero"));
				em.setNombre(temp.getString("nombre"));
				em.setApellido(temp.getString("apellido"));
				enfermero.setEmpleado(em);
				em=new Empleado();
				Medico m=new Medico();
				em.setNumeroEmpleado(resultado.getInt("numero_empleado"));
				em.setNombre(resultado.getString("nombre"));
				em.setApellido(resultado.getString("apellido"));
				m.setEmpleado(em);
				p=new Paciente();
				p.setNombre(resultado.getString("nombrePaciente"));
				p.setApellido(resultado.getString("apellidoPaciente"));
				cita.setCodigoCita(resultado.getInt("codigo_cita"));
				cita.setPaciente(p);
				cita.setMedico(m);
				cita.setFecha(resultado.getDate("fecha"));
				cita.setHora(resultado.getString("hora"));
				lista.add(new Preclinica(cita,enfermero,
						resultado.getDouble("temperatura"),
						resultado.getDouble("peso"),
						resultado.getDouble("altura"),
						resultado.getString("presion")
						));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void llenar(Connection conexion,ObservableList<Preclinica> lista){
		try {
			PreparedStatement inst=conexion.prepareStatement("SELECT "
					+ "a.codigo_cita, "
					+ "a.temperatura, "
					+ "a.peso, a.altura, "
					+ "a.presion, "
					+ "a.numero_empleado_enfermero,"
					+ "b.fecha, "
					+ "b.hora, "
					+ "c.nombre nombrePaciente, "
					+ "c.apellido apellidoPaciente, "
					+ "d.numero_empleado, "
					+ "d.nombre, "
					+ "d.apellido "
					+ "FROM tbl_preclinica a "
					+ "INNER JOIN tbl_citas b "
					+ "ON(a.codigo_cita=b.codigo_cita) "
					+ "INNER JOIN tbl_pacientes c "
					+ "ON(c.numero_expediente=b.numero_expediente) "
					+ "INNER JOIN tbl_empleados d "
					+ "ON(b.numero_empleado=d.numero_empleado) ");
			ResultSet resultado=inst.executeQuery();
			cita=new Cita();
			enfermero=new Enfermero();
			Paciente p;
			while(resultado.next()){
				Statement instT=conexion.createStatement();
				ResultSet temp=instT.executeQuery("select "
						+ "a.numero_empleado_enfermero, "
						+ "b.nombre, "
						+ "b.apellido "
						+ "from tbl_preclinica a "
						+ "INNER JOIN tbl_empleados b "
						+ "ON(a.numero_empleado_enfermero=b.numero_empleado) "
						+ "WHERE numero_empleado_enfermero="
						+ resultado.getInt("numero_empleado_enfermero"));
				temp.next();
				Empleado em=new Empleado();
				em.setNumeroEmpleado(resultado.getInt("numero_empleado_enfermero"));
				em.setNombre(temp.getString("nombre"));
				em.setApellido(temp.getString("apellido"));
				enfermero.setEmpleado(em);
				em=new Empleado();
				Medico m=new Medico();
				em.setNumeroEmpleado(resultado.getInt("numero_empleado"));
				em.setNombre(resultado.getString("nombre"));
				em.setApellido(resultado.getString("apellido"));
				m.setEmpleado(em);
				p=new Paciente();
				p.setNombre(resultado.getString("nombrePaciente"));
				p.setApellido(resultado.getString("apellidoPaciente"));
				cita.setCodigoCita(resultado.getInt("codigo_cita"));
				cita.setPaciente(p);
				cita.setMedico(m);
				cita.setFecha(resultado.getDate("fecha"));
				cita.setHora(resultado.getString("hora"));
				lista.add(new Preclinica(cita,enfermero,
						resultado.getDouble("temperatura"),
						resultado.getDouble("peso"),
						resultado.getDouble("altura"),
						resultado.getString("presion")
						));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public static int obtenerUltimo(Connection conexion){
		try {
			Statement inst=conexion.createStatement();
			ResultSet resultados=inst.executeQuery("SELECT MAX(codigo_cita) AS id FROM tbl_preclinica");
			resultados.next();
			return resultados.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int guardar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("INSERT INTO tbl_preclinica("
					+ "codigo_cita, "
					+ "temperatura, "
					+ "peso, "
					+ "altura, "
					+ "presion, "
					+ "numero_empleado_enfermero, "
					+ "estado) "
					+ "VALUES (?,?,?,?,?,?,?)");
			inst.setInt(1, cita.getCodigoCita());
			inst.setDouble(2, temperatura.get());
			inst.setDouble(3, peso.get());
			inst.setDouble(4, altura.get());
			inst.setString(5, presion.get());
			inst.setInt(6, enfermero.getEmpleado().getNumeroEmpleado());
			inst.setString(7, "FINALIZADO");
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int actualizar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("UPDATE tbl_preclinica SET "
					+ "temperatura=?, "
					+ "peso=?, "
					+ "altura=?, "
					+ "presion=?, "
					+ "numero_empleado_enfermero=?, "
					+ "estado=? "
					+ "WHERE codigo_cita=?");
			inst.setDouble(1, temperatura.get());
			inst.setDouble(2, peso.get());
			inst.setDouble(3, altura.get());
			inst.setString(4, presion.get());
			inst.setInt(5, enfermero.getEmpleado().getNumeroEmpleado());
			inst.setString(6, "FINALIZADO");
			inst.setInt(7, cita.getCodigoCita());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int eliminar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("DELETE FROM tbl_preclinica WHERE codigo_cita=?");
			inst.setInt(1, cita.getCodigoCita());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
}