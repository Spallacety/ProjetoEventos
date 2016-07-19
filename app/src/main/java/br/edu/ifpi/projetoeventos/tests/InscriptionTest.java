package br.edu.ifpi.projetoeventos.tests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.edu.ifpi.projetoeventos.models.coupon.GeneralCoupon;
import br.edu.ifpi.projetoeventos.models.enums.EventType;
import br.edu.ifpi.projetoeventos.models.event.Activity;
import br.edu.ifpi.projetoeventos.models.enums.ActivityType;
import br.edu.ifpi.projetoeventos.models.event.Event;
import br.edu.ifpi.projetoeventos.models.coupon.Coupon;
import br.edu.ifpi.projetoeventos.models.others.Inscription;

public class InscriptionTest {

	@Test
	public void nao_deve_aplicar_desconto_de_cupons_nao_ativos() {
		GeneralCoupon coupon = new GeneralCoupon("1.0");
		coupon.setExpirationDate(1, 1, 2015);
		Inscription inscription = new Inscription();
		inscription.setCoupon(coupon);
		Activity lecture = new Activity("Lecture", "300", ActivityType.LECTURE);
		Event symposium = new Event(EventType.SYMPOSIUM);
		symposium.addActivity(lecture);
		inscription.addActivity(lecture);
		Assert.assertEquals(new BigDecimal("300"), inscription.getValue());
	}

	@Test
	public void valor_das_inscricoes_sendo_total_dos_seus_itens() {
		Activity panelDiscussion = new Activity("Panel Discussion", "200", ActivityType.PANEL_DISCUSSION);
		Activity lecture = new Activity("Lecture", "300", ActivityType.LECTURE);
		Event symposium = new Event(EventType.SYMPOSIUM);
		symposium.addActivity(panelDiscussion);
		symposium.addActivity(lecture);
		Inscription inscription = new Inscription();
		inscription.addActivity(panelDiscussion);
		inscription.addActivity(lecture);
		Assert.assertEquals(new BigDecimal("500"), inscription.getValue());
	}

	@Test
	public void deve_marcar_inscricao_como_paga_ao_receber_pagamento_total() {
		Activity lecture = new Activity("Lecture", "300", ActivityType.LECTURE);
		Event symposium = new Event(EventType.SYMPOSIUM);
		symposium.addActivity(lecture);
		Inscription inscription = new Inscription();
		inscription.addActivity(lecture);
		inscription.getValue();
		inscription.pay("300");
		Assert.assertEquals(true, inscription.isPaid());
	}

	@Test
	public void nao_deve_incluir_atividades_repetidas() {
		Activity lecture = new Activity("Lecture", "300", ActivityType.LECTURE);
		Event symposium = new Event(EventType.SYMPOSIUM);
		symposium.addActivity(lecture);
		Inscription inscription = new Inscription();
		Assert.assertEquals(true, inscription.addActivity(lecture));
		Assert.assertEquals(false, inscription.addActivity(lecture));
	}

	@Test
	public void inscricao_recem_criada_deve_ter_zero_atividade() {
		Inscription inscription = new Inscription();
		List<Activity> testList = new ArrayList<>();
		Assert.assertEquals(testList, inscription.getRegisteredActivityList());
	}

	@Test
	public void inscricoes_com_pagamento_inferiores_ao_valor_de_pagar_devem_nao_estar_paga() {
		Activity lecture = new Activity("Lecture", "300", ActivityType.LECTURE);
		Event symposium = new Event(EventType.SYMPOSIUM);
		symposium.addActivity(lecture);
		Inscription inscription = new Inscription();
		inscription.addActivity(lecture);
		inscription.getValue();
		inscription.pay("200");
		Assert.assertEquals(false, inscription.isPaid());
	}

	@Test
	public void deve_aceitar_incluir_atividades_que_estejam_no_seu_evento() {
		Activity panelDiscussion = new Activity("Panel Discussion", "200", ActivityType.PANEL_DISCUSSION);
		Activity lecture = new Activity("Lecture", "300", ActivityType.LECTURE);
		Event symposium = new Event(EventType.SYMPOSIUM);
		symposium.addActivity(panelDiscussion);
		symposium.addActivity(lecture);
		Inscription inscription = new Inscription();
		Assert.assertEquals(true, inscription.addActivity(panelDiscussion));
		Assert.assertEquals(true, inscription.addActivity(lecture));
	}

	@Test
	public void inscricao_sem_itens_deve_ter_valor_zero() {
		Inscription inscription = new Inscription();
		Assert.assertEquals(new BigDecimal("0"), inscription.getValue());
	}

	@Test
	public void inscricao_deve_aplicar_descontos_ativos() {
		Activity lecture = new Activity("Lecture", "300", ActivityType.LECTURE);
		Event symposium = new Event(EventType.SYMPOSIUM);
		symposium.addActivity(lecture);
		GeneralCoupon coupon = new GeneralCoupon("0.5");
		coupon.setExpirationDate(1, 10, 2500);
		Inscription inscription = new Inscription();
		inscription.setCoupon(coupon);
		inscription.addActivity(lecture);
		Assert.assertEquals(new BigDecimal("150.0"), inscription.getValue());
	}

	@Test
	public void nao_deve_aceitar_incluir_atividades_de_outros_eventos() {
		Activity panelDiscussion = new Activity("Panel Discussion", "200", ActivityType.PANEL_DISCUSSION);
		Activity lecture = new Activity("Lecture", "300", ActivityType.LECTURE);
		Event symposium = new Event(EventType.SYMPOSIUM);
		Event congress = new Event(EventType.CONGRESS);
		symposium.addActivity(panelDiscussion);
		congress.addActivity(lecture);
		Inscription inscription = new Inscription();
		Assert.assertEquals(true, inscription.addActivity(panelDiscussion));
		Assert.assertEquals(false, inscription.addActivity(lecture));
	}

	@Test
	public void inscricao_paga_nao_deve_aceitar_novos_itens() {
		Activity panelDiscussion = new Activity("Panel Discussion", "200", ActivityType.PANEL_DISCUSSION);
		Event symposium = new Event(EventType.SYMPOSIUM);
		symposium.addActivity(panelDiscussion);
		Inscription inscription = new Inscription();
		inscription.setPaid(true);
		Assert.assertEquals(false, inscription.addActivity(panelDiscussion));
	}

}
