package br.edu.ifpi.projetoeventos.models.coupon;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Locale;

import br.edu.ifpi.projetoeventos.models.event.Activity;

public abstract class Coupon {
	
	private String name;
	private Calendar expirationDate;
	private BigDecimal discountPercentual;
	
	public Coupon(String value){
		this.discountPercentual = new BigDecimal(value);
		this.expirationDate = Calendar.getInstance(Locale.getDefault());
		this.expirationDate.set(Calendar.YEAR, 1970);
		this.expirationDate.set(Calendar.MONTH, 0);
		this.expirationDate.set(Calendar.DAY_OF_MONTH, 1);
	}
	
	public boolean isActive(){
		if(expirationDate.getTimeInMillis() >= Calendar.getInstance(Locale.getDefault()).getTimeInMillis()){
			return true;
		}
		return false;
	}

	public void setExpirationDate(int dia, int mes, int ano) {
		Calendar data = Calendar.getInstance(Locale.getDefault());
		data.set(Calendar.YEAR, ano); 
		data.set(Calendar.MONTH, (mes-1)); 
		data.set(Calendar.DAY_OF_MONTH, dia);
		this.expirationDate = data;
	}

	public BigDecimal getDiscountPercentual() {
		return discountPercentual;
	}

}
