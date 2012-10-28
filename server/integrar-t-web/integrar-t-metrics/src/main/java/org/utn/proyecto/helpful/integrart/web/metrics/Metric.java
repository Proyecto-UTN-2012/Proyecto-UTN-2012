package org.utn.proyecto.helpful.integrart.web.metrics;

import java.util.Arrays;

import org.codehaus.jackson.annotate.JsonProperty;
import org.utn.proyecto.helpful.integrart.core.percistence.Entity;

@Entity("metric")
public class Metric {
	@JsonProperty("_id")
	private String id;
	private ActivityMetric activity;
	private String category;
	private String[] subcategories;
	private int value;
	private User user;
	
	public Metric(){}
	
	public Metric(User user, ActivityMetric activity, String category){
		this(user, activity, category, 1);
	}
	
	public Metric(User user, ActivityMetric activity, String category, String subcategories){
		this(user, activity, category, subcategories, 1);
	}
	
	public Metric(User user, ActivityMetric activity, String category, String[] subcategories){
		this(user, activity, category, subcategories, 1);
	}
	
	public Metric(User user, ActivityMetric activity, String category, int value){
		this(user, activity, category, new String[0], value);
	}
	
	public Metric(User user, ActivityMetric activity, String category, String subcategory, int value){
		this(user, activity, category, new String[]{subcategory}, value);
	}
	
	public Metric(User user, ActivityMetric activity,String category, String[] subcategories, int value){
		this.activity = activity;
		this.category = category;
		this.subcategories = subcategories;
		this.value = value;
		this.user = user;
	}

	public ActivityMetric getActivity() {
		return activity;
	}

	public String getCategory() {
		return category;
	}

	public String[] getSubcategories() {
		return subcategories;
	}

	public int getValue() {
		return value;
	}
	
	public User getUser(){
		return user;
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Metric)) return false;
		Metric m = (Metric)o;
		boolean subcategoriesEquals = subcategories.length == m.subcategories.length;
		for(int i=0;i<subcategories.length && subcategoriesEquals;i++){
			subcategoriesEquals = subcategories[i].equals(m.subcategories[i]);
		}
		return this.activity == m.activity && this.category == m.category && this.value == m.value && subcategoriesEquals;
	}
	
	@Override
	public int hashCode(){
		return this.activity.hashCode() + this.category.hashCode() - this.value;
	}
	
	@Override
	public String toString(){
		return "Metric{activity:" + activity.name() + ", category:" + category + ", subcategories:" + Arrays.asList(subcategories).toString() + ", value:" + value + "}";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setActivity(ActivityMetric activity) {
		this.activity = activity;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setSubcategories(String[] subcategories) {
		this.subcategories = subcategories;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
