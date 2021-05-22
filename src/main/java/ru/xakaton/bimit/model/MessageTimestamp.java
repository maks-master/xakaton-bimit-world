package ru.xakaton.bimit.model;

import java.sql.Timestamp;

public class MessageTimestamp {

	private Timestamp ts;
	
	private MessageData data;

	public Timestamp getTs() {
		return ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	public MessageData getData() {
		return data;
	}

	public void setData(MessageData data) {
		this.data = data;
	}
	
	
	
}
