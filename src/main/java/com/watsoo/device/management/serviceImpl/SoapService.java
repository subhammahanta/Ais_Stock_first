package com.watsoo.device.management.serviceImpl;

import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.SoapDTO;

@Service
public class SoapService {

	//@Override
	public String update(SoapDTO request) {
		String str = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\"><soap:Header><context xmlns=\"urn:zimbra\"><userAgent name=\"ZimbraWebClient - GC103 (Linux)\"/><session id=\"31042939\"/><format type=\"js\"/><csrfToken>0_3b5577c04f2cbe37aa1c1d93cf323238864c9b52</csrfToken></context></soap:Header><soap:Body><CreateAccountRequest mailto:xmlns=\"urn:zimbraadmin\"><name>aditya@watsoo.com</name><password>12345678</password><a n=\"zimbraAccountStatus\">active</a><a n=\"displayName\">ADITYA GUPTA</a><a n=\"givenName\">ADITYA</a><a n=\"sn\">GUPTA</a></CreateAccountRequest></soap:Body></soap:Envelope>";
		int nameStartIndex = str.indexOf("<name>");
		int nameEndIndex = str.indexOf("</name>");

		if (nameStartIndex != -1 && nameEndIndex != -1) {
			String nameContent = str.substring(nameStartIndex + "<name>".length(), nameEndIndex);
			str = str.replace(nameContent, request.getName());
		} else {
			System.out.println("Error: <name> tag not found in the SOAP message.");
			return str;
		}
		int passwordStartIndex = str.indexOf("<password>");
		int passwordEndIndex = str.indexOf("</password>");

		if (passwordStartIndex != -1 && passwordEndIndex != -1) {
			String passwordContent = str.substring(passwordStartIndex + "<password>".length(), passwordEndIndex);
			str = str.replace(passwordContent, request.getPassword());
		} else {
			System.out.println("Error: <password> tag not found in the SOAP message.");
			return str;
		}

		String givenNameTag = "<a n=\"givenName\">";
		int startIndex = str.indexOf(givenNameTag);
		if (startIndex != -1) {
			int endIndex = str.indexOf("</a>", startIndex);
			if (endIndex != -1) {
				String oldValue = str.substring(startIndex + givenNameTag.length(), endIndex);
				String newValue = request.getFirstName();
				str = str.replace(oldValue, newValue);
			}
		}

		String snTag = "<a n=\"sn\">";
		int snStartIndex = str.indexOf(snTag);
		if (snStartIndex != -1) {
			int endIndex = str.indexOf("</a>", snStartIndex);
			if (endIndex != -1) {
				String oldValue = str.substring(snStartIndex + snTag.length(), endIndex);
				String newValue = request.getLastName();
				str = str.replace(oldValue, newValue);
			}
		}

		String displayNameTag = "<a n=\"displayName\">";
		int displayNameStartIndex = str.indexOf(displayNameTag);
		if (displayNameStartIndex != -1) {
			int endIndex = str.indexOf("</a>", displayNameStartIndex);
			if (endIndex != -1) {
				String oldValue = str.substring(displayNameStartIndex + displayNameTag.length(), endIndex);
				String newValue = request.getDisplayName();
				str = str.replace(oldValue, newValue);
			}
		}

		String zimbraAccountStatusTag = "<a n=\"zimbraAccountStatus\">";
		int zimbraAccountStatusStartIndex = str.indexOf(zimbraAccountStatusTag);
		if (zimbraAccountStatusStartIndex != -1) {
			int endIndex = str.indexOf("</a>", zimbraAccountStatusStartIndex);
			if (endIndex != -1) {
				String oldValue = str.substring(zimbraAccountStatusStartIndex + zimbraAccountStatusTag.length(),
						endIndex);
				String newValue = request.getZimbraAccountStatus();
				str = str.replace(oldValue, newValue);
			}
		}
		return str;
	}

}