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
import javafx.scene.chart.XYChart.Data;

public class Historial{
	private IntegerProperty codigoHistorial;
	private Preclinica preclinica; //
	private Examen examen;//
	private Medicamento medicamento;//
	private Diagnostico diagnostico;//
	private Date fecha;
	private Cita cita;
	private Medico medico;
	private Enfermero enfermero;
	private StringProperty observacion;

	public Historial(int codigoHistorial, Preclinica preclinica, Examen examen,
		Medicamento medicamento, Diagnostico diagnostico, Cita cita,
		Medico medico, Enfermero enfermero, Date fecha, String observacion) {
		this.codigoHistorial = new SimpleIntegerProperty(codigoHistorial);
		this.preclinica = preclinica;
		this.examen = examen;
		this.medicamento = medicamento;
		this.diagnostico = diagnostico;
		this.cita = cita;
		this.medico = medico;
		this.enfermero = enfermero;
		this.fecha=fecha;
		this.observacion = new SimpleStringProperty(observacion);
	}

	public Historial(){

	}

	//Metodos atributo: codigoHistorial
	public int getCodigoHistorial() {
		return codigoHistorial.get();
	}
	public void setCodigoHistorial(int codigoHistorial) {
		this.codigoHistorial = new SimpleIntegerProperty(codigoHistorial);
	}
	public IntegerProperty CodigoHistorialProperty() {
		return codigoHistorial;
	}
	//Metodos atributo: preclinica
	public Preclinica getPreclinica() {
		return preclinica;
	}
	public void setPreclinica(Preclinica preclinica) {
		this.preclinica = preclinica;
	}
	//Metodos atributo: examen
	public Examen getExamen() {
		return examen;
	}
	public void setExamen(Examen examen) {
		this.examen = examen;
	}
	//Metodos atributo: medicamento
	public Medicamento getMedicamento() {
		return medicamento;
	}
	public void setMedicamento(Medicamento medicamento) {
		this.medicamento = medicamento;
	}
	//Metodos atributo: diagnostico
	public Diagnostico getDiagnostico() {
		return diagnostico;
	}
	public void setDiagnostico(Diagnostico diagnostico) {
		this.diagnostico = diagnostico;
	}
	//Metodos atributo: cita
	public Cita getCita() {
		return cita;
	}
	public void setCita(Cita cita) {
		this.cita = cita;
	}
	//Metodos atributo: medico
	public Medico getMedico() {
		return medico;
	}
	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	//Metodos atributo: enfermero
	public Enfermero getEnfermero() {
		return enfermero;
	}
	public void setEnfermero(Enfermero enfermero) {
		this.enfermero = enfermero;
	}

	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	//Metodos atributo: observacion
	public String getObservacion() {
		return observacion.get();
	}
	public void setObservacion(String observacion) {
		this.observacion = new SimpleStringProperty(observacion);
	}
	public StringProperty ObservacionProperty() {
		return observacion;
	}

