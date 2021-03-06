package br.edu.ifpi.projetoeventos;

import org.junit.Test;

import br.edu.ifpi.projetoeventos.exceptions.ActivityIsNotFromThisEventException;
import br.edu.ifpi.projetoeventos.exceptions.EventNotOpenException;
import br.edu.ifpi.projetoeventos.exceptions.InscriptionAlreadyContainsActivityException;
import br.edu.ifpi.projetoeventos.exceptions.PaidInscriptionException;
import br.edu.ifpi.projetoeventos.models.coupon.ActivityCoupon;
import br.edu.ifpi.projetoeventos.models.coupon.GeneralCoupon;
import br.edu.ifpi.projetoeventos.models.enums.EventType;
import br.edu.ifpi.projetoeventos.models.event.Activity;
import br.edu.ifpi.projetoeventos.models.enums.ActivityType;
import br.edu.ifpi.projetoeventos.models.coupon.Coupon;
import br.edu.ifpi.projetoeventos.models.event.Event;
import br.edu.ifpi.projetoeventos.models.event.Factory;
import br.edu.ifpi.projetoeventos.models.others.Inscription;

import junit.framework.Assert;

import java.math.BigDecimal;

public class CouponTest {

	@Test
	public void deve_ser_ativo_se_no_periodo_de_validade() {
		GeneralCoupon coupon = new GeneralCoupon("0");
		coupon.setExpirationDate(15, 12, 2500);
		Assert.assertEquals(true, coupon.isActive());
	}
	
	@Test
	public void nao_deve_ser_ativo_se_fora_da_validade(){
		GeneralCoupon coupon = new GeneralCoupon("0");
		coupon.setExpirationDate(1, 0, 1970);
		Assert.assertEquals(false, coupon.isActive());
	}

	@Test
	public void deve_aplicar_cupom_de_uma_unica_atividade() throws PaidInscriptionException, InscriptionAlreadyContainsActivityException, ActivityIsNotFromThisEventException, EventNotOpenException {
		Activity lecture = Factory.makeActivity();
		lecture.setName("Lecture");
		lecture.setValue(new BigDecimal("300"));
		lecture.setActivityType(ActivityType.LECTURE);
		Activity minicourse = Factory.makeActivity();
		minicourse.setName("Minicourse");
		minicourse.setValue(new BigDecimal("300"));
		minicourse.setActivityType(ActivityType.MINICOURSE);
		Activity minicourse2 = Factory.makeActivity();
		minicourse2.setName("Minicourse");
		minicourse2.setValue(new BigDecimal("300"));
		minicourse2.setActivityType(ActivityType.MINICOURSE);
		Event symposium = Factory.makeEvent();
		symposium.setEventType(EventType.SYMPOSIUM);
		symposium.addActivity(lecture);
		symposium.addActivity(minicourse);
		symposium.addActivity(minicourse2);
		ActivityCoupon coupon = new ActivityCoupon("0.5");
		coupon.setExpirationDate(1, 10, 2500);
		Activity activity = Factory.makeActivity();
		activity.setActivityType(ActivityType.MINICOURSE);
		coupon.setActivity(activity);
		Inscription inscription = new Inscription(symposium);
		inscription.addActivity(lecture);
		inscription.addActivity(minicourse);
		inscription.addActivity(minicourse2);
		inscription.setCoupon(coupon);
		Assert.assertEquals(new BigDecimal("600.0"), inscription.getValue());
	}

}
