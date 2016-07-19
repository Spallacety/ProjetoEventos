package br.edu.ifpi.projetoeventos.tests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.edu.ifpi.projetoeventos.exceptions.ActivityIsNotFromThisEventException;
import br.edu.ifpi.projetoeventos.exceptions.EventNotOpenException;
import br.edu.ifpi.projetoeventos.exceptions.InscriptionAlreadyContainsActivityException;
import br.edu.ifpi.projetoeventos.exceptions.PaidInscriptionException;
import br.edu.ifpi.projetoeventos.models.coupon.GeneralCoupon;
import br.edu.ifpi.projetoeventos.models.enums.EventType;
import br.edu.ifpi.projetoeventos.models.event.Activity;
import br.edu.ifpi.projetoeventos.models.enums.ActivityType;
import br.edu.ifpi.projetoeventos.models.event.Event;
import br.edu.ifpi.projetoeventos.models.others.Inscription;

public class InscriptionTest {

	Event symposium;
	Event congress;
	Activity lecture;
	Activity panelDiscussion;


	@Before
	public void init(){
		symposium = new Event(EventType.SYMPOSIUM);
		congress = new Event(EventType.CONGRESS);
		lecture = new Activity("Lecture", "300", ActivityType.LECTURE);
		panelDiscussion = new Activity("Panel Discussion", "200", ActivityType.PANEL_DISCUSSION);
	}

	@Test
	public void nao_deve_aplicar_desconto_de_cupons_nao_ativos() throws PaidInscriptionException, InscriptionAlreadyContainsActivityException, ActivityIsNotFromThisEventException, EventNotOpenException {
		GeneralCoupon coupon = new GeneralCoupon("1.0");
		coupon.setExpirationDate(1, 1, 2015);
		Inscription inscription = new Inscription(symposium);
		inscription.setCoupon(coupon);
		symposium.addActivity(lecture);
		inscription.addActivity(lecture);
		Assert.assertEquals(new BigDecimal("300"), inscription.getValue());
	}

	@Test
	public void valor_das_inscricoes_sendo_total_dos_seus_itens() throws PaidInscriptionException, InscriptionAlreadyContainsActivityException, ActivityIsNotFromThisEventException, EventNotOpenException {
		symposium.addActivity(panelDiscussion);
		symposium.addActivity(lecture);
		Inscription inscription = new Inscription(symposium);
		inscription.addActivity(panelDiscussion);
		inscription.addActivity(lecture);
		Assert.assertEquals(new BigDecimal("500"), inscription.getValue());
	}

	@Test
	public void deve_marcar_inscricao_como_paga_ao_receber_pagamento_total() throws PaidInscriptionException,
			InscriptionAlreadyContainsActivityException, ActivityIsNotFromThisEventException, EventNotOpenException {
		symposium.addActivity(lecture);
		Inscription inscription = new Inscription(symposium);
		inscription.addActivity(lecture);
		inscription.getValue();
		inscription.pay("300");
		Assert.assertEquals(true, inscription.isPaid());
	}

	@Test (expected = InscriptionAlreadyContainsActivityException.class)
	public void nao_deve_incluir_atividades_repetidas() throws PaidInscriptionException,
			InscriptionAlreadyContainsActivityException, ActivityIsNotFromThisEventException, EventNotOpenException {
		symposium.addActivity(lecture);
		Inscription inscription = new Inscription(symposium);
		inscription.addActivity(lecture);
		inscription.addActivity(lecture);
	}

	@Test
	public void inscricao_recem_criada_deve_ter_zero_atividade() {
		Inscription inscription = new Inscription(symposium);
		List<Activity> testList = new ArrayList<>();
		Assert.assertEquals(testList, inscription.getRegisteredActivityList());
	}

	@Test
	public void inscricoes_com_pagamento_inferiores_ao_valor_de_pagar_devem_nao_estar_paga() throws PaidInscriptionException,
			InscriptionAlreadyContainsActivityException, ActivityIsNotFromThisEventException, EventNotOpenException {
		symposium.addActivity(lecture);
		Inscription inscription = new Inscription(symposium);
		inscription.addActivity(lecture);
		inscription.getValue();
		inscription.pay("200");
		Assert.assertEquals(false, inscription.isPaid());
	}

	@Test
	public void deve_aceitar_incluir_atividades_que_estejam_no_seu_evento() throws PaidInscriptionException,
			InscriptionAlreadyContainsActivityException, ActivityIsNotFromThisEventException, EventNotOpenException {
		symposium.addActivity(panelDiscussion);
		symposium.addActivity(lecture);
		Inscription inscription = new Inscription(symposium);
		inscription.addActivity(panelDiscussion);
		inscription.addActivity(lecture);
		Assert.assertEquals(2, inscription.getRegisteredActivityList().size());
	}

	@Test
	public void inscricao_sem_itens_deve_ter_valor_zero() {
		Inscription inscription = new Inscription(symposium);
		Assert.assertEquals(new BigDecimal("0"), inscription.getValue());
	}

	@Test
	public void inscricao_deve_aplicar_descontos_ativos() throws PaidInscriptionException,
			InscriptionAlreadyContainsActivityException, ActivityIsNotFromThisEventException, EventNotOpenException {
		symposium.addActivity(lecture);
		GeneralCoupon coupon = new GeneralCoupon("0.5");
		coupon.setExpirationDate(1, 10, 2500);
		Inscription inscription = new Inscription(symposium);
		inscription.setCoupon(coupon);
		inscription.addActivity(lecture);
		Assert.assertEquals(new BigDecimal("150.0"), inscription.getValue());
	}

	@Test (expected = ActivityIsNotFromThisEventException.class)
	public void nao_deve_aceitar_incluir_atividades_de_outros_eventos() throws PaidInscriptionException,
			InscriptionAlreadyContainsActivityException, ActivityIsNotFromThisEventException, EventNotOpenException {
		symposium.addActivity(panelDiscussion);
		congress.addActivity(lecture);
		Inscription inscription = new Inscription(symposium);
		inscription.addActivity(panelDiscussion);
		inscription.addActivity(lecture);
	}

	@Test (expected = PaidInscriptionException.class)
	public void inscricao_paga_nao_deve_aceitar_novos_itens() throws PaidInscriptionException,
			InscriptionAlreadyContainsActivityException, ActivityIsNotFromThisEventException, EventNotOpenException {
		symposium.addActivity(panelDiscussion);
		Inscription inscription = new Inscription(symposium);
		inscription.setPaid(true);
		inscription.addActivity(panelDiscussion);
	}

}
