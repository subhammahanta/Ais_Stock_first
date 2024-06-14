package com.watsoo.device.management.util;

import java.util.Base64;
import java.util.Date;
import java.util.regex.Pattern;

import com.watsoo.device.management.dto.LoginResponse;
import com.watsoo.device.management.model.CredentialMaster;

public class TokenUtility {
	public static String tokenPrefix = "W@ts00#";
	
    public static String generateToken(CredentialMaster credentailMstr) {
        Base64.Encoder enc = Base64.getEncoder();
        String toknisedString = tokenValues(credentailMstr);
        String encodedString = enc.encodeToString(toknisedString.getBytes());
        return encodedString;
    }
    
    
    public static LoginResponse decodeToken(String toDecodeString) {
    	LoginResponse response = new LoginResponse();
        Base64.Decoder dec = Base64.getDecoder();
        String decodedString = new String(dec.decode(toDecodeString));
        String[] decodeValues = decodedString.split("||");
        response.setId(Long.valueOf(decodeValues[0]));
        response.setEmail(decodeValues[1]);
        response.setLoginTimeStamp(DateUtil.getDateFromLong(Long.valueOf(decodeValues[2])));
        return response;
    }
    public static Boolean validateToken(Long userId,String token) {
        Base64.Decoder dec = Base64.getDecoder();
        String decodedString = new String(dec.decode(token));
        String[] decodeValues = decodedString.split(Pattern.quote("||"));;
        Long tokenUserId = Long.valueOf(decodeValues[1]);
        if(tokenUserId != null && tokenUserId.intValue() == userId.intValue()) {
        	return true;
        } else {
        	return false;
        }
    }
    
    public static String tokenValues(CredentialMaster credentialMaster) {
    	String toknisedString = tokenPrefix + "||"+ credentialMaster.getId() +"||" + credentialMaster.getEmail() + "||" + new Date().getTime();
    	return toknisedString;
    }
    
    
    public static String getBoxNextNumber(Long stateId) {
		String newEtpNumber = "AIS_" + String.valueOf(stateId) + "_" +String.valueOf(new Date().getTime()) ;
		return newEtpNumber;
	}
   
    public static String getReConfigBoxNumber(Long stateId) {
		String newEtpNumber = "AISR_" + String.valueOf(stateId) + "_" +String.valueOf(new Date().getTime()) ;
		return newEtpNumber;
	}
}