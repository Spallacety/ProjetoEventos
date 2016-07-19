package br.edu.ifpi.projetoeventos.tests;

import org.junit.Test;

import br.edu.ifpi.projetoeventos.models.activity.AActivity;
import br.edu.ifpi.projetoeventos.models.activity.Lecture;
import br.edu.ifpi.projetoeventos.models.activity.Minicourse;
import br.edu.ifpi.projetoeventos.models.others.Coupon;
import br.edu.ifpi.projetoeventos.models.others.Inscription;

import junit.framework.Assert;

import java.math.BigDecimal;

public class CouponTest {

	@Test
	public void deve_ser_ativo_se_no_periodo_de_validade() {
		Coupon coupon = new Coupon("0");
		coupon.setExpirationDate(15, 12, 2500);
		Assert.assertEquals(true, coupon.isActive());
	}
	
	@Test
	public void nao_deve_ser_ativo_se_fora_da_validade(){
		Coupon coupon = new Coupon("0");
		coupon.setExpirationDate(1, 0, 1970);
		Assert.assertEquals(false, coupon.isActive());
	}

	@Test
	public void deve_aplicar_cupom_de_uma_unica_atividade(){
		AActivity lecture = new Lecture("Lecture", "300");
		AActivity minicourse = new Minicourse("Minicourse", "300");
		AActivity minicourse2 = new Minicourse("Minicourse", "300");
		Coupon coupon = new Coupon("0.5");
		coupon.setExpirationDate(1, 10, 2500);
		coupon.setActivity(new Minicourse("Test", "0"));
		Inscription inscription = new Inscription();
		inscription.setCoupon(coupon);
		inscription.addActivity(lecture);
		inscription.addActivity(minicourse);
		inscription.addActivity(minicourse2);
		Assert.assertEquals(new BigDecimal("600.0"), inscription.getValue());
	}
}
