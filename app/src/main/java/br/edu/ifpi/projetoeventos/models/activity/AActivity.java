package br.edu.ifpi.projetoeventos.models.activity;

import br.edu.ifpi.projetoeventos.models.event.AEvent;

public abstract class AActivity {

	protected double value;
	protected String name;
	protected AEvent event;
	
	public AActivity(String name, double value){
		this.name = name;
		this.value = value;
	}

	public double getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public AEvent getEvent() {
		return event;
	}

	public void setEvent(AEvent event) {
		this.event = event;
	}
	
}
