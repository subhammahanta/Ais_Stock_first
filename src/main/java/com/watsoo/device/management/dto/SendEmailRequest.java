package com.watsoo.device.management.dto;

public class SendEmailRequest {

	private String senderEmail;

	private String senderPassword;

	private String port;

	private String host;

	private String[] toEmailIds;

	private String[] ccEmailIds;

	private String subject;

	private String content;

	private String attachment;

	private String attachmentName;

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getSenderPassword() {
		return senderPassword;
	}

	public void setSenderPassword(String senderPassword) {
		this.senderPassword = senderPassword;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String[] getToEmailIds() {
		return toEmailIds;
	}

	public void setToEmailIds(String[] toEmailIds) {
		this.toEmailIds = toEmailIds;
	}

	public String[] getCcEmailIds() {
		return ccEmailIds;
	}

	public void setCcEmailIds(String[] ccEmailIds) {
		this.ccEmailIds = ccEmailIds;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

}
