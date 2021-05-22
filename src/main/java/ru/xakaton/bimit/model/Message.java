package ru.xakaton.bimit.model;

public class Message {

	private String sensorName;
	private String sensorID;
	private MessageTimestamp ts;
	
	public String getSensorName() {
		return sensorName;
	}
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}
	public String getSensorID() {
		return sensorID;
	}
	public void setSensorID(String sensorID) {
		this.sensorID = sensorID;
	}
	public MessageTimestamp getTs() {
		return ts;
	}
	public void setTs(MessageTimestamp ts) {
		this.ts = ts;
	}
}
