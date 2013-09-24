package com.getit.todoapp.service;

public interface SendGridParameter {
	public static final String URL = "http://sendgrid.com/api/mail.send.json";
	public static final String API_USER = "api_user";
	public static final String API_KEY = "api_key";
	public static final String RECEIVER_EMAIL = "to";
	public static final String RECEIVER_NAME = "toname";
	public static final String SUBJECT = "subject";
	public static final String TEXT = "text";
	public static final String HTML = "html";
	public static final String SENDER_EMAIL = "from";
	public static final String SENDER_NAME = "fromname";
	public static final String BLIND_COPY_EMAIL = "bcc";
}