package br.edu.ifpi.projetoeventos.models.enums;

public enum EventStatus{

	IN_PROGRESS("Em andamento"),
	OPEN("Aberto"),
	FINISHED("Finalizado");
	
	private final String status;
	
	private EventStatus(String status){
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
