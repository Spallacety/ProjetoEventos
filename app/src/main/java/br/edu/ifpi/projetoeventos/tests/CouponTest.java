package br.edu.ifpi.projetoeventos.tests;

import org.junit.Test;

import br.edu.ifpi.projetoeventos.models.event.Activity;
import br.edu.ifpi.projetoeventos.models.enums.ActivityType;
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
		Activity lecture = new Activity("Lecture", "300", ActivityType.LECTURE);
		Activity minicourse = new Activity("Minicourse", "300", ActivityType.MINICOURSE);
		Activity minicourse2 = new Activity("Minicourse", "300", ActivityType.MINICOURSE);
		Coupon coupon = new Coupon("0.5");
		coupon.setExpirationDate(1, 10, 2500);
		coupon.setActivity(new Activity("Test", "0", ActivityType.MINICOURSE));
		Inscription inscription = new Inscription();
		inscription.setCoupon(coupon);
		inscription.addActivity(lecture);
		inscription.addActivity(minicourse);
		inscription.addActivity(minicourse2);
		Assert.assertEquals(new BigDecimal("600.0"), inscription.getValue());
	}
}
