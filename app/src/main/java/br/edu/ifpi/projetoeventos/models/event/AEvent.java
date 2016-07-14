package br.edu.ifpi.projetoeventos.models.event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.edu.ifpi.projetoeventos.models.activity.AActivity;
import br.edu.ifpi.projetoeventos.models.enums.EStatus;
import br.edu.ifpi.projetoeventos.models.others.Institution;

public abstract class AEvent {

	protected String name;
	protected Institution institution;
	protected List<AActivity> activityList = new ArrayList<>();
	protected EStatus status;
	protected Calendar initialDate;

	public AEvent(){
		this.status = EStatus.OPEN;
		this.initialDate = Calendar.getInstance();
		this.initialDate.set(Calendar.YEAR, 1970);
		this.initialDate.set(Calendar.MONTH, 0);
		this.initialDate.set(Calendar.DAY_OF_MONTH, 1);
	}

	public boolean addActivity(AActivity activity){
		if(containsActivity(this.activityList, activity)){
			return false;
		}
		activity.setEvent(this);
		this.activityList.add(activity);
		return true;
	}

	private boolean containsActivity(List<AActivity> activityList, AActivity activity) {
		for (AActivity a : activityList) {
			if(a.hashCode() == activity.hashCode()) return true;
		}
		return false;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Institution getInstitution() {
		return this.institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public List<AActivity> getActivityList() {
		return this.activityList;
	}

	public EStatus getStatus() {
		return this.status;
	}

	public void setStatus(EStatus status) {
		this.status = status;
	}

	public Calendar getInitialDate() {
		return this.initialDate;
	}

	public void setInitialDate(int day, int month, int year) {
		Calendar data = Calendar.getInstance();
		data.set(Calendar.YEAR, year);
		data.set(Calendar.MONTH, (month-1));
		data.set(Calendar.DAY_OF_MONTH, day);
		if(data.getTimeInMillis() >= Calendar.getInstance(Locale.getDefault()).getTimeInMillis()){
			this.initialDate = data;
		}
	}

}
