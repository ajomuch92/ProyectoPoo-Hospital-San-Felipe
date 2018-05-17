package implementacion;

import java.sql.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Persona{
	private StringProperty identidad;
	private StringProperty nombre;
	private StringProperty apellido;
	private StringProperty genero;
	private Date fechaNacimiento;
	private IntegerProperty edad;
	private StringProperty telefono;
	private StringProperty celular;
	private StringProperty direccion;

	public Persona(String identidad, String nombre, String apellido,
					String genero, Date fechaNacimiento, int edad,
					String telefono, String celular, String direccion) {
		this.identidad = new SimpleStringProperty(identidad);
		this.nombre = new SimpleStringProperty(nombre);
		this.apellido = new SimpleStringProperty(apellido);
		this.genero = new SimpleStringProperty(genero);
		this.fechaNacimiento = fechaNacimiento;
		this.edad = new SimpleIntegerProperty(edad);
		this.telefono = new SimpleStringProperty(telefono);
		this.celular = new SimpleStringProperty(celular);
		this.direccion = new SimpleStringProperty(direccion);
	}

	public Persona(){

	}

	//Metodos atributo: identidad
	public String getIdentidad() {
		return identidad.get();
	}
	public void setIdentidad(String identidad) {
		this.identidad = new SimpleStringProperty(identidad);
	}
	public StringProperty IdentidadProperty() {
		return identidad;
	}
	//Metodos atributo: nombre
	public String getNombre() {
		return nombre.get();
	}
	public void setNombre(String nombre) {
		this.nombre = new SimpleStringProperty(nombre);
	}
	public StringProperty NombreProperty() {
		return nombre;
	}
	//Metodos atributo: apellido
	public String getApellido() {
		return apellido.get();
	}
	public void setApellido(String apellido) {
		this.apellido = new SimpleStringProperty(apellido);
	}
	public StringProperty ApellidoProperty() {
		return apellido;
	}
	//Metodos atributo: genero
	public String getGenero() {
		return genero.get();
	}
	public void setGenero(String genero) {
		this.genero = new SimpleStringProperty(genero);
	}
	public StringProperty GeneroProperty() {
		return genero;
	}
	//Metodos atributo: fechaNacimiento
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	//Metodos atributo: edad
	public int getEdad() {
		return edad.get();
	}
	public void setEdad(int edad) {
		this.edad = new SimpleIntegerProperty(edad);
	}
	public IntegerProperty EdadProperty() {
		return edad;
	}
	//Metodos atributo: telefono
	public String getTelefono() {
		return telefono.get();
	}
	public void setTelefono(String telefono) {
		this.telefono = new SimpleStringProperty(telefono);
	}
	public StringProperty TelefonoProperty() {
		return telefono;
	}
	//Metodos atributo: celular
	public String getCelular() {
		return celular.get();
	}
	public void setCelular(String celular) {
		this.celular = new SimpleStringProperty(celular);
	}
	public StringProperty CelularProperty() {
		return celular;
	}
	//Metodos atributo: direccion
	public String getDireccion() {
		return direccion.get();
	}
	public void setDireccion(String direccion) {
		this.direccion = new SimpleStringProperty(direccion);
	}
	public StringProperty DireccionProperty() {
		return direccion;
	}


	@Override
	public String toString() {
		return nombre.get() + " " + apellido.get() ;
	}



}
