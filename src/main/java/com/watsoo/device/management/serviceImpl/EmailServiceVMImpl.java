package com.watsoo.device.management.serviceImpl;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.service.EmailServiceVM;

@Service
public class EmailServiceVMImpl implements EmailServiceVM {

	@Autowired
	private JavaMailSender javaMailService;

	@Override
	public void sendDeviceActRenSupportMail(String email, String date, String link) {
		try {
			StringBuilder sb = new StringBuilder();
			MimeMessage mimeMsg = javaMailService.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true);
			sb.append("<html><body>");
	        sb.append("<p>Dear sir/ma'am,</p>");
	        sb.append("<p>I'm excited to inform you that your requested data export is now available. You can download the file using the link:</p>");
	        sb.append("<p><a href=\"" + link + "\">" + link + "</a></p>");
	        sb.append("<p>Please review it carefully, and don't hesitate to contact us for further assistance.</p>");
	        sb.append("<p>Best regards,</p>");
	        sb.append("<p>Team Watsoo</p>");
	        sb.append("</body></html>");
			String[] mails = email.split(",");
			helper.setTo(mails);
			helper.setSubject("All Devices Data");
			helper.setText(sb.toString(), true);
			EmailThread sendEmail = new EmailThread(javaMailService, mimeMsg);
			Thread parallelThread = new Thread(sendEmail);
			parallelThread.setPriority(Thread.MAX_PRIORITY);
			parallelThread.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendUnConfigureDevicesExcelMail(String email, String date, String link) {
		try {
			StringBuilder sb = new StringBuilder();
			MimeMessage mimeMsg = javaMailService.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true);
			sb.append("<html><body>");
	        sb.append("<p>Dear sir/ma'am,</p>");
	        sb.append("<p>I'm excited to inform you that your requested data export is now available. You can download the file using the link:</p>");
	        sb.append("<p><a href=\"" + link + "\">" + link + "</a></p>");
	        sb.append("<p>Please review it carefully, and don't hesitate to contact us for further assistance.</p>");
	        sb.append("<p>Best regards,</p>");
	        sb.append("<p>Team Watsoo</p>");
	        sb.append("</body></html>");
			String[] mails = email.split(",");
			helper.setTo(mails);
			helper.setSubject("All UnConfigure Devices Data");
			helper.setText(sb.toString(), true);
			EmailThread sendEmail = new EmailThread(javaMailService, mimeMsg);
			Thread parallelThread = new Thread(sendEmail);
			parallelThread.setPriority(Thread.MAX_PRIORITY);
			parallelThread.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendConfigureDevicesExcelMail(String email, String date, String link,String requestCode) {
		try {
			StringBuilder sb = new StringBuilder();
			MimeMessage mimeMsg = javaMailService.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true);
			sb.append("<html><body>");
	        sb.append("<p>Dear sir/ma'am,</p>");
	        sb.append("<p>I'm excited to inform you that your requested data export is now available. You can download the file using the link:</p>");
	        sb.append("<p><a href=\"" + link + "\">" + link + "</a></p>");
	        sb.append("<p>Please review it carefully, and don't hesitate to contact us for further assistance.</p>");
	        sb.append("<p>Best regards,</p>");
	        sb.append("<p>Team Watsoo</p>");
	        sb.append("</body></html>");
			String[] mails = email.split(",");
			helper.setTo(mails);
			helper.setSubject("Device Configure Details:"+requestCode);
			helper.setText(sb.toString(), true);
			EmailThread sendEmail = new EmailThread(javaMailService, mimeMsg);
			Thread parallelThread = new Thread(sendEmail);
			parallelThread.setPriority(Thread.MAX_PRIORITY);
			parallelThread.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
