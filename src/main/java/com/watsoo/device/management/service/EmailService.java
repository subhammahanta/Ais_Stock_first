package com.watsoo.device.management.service;

import java.util.Map;

import com.watsoo.device.management.dto.CustomResponse;
import com.watsoo.device.management.dto.MailRequest;
import com.watsoo.device.management.dto.SendEmailRequest;

public interface EmailService {

	public CustomResponse sendEmail(SendEmailRequest obj);

	CustomResponse sendEmailV1(SendEmailRequest obj);

	CustomResponse sendMail(MailRequest mail, Map<String, Object> model);
}
