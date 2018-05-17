package implementacion;

public class Atencion {
	private Preclinica preclinica;
	private String sintomas;
	private String examenes;
	private String diagnostico;
	private String tratamiento;

	public Atencion(Preclinica preclinica, String sintomas, String examenes, String diagnostico, String tratamiento) {
		super();
		this.preclinica = preclinica;
		this.sintomas = sintomas;
		this.examenes = examenes;
		this.diagnostico = diagnostico;
		this.tratamiento = tratamiento;
	}

	public Atencion() {
		super();
	}

	public Preclinica getPreclinica() {
		return preclinica;
	}

	public void setPreclinica(Preclinica preclinica) {
		this.preclinica = preclinica;
	}

	public String getSintomas() {
		return sintomas;
	}

	public void setSintomas(String sintomas) {
		this.sintomas = sintomas;
	}

	public String getExamenes() {
		return examenes;
	}

	public void setExamenes(String examenes) {
		this.examenes = examenes;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getTratamiento() {
		return tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	@Override
	public String toString() {
		return "Atencion [preclinica=" + preclinica + ", sintomas=" + sintomas + ", examenes=" + examenes
				+ ", diagnostico=" + diagnostico + ", tratamiento=" + tratamiento + "]";
	}


}
