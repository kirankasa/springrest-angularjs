package com.getit.todoapp.domain;

import flexjson.JSONDeserializer;

public class Feedback {
	
	private String email;
	private String body;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	
	public static Feedback fromJsonToFeedback(String json) {
        return new JSONDeserializer<Feedback>().use(null, Feedback.class).deserialize(json);
    }

}
