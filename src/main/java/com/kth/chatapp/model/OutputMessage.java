package com.kth.chatapp.model;

public class OutputMessage {

	private String sender;
	private String text;
	private String time;
	public OutputMessage(String sender, String text, String time) {
		super();
		this.sender = sender;
		this.text = text;
		this.time = time;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	 
}
