package com.getit.todoapp.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class MyAuthenticationFailureHandler extends
		SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		
		
		JSONObject jsonObject=new JSONObject();
		System.out.println(exception.getMessage());
		try {
			jsonObject.put("message", "Invalid username");
			jsonObject.put("status", "Not okay");
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,jsonObject.toString());
		
	}
}