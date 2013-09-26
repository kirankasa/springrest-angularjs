package com.getit.todoapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.getit.todoapp.domain.Feedback;
import com.getit.todoapp.service.EmailService;
import com.getit.todoapp.service.Message;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
		Feedback feedback = Feedback.fromJsonToFeedback(json);
        Message message=new Message();
        message.setBody(feedback.getBody());
        message.setReceiverEmail("kiranreddy2004@gmail.com");
        message.setReceiverName("kiran");
        message.setSenderEmail(feedback.getEmail());
        message.setSubject("Todo : feedback");
        
        emailService.send(message);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

}
