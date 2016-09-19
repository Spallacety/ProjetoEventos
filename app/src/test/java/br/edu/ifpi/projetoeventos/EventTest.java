package br.edu.ifpi.projetoeventos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.threeten.bp.LocalDateTime;

import br.edu.ifpi.projetoeventos.models.enums.EventType;
import br.edu.ifpi.projetoeventos.models.event.Activity;
import br.edu.ifpi.projetoeventos.models.enums.EventStatus;
import br.edu.ifpi.projetoeventos.models.event.Event;
import br.edu.ifpi.projetoeventos.models.event.Factory;

import junit.framework.Assert;

public class EventTest {

	@Test
	public void nao_deve_aceitar_eventos_data_passada() {
		Event event = Factory.makeEvent();
		event.setInitialDate(LocalDateTime.of(1980, 02, 03, 00, 00));
		Assert.assertEquals(null, event.getInitialDate());
	}

	@Test
	public void event_recem_criado_deve_ter_zero_atividades() {
		Event event = Factory.makeEvent();
		List<Activity> testList = new ArrayList<>();
		Assert.assertEquals(testList, event.getActivityList());
	}

	@Test
	public void event_recem_criado_deve_ser_em_aberto() {
		Event event = Factory.makeEvent();
		Assert.assertEquals(EventStatus.OPEN, event.getStatus());
	}

	@Test
	public void deve_aceitar_events_com_data_hoje_ou_futura() {
		Event event = Factory.makeEvent();
		LocalDateTime testDate = LocalDateTime.of(2500, 12, 15, 00, 00);
		event.setInitialDate(testDate);
		Assert.assertEquals(testDate, event.getInitialDate());
	}
	
}
