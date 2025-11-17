package com.repository.manager.service.email;

import com.repository.manager.core.model.EmailDetails;

public interface EmailService {
	String sendEmail(EmailDetails emailDetails);

	String sendEmailWithAttachment(EmailDetails emailDetails);
}
