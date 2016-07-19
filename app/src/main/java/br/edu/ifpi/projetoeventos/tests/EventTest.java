package br.edu.ifpi.projetoeventos.tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import br.edu.ifpi.projetoeventos.models.enums.EventType;
import br.edu.ifpi.projetoeventos.models.event.Activity;
import br.edu.ifpi.projetoeventos.models.enums.EventStatus;
import br.edu.ifpi.projetoeventos.models.event.Event;

import junit.framework.Assert;

public class EventTest {

	@Test
	public void nao_deve_aceitar_events_data_passada() {
		Event event = new Event(EventType.CYCLE_OF_LECTURES);
		Calendar testDate = Calendar.getInstance();
		testDate.set(Calendar.YEAR, 1970);
		testDate.set(Calendar.MONTH, 0);
		testDate.set(Calendar.DAY_OF_MONTH, 1);
		event.setInitialDate(3, 2, 1980);
		Assert.assertEquals(testDate, event.getInitialDate());
	}

	@Test
	public void event_recem_criado_deve_ter_zero_atividades() {
		Event event = new Event(EventType.SYMPOSIUM);
		List<Activity> testList = new ArrayList<>();
		Assert.assertEquals(testList, event.getActivityList());
	}

	@Test
	public void event_recem_criado_deve_ser_em_aberto() {
		Event event = new Event(EventType.CONGRESS);
		Assert.assertEquals(EventStatus.OPEN, event.getStatus());
	}

	@Test
	public void deve_aceitar_events_com_data_hoje_ou_futura() {
		Event event = new Event(EventType.JOURNEY);
		Calendar testDate = Calendar.getInstance();
		testDate.set(Calendar.YEAR, 2500);
		testDate.set(Calendar.MONTH, 11);
		testDate.set(Calendar.DAY_OF_MONTH, 15);
		event.setInitialDate(15, 12, 2500);
		Assert.assertEquals(testDate, event.getInitialDate());
	}
	
}
