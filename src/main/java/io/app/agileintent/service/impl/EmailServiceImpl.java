package io.app.agileintent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import io.app.agileintent.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	@Async
	public void sendEmail(SimpleMailMessage message) {
		javaMailSender.send(message);
		
	}

}
