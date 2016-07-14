package br.edu.ifpi.projetoeventos.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.edu.ifpi.projetoeventos.models.activity.AActivity;
import br.edu.ifpi.projetoeventos.models.activity.Lecture;
import br.edu.ifpi.projetoeventos.models.activity.PanelDiscussion;
import br.edu.ifpi.projetoeventos.models.event.AEvent;
import br.edu.ifpi.projetoeventos.models.event.Congress;
import br.edu.ifpi.projetoeventos.models.event.Symposium;
import br.edu.ifpi.projetoeventos.models.others.Coupon;
import br.edu.ifpi.projetoeventos.models.others.Inscription;

public class InscriptionTest {

	@Test
	public void nao_deve_aplicar_desconto_de_cupons_nao_ativos() {
		Coupon coupon = new Coupon();
		coupon.setExpirationDate(1, 1, 2015);
		coupon.setDiscountPercentual(100);
		Inscription inscription = new Inscription();
		inscription.setCoupon(coupon);
		AActivity lecture = new Lecture("Lecture", 300);
		inscription.addActivity(lecture);
		Assert.assertEquals(300, inscription.getValue(), 0.0);
	}

	@Test
	public void valor_das_inscricoes_sendo_total_dos_seus_itens() {
		AActivity panelDiscussion = new PanelDiscussion("Panel Discussion", 200);
		AActivity lecture = new Lecture("Lecture", 300);
		AEvent symposium = new Symposium();
		symposium.addActivity(panelDiscussion);
		symposium.addActivity(lecture);
		Inscription inscription = new Inscription();
		inscription.addActivity(panelDiscussion);
		inscription.addActivity(lecture);
		Assert.assertEquals(500, inscription.getValue(), 0.0);
	}

	@Test
	public void deve_marcar_inscricao_como_paga_ao_receber_pagamento_total() {
		AActivity lecture = new Lecture("Lecture", 300);
		AEvent symposium = new Symposium();
		symposium.addActivity(lecture);
		Inscription inscription = new Inscription();
		inscription.addActivity(lecture);
		inscription.getValue();
		inscription.pay(300);
		Assert.assertEquals(true, inscription.isPaid());
	}

	@Test
	public void nao_deve_incluir_atividades_repetidas() {
		AActivity lecture = new Lecture("Lecture", 300);
		AEvent symposium = new Symposium();
		symposium.addActivity(lecture);
		Inscription inscription = new Inscription();
		Assert.assertEquals(true, inscription.addActivity(lecture));
		Assert.assertEquals(false, inscription.addActivity(lecture));
	}

	@Test
	public void inscricao_recem_criada_deve_ter_zero_atividade() {
		Inscription inscription = new Inscription();
		List<AActivity> testList = new ArrayList<>();
		Assert.assertEquals(testList, inscription.getRegisteredActivityList());
	}

	@Test
	public void inscricoes_com_pagamento_inferiores_ao_valor_de_pagar_devem_nao_estar_paga() {
		AActivity lecture = new Lecture("Lecture", 300);
		AEvent symposium = new Symposium();
		symposium.addActivity(lecture);
		Inscription inscription = new Inscription();
		inscription.addActivity(lecture);
		inscription.getValue();
		inscription.pay(200);
		Assert.assertEquals(false, inscription.isPaid());
	}

	@Test
	public void deve_aceitar_incluir_atividades_que_estejam_no_seu_evento() {
		AActivity panelDiscussion = new PanelDiscussion("Panel Discussion", 200);
		AActivity lecture = new Lecture("Lecture", 300);
		AEvent symposium = new Symposium();
		symposium.addActivity(panelDiscussion);
		symposium.addActivity(lecture);
		Inscription inscription = new Inscription();
		Assert.assertEquals(true, inscription.addActivity(panelDiscussion));
		Assert.assertEquals(true, inscription.addActivity(lecture));
	}

	@Test
	public void inscricao_sem_itens_deve_ter_valor_zero() {
		Inscription inscription = new Inscription();
		Assert.assertEquals(0.0, inscription.getValue(), 0.0);
	}

	@Test
	public void inscricao_deve_aplicar_descontos_ativos_do_evento() {
		AActivity lecture = new Lecture("Lecture", 300);
		Coupon coupon = new Coupon();
		coupon.setExpirationDate(1, 10, 2500);
		coupon.setDiscountPercentual(0.5);
		Inscription inscription = new Inscription();
		inscription.setCoupon(coupon);
		inscription.addActivity(lecture);
		Assert.assertEquals(150, inscription.getValue(), 0.0);
	}

	@Test
	public void nao_deve_aceitar_incluir_atividades_de_outros_eventos() {
		AActivity panelDiscussion = new PanelDiscussion("Panel Discussion", 200);
		AActivity lecture = new Lecture("Lecture", 300);
		AEvent symposium = new Symposium();
		AEvent congress = new Congress();
		symposium.addActivity(panelDiscussion);
		congress.addActivity(lecture);
		Inscription inscription = new Inscription();
		Assert.assertEquals(true, inscription.addActivity(panelDiscussion));
		Assert.assertEquals(false, inscription.addActivity(lecture));
	}

	@Test
	public void inscricao_paga_nao_deve_aceitar_novos_itens() {
		AActivity panelDiscussion = new PanelDiscussion("Panel Discussion", 200);
		AEvent symposium = new Symposium();
		symposium.addActivity(panelDiscussion);
		Inscription inscription = new Inscription();
		inscription.setPaid(true);
		Assert.assertEquals(false, inscription.addActivity(panelDiscussion));
	}

}
