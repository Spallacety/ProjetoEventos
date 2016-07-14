package br.edu.ifpi.projetoeventos.models.others;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpi.projetoeventos.models.activity.AActivity;
import br.edu.ifpi.projetoeventos.models.event.AEvent;

public class Inscription {

	private User user;
	private boolean paid = false;
	private double value;
	private AEvent event;
	private List<AActivity> registeredActivityList = new ArrayList<>();
	private Coupon coupon = new Coupon();
	
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
	
	public boolean pay(double value){
		if(value >= this.value){
			paid = true;
		}
		return isPaid();
	}
	
	private double calculateDiscount(){
		double discount = 0;
		if(coupon.isActive()){
			if (coupon.getGeneral())
				discount = value * coupon.getDiscountPercentual();
			if (!coupon.getGeneral()){
				for (AActivity activity : registeredActivityList) {
					if (activity.getClass() == coupon.getActivity().getClass()){
						discount += activity.getValue()* coupon.getDiscountPercentual();
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
	
	public double getValue() {
		this.value = 0;
		for (AActivity activity : registeredActivityList) {
			this.value += activity.getValue();
		}
		return value - this.calculateDiscount();
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