	public static void llenar(Connection conexion, ObservableList<Historial> lista){
		try {
			Statement inst=conexion.createStatement();
			ResultSet resultado=inst.executeQuery("select "+
					"a.codigo_historial, "+
				    "a.codigo_cita, "+
				    "a.codigo_examen, "+
				    "a.codigo_medicamento, "+
				    "a.id_diagnostico, "+
				    "a.observacion, "+
					"b.nombre_diagnostico, "+
				    "c.nombre_examen, "+
				    "d.nombre_medicamento, "+
				    "d.codigo_presentacion, "+
				    "e.presentacion, "+
				    "f.temperatura, "+
				    "f.peso, "+
				    "f.altura, "+
				    "f.presion, "+
				    "g.hora, "+
				    "g.fecha, "+
				    "h.nombre as nombrePaciente, "+
				    "h.apellido as apellidoPaciente,"+
				    "l.nombre, "+
				    "l.apellido, "+
				    "n.numero_colegio_enfermeria "+
				    "from tbl_historial a "+
				    "inner join tbl_diagnostico b "+
				    "on (b.id_diagnostico=a.id_diagnostico) "+
				    "inner join tbl_examenes c "+
				    "on (c.codigo_examen=a.codigo_examen) "+
				    "inner join tbl_medicamentos d "+
				    "on (d.codigo_medicamento=a.codigo_medicamento) "+
				    "inner join tbl_presentacion e "+
				    "on (e.codigo_presentacion=d.codigo_presentacion) "+
				    "inner join tbl_preclinica f "+
				    "on (a.codigo_cita=f.codigo_cita) "+
				    "inner join tbl_citas g "+
				    "on (a.codigo_cita=g.codigo_cita) "+
				    "inner join tbl_pacientes h "+
				    "on (g.numero_expediente=h.numero_expediente) "+
				    "inner join tbl_medico k "+
				    "on (g.numero_empleado=k.numero_empleado) "+
				    "inner join tbl_especialidad j "+
				    "on (k.codigo_especialidad=j.codigo_especialidad) "+
				    "inner join tbl_empleados l "+
				    "on (k.numero_empleado=l.numero_empleado) "+
				    "inner join tbl_area m "+
				    "on (l.codigo_area=m.codigo_area) "+
				    "inner join tbl_enfermeros n "+
				    "on (f.numero_empleado_enfermero=n.numero_empleado)");

			while(resultado.next()){
				Statement instT=conexion.createStatement();
				ResultSet r=instT.executeQuery("SELECT "
						+ "a.codigo_cita, "
						+ "c.nombre, "
						+ "c.apellido "
						+ "FROM tbl_historial a "
						+ "INNER JOIN tbl_preclinica b "
						+ "ON(a.codigo_cita=b.codigo_cita) "
						+ "INNER JOIN tbl_enfermeros d "
						+ "ON(b.numero_empleado_enfermero=d.numero_empleado) "
						+ "INNER JOIN tbl_empleados c "
						+ "ON(c.numero_empleado=b.numero_empleado_enfermero) "
						+ "WHERE d.numero_colegio_enfermeria="
						+ resultado.getInt("numero_colegio_enfermeria"));
				r.next();
				Empleado em2=new Empleado();
				em2.setNombre(r.getString("nombre"));
				em2.setApellido(r.getString("apellido"));
				Medico m=new Medico();
				m.setEmpleado(em2);
				Paciente pa=new Paciente();
				pa.setNombre(resultado.getString("nombrePaciente"));
				pa.setApellido(resultado.getString("apellidoPaciente"));
				Cita c=new Cita();
				c.setCodigoCita(resultado.getInt("codigo_cita"));
				c.setFecha(resultado.getDate("fecha"));
				c.setHora(resultado.getString("hora"));
				c.setMedico(m);
				c.setPaciente(pa);
				Empleado em1=new Empleado();
				em1.setNombre(r.getString("nombre"));
				em1.setApellido(r.getString("apellido"));
				Enfermero e=new Enfermero();
				e.setNumeroColegioEnfermeria(resultado.getInt("numero_colegio_enfermeria"));
				e.setEmpleado(em1);
				Preclinica p=new Preclinica(c,
						e,
						resultado.getDouble("temperatura"),
						resultado.getDouble("peso"),
						resultado.getDouble("altura"),
						resultado.getString("presion")
						);
				lista.add(new Historial(
						resultado.getInt("codigo_historial"),
						p,
						new Examen(resultado.getInt("codigo_examen"),resultado.getString("nombre_examen")),
						new Medicamento(resultado.getInt("codigo_medicamento"),resultado.getString("nombre_medicamento"),
								new Presentacion(resultado.getInt("codigo_presentacion"),resultado.getString("presentacion"))),
						new Diagnostico(resultado.getInt("id_diagnostico"),resultado.getString("nombre_diagnostico")),
						c,
						m,
						e,
						resultado.getDate("fecha"),
						resultado.getString("observacion")
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
			ResultSet resultados=inst.executeQuery("SELECT MAX(codigo_historial) AS id FROM tbl_historial");
			resultados.next();
			return resultados.getInt("id");
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int guardar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("INSERT INTO tbl_historial("
					+ "codigo_historial, "
					+ "codigo_cita, "
					+ "codigo_examen, "
					+ "codigo_medicamento, "
					+ "id_diagnostico, "
					+ "fecha, "
					+ "observacion) "
					+ "VALUES (?,?,?,?,?,CURRENT_DATE,?)");
			inst.setInt(1, codigoHistorial.get());
			inst.setInt(2, cita.getCodigoCita());
			inst.setInt(3, examen.getCodigoExamen());
			inst.setInt(4, medicamento.getCodigoMedicamento());
			inst.setInt(5, diagnostico.getCodigoDiagnostico());
			inst.setString(6,observacion.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public int actualizar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("UPDATE tbl_historial SET "
					+ "codigo_cita=?, "
					+ "codigo_examen=?, "
					+ "codigo_medicamento=?, "
					+ "id_diagnostico=?, "
					+ "fecha=CURRENT_DATE, "
					+ "observacion=? "
					+ "WHERE codigo_historial=?");
			inst.setInt(1, cita.getCodigoCita());
			inst.setInt(2, examen.getCodigoExamen());
			inst.setInt(3, medicamento.getCodigoMedicamento());
			inst.setInt(4, diagnostico.getCodigoDiagnostico());
			inst.setString(6,observacion.get());
			inst.setInt(7, codigoHistorial.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public  int eliminar(Connection conexion){
		try {
			PreparedStatement inst=conexion.prepareStatement("DELETE FROM tbl_historial WHERE codigo_historial=?");
			inst.setInt(1, codigoHistorial.get());
			return inst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public static void llenarInformacionGrafico(Connection conexion, ObservableList<Data<String, Integer>> lista, int cod)
	{
		try {
			Statement instruccion = conexion.createStatement();
			ResultSet resultado = instruccion.executeQuery("SELECT "
					+ "a.fecha, "
					+ "COUNT(*) AS CANTIDAD_PACIENTES "
					+ "FROM tbl_historial a "
					+ "INNER JOIN tbl_citas b "
					+ "ON (b.codigo_cita=a.codigo_cita) "
					+ "INNER JOIN tbl_medico c "
					+ "ON (c.numero_empleado=b.numero_empleado) "
					+ "WHERE c.numero_empleado="+String.valueOf(cod)
					+ " GROUP BY a.fecha ORDER BY a.fecha");
			while (resultado.next()){
				lista.add(new Data<String,Integer>(
						resultado.getDate("FECHA").toString(),
						resultado.getInt("CANTIDAD_PACIENTES")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void llenarInformacionGraficoPacientesAtendidos(Connection conexion, ObservableList<Data<String, Integer>> lista)
	{
		try {
			Statement instruccion = conexion.createStatement();
			ResultSet resultado = instruccion.executeQuery("SELECT "
					+ "a.fecha, "
					+ "COUNT(*) AS CANTIDAD_PACIENTES "
					+ "FROM tbl_historial a "
					+ "INNER JOIN tbl_citas b "
					+ "ON (b.codigo_cita=a.codigo_cita) "
					+ "INNER JOIN tbl_medico c "
					+ "ON (c.numero_empleado=b.numero_empleado) "
					+ " GROUP BY a.fecha ORDER BY a.fecha");
			while (resultado.next()){
				lista.add(new Data<String,Integer>(
						resultado.getDate("FECHA").toString(),
						resultado.getInt("CANTIDAD_PACIENTES")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void llenarInformacionGraficoEnfermedades(Connection conexion, ObservableList<Data<String, Integer>> lista)
	{
		try {
			Statement instruccion = conexion.createStatement();
			ResultSet resultado = instruccion.executeQuery("SELECT "
					+ "b.nombre_diagnostico, "
					+ "COUNT(*) AS CANTIDAD_ENFERMEDADES "
					+ "FROM tbl_historial a  "
					+ "INNER JOIN tbl_diagnostico b "
					+ "ON (b.id_diagnostico=a.id_diagnostico) "
					+ "GROUP BY b.nombre_diagnostico ");
			while (resultado.next()){
				lista.add(new Data<String,Integer>(
						resultado.getString ("nombre_diagnostico"),
						resultado.getInt("CANTIDAD_ENFERMEDADES")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void llenarInformacionGraficoPacientesxMedico(Connection conexion, ObservableList<Data<String, Integer>> lista)
	{
		try {
			Statement instruccion = conexion.createStatement();
			ResultSet resultado = instruccion.executeQuery("select  "
					+ "e.nombre,  "
					+ "e.apellido, "
					+ "count(*) as cantidad "
					+ "from tbl_historial a "
					+ "inner join tbl_preclinica b "
					+ "on (a.codigo_cita=b.codigo_cita) "
					+ "inner join tbl_citas c "
					+ "on (a.codigo_cita=c.codigo_cita) "
					+ "inner join tbl_medico d "
					+ "on (c.numero_empleado=d.numero_empleado) "
					+ "inner join tbl_empleados e "
					+ "on (d.numero_empleado=e.numero_empleado) "
					+ "group by d.numero_empleado");
			while (resultado.next()){
				lista.add(new Data<String,Integer>(
						resultado.getString ("nombre")+" "+resultado.getString("apellido"),
						resultado.getInt("cantidad")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}