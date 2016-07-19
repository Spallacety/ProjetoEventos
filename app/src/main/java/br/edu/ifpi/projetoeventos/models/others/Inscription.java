package br.edu.ifpi.projetoeventos.models.others;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.projetoeventos.models.activity.AActivity;
import br.edu.ifpi.projetoeventos.models.event.AEvent;

public class Inscription {

	private User user;
	private boolean paid = false;
	private BigDecimal value;
	private AEvent event;
	private List<AActivity> registeredActivityList = new ArrayList<>();
	private Coupon coupon = new Coupon("0");
	
	public boolean addActivity(AActivity activity){
		if(!paid){
			if(activity.getEvent() == this.event || this.event == null){
				if(containsActivity(registeredActivityList, activity)){
					return false;
				}
				this.event = activity.getEvent();
				this.registeredActivityList.add(activity);
				return true;
			}
			return false;
		}
		return false;
	}

	private boolean containsActivity(List<AActivity> activityList, AActivity activity) {
		for (AActivity a : activityList) {
			if(a.hashCode() == activity.hashCode()){
				return true;
			}
		}
		return false;
	}
	
	public boolean pay(String value){
		BigDecimal tempValue = new BigDecimal(value);
		if(tempValue.compareTo(this.value) == 0){
			paid = true;
		}
		return isPaid();
	}
	
	private BigDecimal calculateDiscount(){
		BigDecimal discount = new BigDecimal("0");
		if(coupon.isActive()){
			if (coupon.getGeneral())
				discount = value.multiply(coupon.getDiscountPercentual());
			if (!coupon.getGeneral()){
				for (AActivity activity : registeredActivityList) {
					if (activity.getClass() == coupon.getActivity().getClass()){
						discount = discount.add(activity.getValue().multiply(coupon.getDiscountPercentual()));
					}
				}
			}
		}
		return discount;
	}
	
	public User getUser() {
		return user;
	}

	public boolean isPaid() {
		return paid;
	}
	
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	
	public BigDecimal getValue() {
		this.value = new BigDecimal("0");
		for (AActivity activity : registeredActivityList) {
			this.value = this.value.add(activity.getValue());
		}
		return value.subtract(this.calculateDiscount());
	}
	
	public AEvent getEvent() {
		return event;
	}
	
	public List<AActivity> getRegisteredActivityList() {
		return registeredActivityList;
	}
	
	public Coupon getCoupon() {
		return coupon;
	}
	
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	
}
