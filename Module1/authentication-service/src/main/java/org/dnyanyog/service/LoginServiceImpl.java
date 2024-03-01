package org.dnyanyog.service;

import java.util.List;

import org.dnyanyog.dto.LoginRequest;
import org.dnyanyog.dto.LoginResponse;
import org.dnyanyog.encryption.EncryptionService;
import org.dnyanyog.entity.Users;
import org.dnyanyog.enums.ErrorCode;
import org.dnyanyog.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	UsersRepository userRepo;
	
	
	
	@Autowired
	EncryptionService encryptionService;


	public LoginResponse validateUser(LoginRequest loginRequest) {
	    LoginResponse response = new LoginResponse();

	    try {
	        List<Users> receivedData = userRepo.findByUsername(loginRequest.getUsername());

	        if (receivedData.size() == 1) {
	            Users userData = receivedData.get(0);
	            String encryptedPassword = userData.getPassword();
	            String requestPassword = encryptionService.encrypt(loginRequest.getPassword());

	            if (requestPassword.equalsIgnoreCase(encryptedPassword)) {
	                response.setStatus(ErrorCode.VALIDATION_SUCCESS.getStatus());
	                response.setMessage(ErrorCode.VALIDATION_SUCCESS.getMessage());
	            } else {
	                response.setStatus(ErrorCode.VALIDATION_FAIL.getStatus());
	                response.setMessage(ErrorCode.VALIDATION_FAIL.getMessage());
	            }
	        } else {
	            response.setStatus(ErrorCode.USERNAME_NOTFOUND.getStatus());
	            response.setMessage(ErrorCode.USERNAME_NOTFOUND.getMessage());
	        }
	    } catch (Exception e) {
	      
	        e.printStackTrace();
	        response.setStatus(ErrorCode.LOGIN_ERROR.getStatus());
	        response.setMessage(ErrorCode.LOGIN_ERROR.getMessage());
	    }

	    return response;
	}


}

	
	
//	public LoginResponse usersLogin(LoginRequest request) {
//		LoginResponse response = new LoginResponse();
//
//		if (request.getUsername().equalsIgnoreCase("admin") && request.getPassword().equals("admin123")) {
//			response.setStatus("Success");
//			response.setMessage("Login successfully!!");
//		} else {
//			response.setStatus("Fail");
//			response.setMessage("Login fail");
//		}
//		return response;



	
	

