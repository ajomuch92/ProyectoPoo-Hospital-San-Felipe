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



public class Cita{
	private IntegerProperty codigoCita;
	private StringProperty hora;
	private Date fecha;
	private Paciente paciente;
	private Medico medico;
	private StringProperty estado;

	public Cita(int codigoCita, String hora, Date fecha,
			Paciente paciente, Medico medico, String estado) {
		this.codigoCita = new SimpleIntegerProperty(codigoCita);
		this.hora = new SimpleStringProperty(hora);
		this.fecha = fecha;
		this.paciente = paciente;
		this.medico = medico;
		this.estado = new SimpleStringProperty(estado);
	}

	public Cita(){

	}

	//Metodos atributo: codigoCita
	public int getCodigoCita() {
		return codigoCita.get();
	}
	public void setCodigoCita(int codigoCita) {
		this.codigoCita = new SimpleIntegerProperty(codigoCita);
	}
	public IntegerProperty CodigoCitaProperty() {
		return codigoCita;
	}
	//Metodos atributo: hora
	public String getHora() {
		return hora.get();
	}
	public void setHora(String hora) {
		this.hora = new SimpleStringProperty(hora);
	}
	public StringProperty HoraProperty() {
		return hora;
	}
	//Metodos atributo: fecha
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	//Metodos atributo: paciente
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	//Metodos atributo: Medico
	public Medico getMedico() {
		return medico;
	}
	public void setMedico(Medico Medico) {
		this.medico = Medico;
	}

	//Metodos atributo: estado
	public String getEstado() {
		return estado.get();
	}
	public void setEstado(String estado) {
		this.estado = new SimpleStringProperty(estado);
	}
	public StringProperty EstadoProperty() {
		return estado;
	}

	@Override
	public String toString() {
		return  paciente.toString()+"(Cita# "+String.valueOf(codigoCita.get())+")" ;
	}

	public static void llenar(Connection conexion,ObservableList<Cita> lista,int i){
		String where="";
		if(i>0)
			where="WHERE a.fecha=CURRENT_DATE AND estado!='FINALIZADO'";
		try {
			PreparedStatement inst=conexion.prepareStatement("UPDATE tbl_citas SET estado=? WHERE fecha<CURRENT_DATE AND estado!='FINALIZADO'");
			inst.setString(1, "No asistió");
			inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Statement inst=conexion.createStatement();
			ResultSet resultado=inst.executeQuery("SELECT "
					+ "a.codigo_cita, "
					+ "a.hora, "
					+ "a.fecha, "
					+ "a.numero_expediente, "
					+ "a.numero_empleado, "
					+ "a.estado, "
					+ "b.nombre, "
					+ "b.apellido, "
					+ "c.nombre nombrePaciente, "
					+ "c.apellido apellidoPaciente "
					+ "FROM tbl_citas a "
					+ "INNER JOIN tbl_empleados b "
					+ "ON(a.numero_empleado=b.numero_empleado) "
					+ "INNER JOIN tbl_pacientes c "
					+ "ON(a.numero_expediente=c.numero_expediente) "
					+ where);
			Medico m;
			Paciente p;
			Empleado em;
			while(resultado.next()){
				p=new Paciente();
				m=new Medico();
				em=new Empleado();
				em.setNumeroEmpleado(resultado.getInt("numero_empleado"));
				em.setNombre(resultado.getString("nombre"));
				em.setApellido(resultado.getString("apellido"));
				m.setEmpleado(em);
				p.setNumeroExpediente(resultado.getInt("numero_expediente"));
				p.setNombre(resultado.getString("nombrePaciente"));
				p.setApellido(resultado.getString("apellidoPaciente"));
				lista.add(new Cita(
						resultado.getInt("codigo_cita"),
						resultado.getString("hora"),
						resultado.getDate("fecha"),
						p,
						m,
						resultado.getString("estado")
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
			ResultSet resultados=inst.executeQuery("SELECT MAX(codigo_cita) AS id FROM tbl_citas");
			resultados.next();
			return resultados.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static int cambiarEstado(Connection conexion, int filtro){
		try {
			PreparedStatement inst=conexion.prepareStatement("UPDATE tbl_citas SET estado=? WHERE codigo_cita=?");
			inst.setString(1, "FINALIZADO");
			inst.setInt(2, filtro);
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int guardar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("INSERT INTO tbl_citas("
					+ "codigo_cita, "
					+ "hora, "
					+ "fecha, "
					+ "numero_expediente, "
					+ "numero_empleado, "
					+ "estado) "
					+ "VALUES (?,?,?,?,?,?)");
			inst.setInt(1, codigoCita.get());
			inst.setString(2, hora.get());
			inst.setDate(3, fecha);
			inst.setInt(4, paciente.getNumeroExpediente());
			inst.setInt(5, medico.getEmpleado().getNumeroEmpleado());
			inst.setString(6, estado.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int actualizar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("UPDATE tbl_citas SET "
					+ "hora=?, "
					+ "fecha=?, "
					+ "numero_expediente=?, "
					+ "numero_empleado=?, "
					+ "estado=? "
					+ "WHERE codigo_cita=?");
			inst.setString(1, hora.get());
			inst.setDate(2, fecha);
			inst.setInt(3, paciente.getNumeroExpediente());
			inst.setInt(4, medico.getEmpleado().getNumeroEmpleado());
			inst.setString(5, estado.get());
			inst.setInt(6, codigoCita.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int eliminar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("DELETE FROM tbl_citas WHERE codigo_cita=?");
			inst.setInt(1, codigoCita.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
}




