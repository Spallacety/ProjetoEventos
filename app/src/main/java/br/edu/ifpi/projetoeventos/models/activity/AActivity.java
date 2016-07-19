package br.edu.ifpi.projetoeventos.models.activity;

import java.math.BigDecimal;

import br.edu.ifpi.projetoeventos.models.event.AEvent;

public abstract class AActivity {

	protected BigDecimal value;
	protected String name;
	protected AEvent event;
	
	public AActivity(String name, String value){
		this.name = name;
		this.value = new BigDecimal(value);
	}

	public BigDecimal getValue() {
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
