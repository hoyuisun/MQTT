package com.cht.iot;

public class IRawdata {
	private String id;
	private String time;
	private Double lat;
	private Double lon;
	private Boolean save;
	private String [] value;
	
	public IRawdata(){
	}
	
	//Set functions
	public void setId(String id){
		this.id = id;
	}
	
	public void setTime(String time){
		this.time = time;
	}
	
	public void setLat(Double lat){
		this.lat = lat;
	}
	
	public void setLon(Double lon){
		this.lon = lon;
	}
	
	public void setSave(Boolean save){
		this.save = save;
	}
	
	public void setValues(String [] values){
		this.value = values;
	}
	
	//Get functions
	public String getId(){
		return id;
	}
	
	public String getTime(){
		return time;
	}
	
	public Double getLat(){
		return lat;
	}
	
	public Double getLon(){
		return lon;
	}
	
	public Boolean getSave(){
		return save;
	}
	
	public String [] values(){
		return value;
	}
}
