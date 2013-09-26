package com.getit.todoapp.service;

import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = -4093981756240899937L;
	private String senderName;
	private String senderEmail;
	private String ccEmail;
	private String subject;
	private String body;
	private String receiverName;
	private String receiverEmail;

	public Message() {
		super();
	}

	public Message(String senderName, String senderEmail, String ccEmail,
			String subject, String body, String receiverName,
			String receiverEmail) {
		super();
		this.senderName = senderName;
		this.senderEmail = senderEmail;
		this.ccEmail = ccEmail;
		this.subject = subject;
		this.body = body;
		this.receiverName = receiverName;
		this.receiverEmail = receiverEmail;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getCcEmail() {
		return ccEmail;
	}

	public void setCcEmail(String ccEmail) {
		this.ccEmail = ccEmail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

}