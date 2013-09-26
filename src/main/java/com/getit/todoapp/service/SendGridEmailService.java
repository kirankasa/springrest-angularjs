package com.getit.todoapp.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class SendGridEmailService implements EmailService {

	protected static Logger logger = Logger.getLogger("service");
	private RestTemplate restTemplate = new RestTemplate();

	@Value("${sendgrid.api.user}")
	private String sendgridApiUser;

	@Value("${sendgrid.api.key}")
	private String sendgridApiKey;

	@Override
	public StatusResponse send(Message message) {
		try {
			MultiValueMap<String, Object> vars = new LinkedMultiValueMap<String, Object>();
			vars.add(SendGridParameter.API_USER, sendgridApiUser);
			vars.add(SendGridParameter.API_KEY, sendgridApiKey);
			vars.add(SendGridParameter.SENDER_NAME, message.getSenderName());
			vars.add(SendGridParameter.SENDER_EMAIL, message.getSenderEmail());
			vars.add(SendGridParameter.BLIND_COPY_EMAIL, message.getCcEmail());
			vars.add(SendGridParameter.SUBJECT, message.getSubject());
			vars.add(SendGridParameter.TEXT, "");
			vars.add(SendGridParameter.HTML, message.getBody());
			vars.add(SendGridParameter.RECEIVER_EMAIL,
					message.getReceiverEmail());
			vars.add(SendGridParameter.RECEIVER_NAME, message.getReceiverName());

			restTemplate.postForLocation(SendGridParameter.URL, vars);
		} catch (Exception ex) {
			logger.error(ex);
			return new StatusResponse(false, "An error has occurred!");
		}

		return new StatusResponse(true, "Message sent");
	}

}