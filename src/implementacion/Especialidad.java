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

public class Especialidad{
		private IntegerProperty codigoEspecialidad;
		private StringProperty nombreEspecialidad;
		private StringProperty descripcion;

		public Especialidad(int codigoEspecialidad, String nombreEspecialidad, String descripcion) {
			this.codigoEspecialidad = new SimpleIntegerProperty(codigoEspecialidad);
			this.nombreEspecialidad = new SimpleStringProperty(nombreEspecialidad);
			this.descripcion = new SimpleStringProperty(descripcion);
		}

		public Especialidad(){

		}

		//Metodos atributo: codigoEspecialidad
		public int getCodigoEspecialidad() {
			return codigoEspecialidad.get();
		}
		public void setCodigoEspecialidad(int codigoEspecialidad) {
			this.codigoEspecialidad = new SimpleIntegerProperty(codigoEspecialidad);
		}
		public IntegerProperty CodigoEspecialidadProperty() {
			return codigoEspecialidad;
		}
		//Metodos atributo: nombreEspecialidad
		public String getNombreEspecialidad() {
			return nombreEspecialidad.get();
		}
		public void setNombreEspecialidad(String nombreEspecialidad) {
			this.nombreEspecialidad = new SimpleStringProperty(nombreEspecialidad);
		}
		public StringProperty NombreEspecialidadProperty() {
			return nombreEspecialidad;
		}

		public String getDescripcion() {
			return descripcion.get();
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = new SimpleStringProperty(descripcion);
		}
		public StringProperty DescripcionProperty() {
			return descripcion;
		}

		@Override
		public String toString(){
			return nombreEspecialidad.get();
		}

		public int guardarRegistro(Connection conexion){
			try {
				PreparedStatement instruccion =
						conexion.prepareStatement(
							"INSERT INTO tbl_especialidad ( " +
									"codigo_especialidad, "+
									"nombre_especialidad, "	+
									"descripcion "+
							") VALUES (?,?,?)"
						);
				instruccion.setInt(1, codigoEspecialidad.get());
				instruccion.setString(2, nombreEspecialidad.get());
                instruccion.setString(3, descripcion.get());
				return instruccion.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		}

		public static void llenarInformacion(Connection conexion, ObservableList<Especialidad> lista){

			try {
				Statement 	instruccion = conexion.createStatement();
				ResultSet resultado = instruccion.executeQuery("SELECT codigo_especialidad, nombre_especialidad,descripcion FROM tbl_especialidad");
				while(resultado.next()){
					lista.add(new Especialidad(
							resultado.getInt("codigo_especialidad"),
							resultado.getString("nombre_especialidad"),
							resultado.getString("descripcion")
							)
);
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}



		public int eliminar(Connection conexion){
			try {
				PreparedStatement inst=conexion.prepareStatement(
						"DELETE FROM tbl_especialidad WHERE codigo_especialidad =?");
				inst.setInt(1, codigoEspecialidad.get());
				return inst.executeUpdate();
			} catch (SQLException e) {

				e.printStackTrace();
				return 0;
			}
		}




		public int actualizarRegistro(Connection conexion){
			 try {
				PreparedStatement instruccion =
						conexion.prepareStatement(
							"UPDATE tbl_especialidad SET  "
							+"nombre_especialidad = ?,"
							+"descripcion = ?"
                            + " WHERE codigo_especialidad =?  "
						);
				instruccion.setString(1,nombreEspecialidad.get());
				instruccion.setString(2, descripcion.get());
				instruccion.setInt(3, codigoEspecialidad.get());
				return instruccion.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		 }

		public static int obtenerUltimo(Connection conexion){
			try {
				Statement inst=conexion.createStatement();
				ResultSet resultados=inst.executeQuery("SELECT MAX(codigo_especialidad) AS id FROM tbl_especialidad");
				resultados.next();
				return resultados.getInt("id");
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
		}
}
