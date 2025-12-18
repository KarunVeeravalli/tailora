package com.tailora.util;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tailora.common.Header;
import com.tailora.dto.request.Request;
import com.tailora.dto.request.UserInputDto;
import com.tailora.model.UnAuthUser;
import com.tailora.model.UserProfile;
import com.tailora.repository.UserProfileRepo;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class Helper {
	
	@Autowired
	private UserProfileRepo userProfileRepo;
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	 @Value("${tailora.app.sendermail}")
	  private String fromEmail;
	
	 @Value("${tailora.req-res.encryption}")
	 private String encryptedFlag;
	 
		@Value("${tailora.encrypted.secret}")
		private String secretKey;		 
	 
	 public String getMail() {
		 return fromEmail;
	 }
	 
	public String getRequestLogger(Class<?> className,String method, Object request , String reqId) {
		return ("<------ REQ-ID: "+reqId+"  "+className.getName()+": "+method+" (BEGINS) with request => {"+ request+" } ------>");
	}
	
	public String getResponseLogger(Class<?> className,String method, Object response, String reqId ) {
		return ("<------ REQ-ID: "+reqId+"  "+className.getName()+": "+method+" (ENDS) with response => {"+ response+" } ------>");
	}
	
	public UnAuthUser getUnAuthUserByDto(UserInputDto dto) {
		UnAuthUser user = new UnAuthUser();
		user.setEmail(dto.getEmail());
		user.setMobileNumber(dto.getMobileNumber());
		user.setPassword(dto.getPassword());
		user.setUsername(dto.getUsername());
		user.setCreatedDateTime(LocalDateTime.now());
		return user;
	}
	
	public String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}

		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
	
	public String getOtp() {
		Integer upper = 999999;
		Integer lower = 111111;
		Integer otp = (int) (Math.random() * (upper - lower)) + lower;
		return otp.toString();
	}
	public Boolean isUserExistsByEmail(String emial) {
		UserProfile profile = userProfileRepo.getUserByEmail(emial);
		return profile != null ? true : false;
	}
	
	public Boolean isUserExistsByUsername(String username) {
		UserProfile profile = userProfileRepo.getUserByUsername(username);
		return profile != null ? true : false;
	}
	
	public String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7);
		}

		return null;
	}
	
	public  String object2String(Object req) {
		try {
			String jsonString = mapper.writeValueAsString(req);
			if(encryptedFlag.equals("true")) {
				jsonString = encryptAES(jsonString, secretKey);
				return jsonString;
			}
			return req.toString();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Some error while Encoding");
		}
	}	
	
	
	
	public static String encryptAES(String data, String secretKey) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(data.getBytes());

        return Base64.getEncoder().encodeToString(encrypted);
    }
	public static String decryptAES(String encryptedData, String secretKey) throws Exception {
	    SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	    cipher.init(Cipher.DECRYPT_MODE, keySpec);
	    byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
	    return new String(decrypted);
	}
	
	public  Header getHeader(String req) {
		try {
			if(encryptedFlag.equals("true")) {
				req = decryptAES(req, secretKey);
			}
			
			Request<?> request = mapper.readValue(req, Request.class);

	        Object bodyObj = request.getRequestHeader();
	        String bodyJson = mapper.writeValueAsString(bodyObj); 
	        return mapper.readValue(bodyJson, Header.class); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Some error while decoding the Header");
		}
	}
	

	public  <T> T string2Object(String req, Class<T> type) {
		try {
			if(encryptedFlag.equals("true")) {
				req = decryptAES(req, secretKey);
			}
			System.out.println(req);
			Request<?> request = mapper.readValue(req, Request.class);
			System.out.println(request);
	        Object bodyObj = request.getRequestBody();
	        String bodyJson = mapper.writeValueAsString(bodyObj); 
	        return mapper.readValue(bodyJson, type); 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Some error while decoding");
		}
	}	
	

}
