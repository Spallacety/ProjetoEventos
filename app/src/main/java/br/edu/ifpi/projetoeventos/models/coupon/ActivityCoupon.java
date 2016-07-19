package br.edu.ifpi.projetoeventos.models.coupon;

import br.edu.ifpi.projetoeventos.models.event.Activity;

public class ActivityCoupon extends Coupon{

    private Activity activity;

    public ActivityCoupon(String value) {
        super(value);
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

}
