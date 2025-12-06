package com.repository.manager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.repository.manager.core.model.EmailDetails;
import com.repository.manager.service.email.EmailService;

@RestController
public class MailController {
	@Autowired
	private EmailService emailService;

	@GetMapping("/mail")
	public String sendMail(@RequestBody EmailDetails emailDetails) {
		return emailService.sendEmail(emailDetails);
	}

	@GetMapping("/mailWithAttachment")
	public String sendMailWithAttachment(@RequestBody EmailDetails emailDetails) {
		return emailService.sendEmailWithAttachment(emailDetails);
	}
}
