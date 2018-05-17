package application;

public class ControladorEnfermeros {
	private Main main;

	public void setMain(Main main){
		this.main=main;
	}

	public void salir(){
		main.principal(0);
	}
}
