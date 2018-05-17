package application;

import javafx.fxml.FXML;

public class ControladorMedico {
	private Main main;

	public void setMain(Main main){
		this.main=main;
	}

	@FXML
	public void salir(){
		main.principal(1);
	}

}
