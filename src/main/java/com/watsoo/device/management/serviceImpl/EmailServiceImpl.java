package com.watsoo.device.management.serviceImpl;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.validator.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.watsoo.device.management.dto.CustomResponse;
import com.watsoo.device.management.dto.MailRequest;
import com.watsoo.device.management.dto.SendEmailRequest;
import com.watsoo.device.management.service.EmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@SuppressWarnings("deprecation")
@Service
public class EmailServiceImpl implements EmailService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private Configuration config;

	@Override
	public CustomResponse sendEmailV1(SendEmailRequest obj) {
		try {

			Properties prop = new Properties();
			prop.put("mail.smtp.auth", true);
			prop.put("mail.smtp.starttls.enable", true);

			prop.put("mail.smtp.EnableSSL.enable", "true");
			prop.put("mail.smtp.host", obj.getHost());
			prop.put("mail.smtp.port", obj.getPort());
			prop.put("mail.smtp.ssl.trust", obj.getHost());

			Session session = Session.getInstance(prop, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(obj.getSenderEmail(), obj.getSenderPassword());
				}
			});

			List<String> getToRecipients = addToAddresses(Arrays.asList(obj.getToEmailIds()));

			if (!getToRecipients.get(1).isEmpty())
				LOGGER.info("Invalid toRecipients :" + getToRecipients.get(1));

			MimeMessage mimeMsg = new MimeMessage(session);
			mimeMsg.setFrom(obj.getSenderEmail());
			mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(getToRecipients.get(0)));
			mimeMsg.setSubject(obj.getSubject(), "UTF-8");

			if (obj.getAttachment() != null) {
				MimeBodyPart mimeBodyPart = new MimeBodyPart();
				Multipart multipart = new MimeMultipart();
				BodyPart htmlBodyPart = new MimeBodyPart();
				htmlBodyPart.setContent(obj.getContent(), "text/html");
				multipart.addBodyPart(htmlBodyPart);
				byte[] bytes = Base64.getDecoder().decode(obj.getAttachment());
//				InputStream is = new BufferedInputStream(new ByteArrayInputStream(bytes));
//				String mimeType = URLConnection.guessContentTypeFromStream(is);
				ByteArrayDataSource bds = new ByteArrayDataSource(bytes,
						"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				mimeBodyPart.setDataHandler(new DataHandler(bds));
				mimeBodyPart.setFileName(obj.getAttachmentName());
				multipart.addBodyPart(mimeBodyPart);

				mimeMsg.setContent(multipart);
			} else {
				mimeMsg.setContent(obj.getContent(), "text/html");
			}
			Transport.send(mimeMsg);
			LOGGER.info("E-mail has been sent.");
			return new CustomResponse(HttpStatus.OK.value(), "E-mail has been sent.", "Master.GetSuccessMessage");
		} catch (Exception e) {
			LOGGER.error("Error in send Email V1 of Email Service Impl");
			e.printStackTrace();
			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, "Master.GetSuccessMessage");
		}

	}

	private List<String> addToAddresses(List<String> to) {
		List<String> invalidAddresses = new LinkedList<>();
		List<String> validAddresses = new LinkedList<>();
		to.stream().forEachOrdered(email -> {
			if (validateEmail(email.trim()))
				validAddresses.add(email.trim());
			else
				invalidAddresses.add(email.trim());
		});

		return Arrays.asList(String.join(",", validAddresses.stream().toArray(String[]::new)),
				String.join(",", invalidAddresses.stream().toArray(String[]::new)));
	}

	private Boolean validateEmail(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

	@Override
	public CustomResponse sendMail(MailRequest mail, Map<String, Object> model) {
		MimeMessage message = sender.createMimeMessage();
		try {

			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			Template t = config.getTemplate("Welcome-Company.ftl");

			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

			helper.setTo(mail.getTo());

			helper.setText(html, true);

			helper.setSubject(mail.getSubject());

			helper.setFrom(mail.getFrom());

			sender.send(message);

			System.out.println("Mail Sendiinggg");
			return new CustomResponse(HttpStatus.OK.value(), "E-mail has been sent.", "Email.SuccessMsg");

		} catch (Exception e) {

			LOGGER.error("Error in send Mail of Email Service Impl");

			return new CustomResponse(HttpStatus.BAD_REQUEST.value(), null, "Email.UnSuccessMsg");
		}
	}

	@Override
	public CustomResponse sendEmail(SendEmailRequest obj) {
		// TODO Auto-generated method stub
		return null;
	}

}