package utilerias;

public class Main {

	public static void main(String[] args) {
		GestorConexiones gestor=new GestorConexiones();
		gestor.establecerConexion();
		gestor.cerrarConexion();
	}

}
