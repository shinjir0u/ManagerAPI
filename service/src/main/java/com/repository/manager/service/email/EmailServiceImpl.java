package com.repository.manager.service.email;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.repository.manager.core.model.EmailDetails;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {
	@Value("${MAIL_USERNAME}")
	private String mailUsername;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public String sendEmail(EmailDetails emailDetails) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(mailUsername);
			mailMessage.setTo(emailDetails.getRecipient());
			mailMessage.setText(emailDetails.getMessageBody());
			mailMessage.setSubject(emailDetails.getSubject());

			javaMailSender.send(mailMessage);
			return "Email sent successfully!";
		} catch (Exception e) {
			return "Error sending mail!";
		}
	}

	@Override
	public String sendEmailWithAttachment(EmailDetails emailDetails) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setFrom(mailUsername);
			mimeMessageHelper.setTo(emailDetails.getRecipient());
			mimeMessageHelper.setText(emailDetails.getMessageBody());
			mimeMessageHelper.setSubject(emailDetails.getSubject());

			FileSystemResource resource = new FileSystemResource(new File(emailDetails.getAttachment()));
			mimeMessageHelper.addAttachment(resource.getFilename(), resource);

			javaMailSender.send(mimeMessage);
			return "Mail Sent Successfully!";
		} catch (Exception e) {
			return "Error sending mail!";
		}
	}

}
