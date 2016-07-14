package br.edu.ifpi.projetoeventos.models.enums;

public enum EStatus {

	IN_PROGRESS("Em andamento"),
	OPEN("Aberto"),
	FINISHED("Finalizado");
	
	private final String status;
	
	private EStatus(String status){
		this.status = status;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	@Override
	public String toString() {
		return this.getStatus();
	}
}
