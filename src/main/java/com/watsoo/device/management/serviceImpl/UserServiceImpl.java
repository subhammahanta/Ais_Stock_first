package com.watsoo.device.management.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.watsoo.device.management.dto.Response;
import com.watsoo.device.management.dto.UserDTO;
import com.watsoo.device.management.model.CredentialMaster;
import com.watsoo.device.management.model.User;
import com.watsoo.device.management.model.UserType;
import com.watsoo.device.management.repository.CredentialMasterRepository;
import com.watsoo.device.management.repository.UserRepository;
import com.watsoo.device.management.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CredentialMasterRepository credentialMasterRepository;

	@Value("${user.default.pass}")
	private String defaultPass;

	@Override
	public Response<?> createUser(UserDTO userDTO) {
		try {
			User response = null;
			User user = userDTO.convertToEntity();
			Optional<CredentialMaster> credentialMasterOptional = credentialMasterRepository
					.findByEmail(userDTO.getEmail());
			if (credentialMasterOptional.isPresent()) {
				return new Response<>(HttpStatus.ALREADY_REPORTED.value(), "already exist by this email code");
			}
			if (userDTO != null) {
				List<User> userFound = userRepository.findAll(user.getEmail(), user.getName());
				if (!userFound.isEmpty()) {
					return new Response<>(HttpStatus.ALREADY_REPORTED.value(), "user is already exist");
				}
				response = userRepository.save(user);
				setUserCredentials(user.getEmail(), response.getId(),
						userDTO.getUserType() != null && userDTO.getUserType().getId() != null
								? userDTO.getUserType().getId()
								: 3L,
						user.getName());
			}
			return new Response<>(HttpStatus.CREATED.value(), HttpStatus.CREATED.name(), response);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

	@Override
	public Response<?> findUserById(Long id) {
		try {
			Optional<User> userData = userRepository.findById(id);
			Response<?> response = new Response<>(HttpStatus.NOT_FOUND, "ICCID not Found");
			if (userData != null && userData.isPresent()) {
				response = new Response<>(HttpStatus.OK.value(), HttpStatus.OK.name(), userData);
			} else {
				response.setResponseCode(HttpStatus.NOT_FOUND.value());
			}
			return response;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		}
	}

	public void setUserCredentials(String email, Long userId, Long userTypeId, String name) {
		try {
			CredentialMaster credentials = new CredentialMaster();
			credentials.setEmail(email);
			credentials.setPassword(defaultPass);
			credentials.setIsActive(true);
			credentials.setUserId(new User(userId));
			credentials.setUserTypeId(new UserType(userTypeId));
			credentialMasterRepository.save(credentials);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}

}
