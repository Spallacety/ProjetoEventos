package br.edu.ifpi.projetoeventos.models.others;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.projetoeventos.models.event.Activity;
import br.edu.ifpi.projetoeventos.models.event.Event;

public class Inscription {

	private User user;
	private boolean paid = false;
	private BigDecimal value;
	private Event event;
	private List<Activity> registeredActivityList = new ArrayList<>();
	private Coupon coupon = new Coupon("0");
	
	public boolean addActivity(Activity activity){
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

	private boolean containsActivity(List<Activity> activityList, Activity activity) {
		for (Activity a : activityList) {
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
				for (Activity activity : registeredActivityList) {
					if (activity.getActivityType() == coupon.getActivity().getActivityType()){
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
		for (Activity activity : registeredActivityList) {
			this.value = this.value.add(activity.getValue());
		}
		return value.subtract(this.calculateDiscount());
	}
	
	public Event getEvent() {
		return event;
	}
	
	public List<Activity> getRegisteredActivityList() {
		return registeredActivityList;
	}
	
	public Coupon getCoupon() {
		return coupon;
	}
	
	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	
}
