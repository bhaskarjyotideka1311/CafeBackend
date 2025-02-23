package com.project.RestImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.project.utils.CafeUtils;
import com.project.utils.EmailUtils;
import com.google.common.base.Strings;
import com.project.Constants.CafeConstants;
import com.project.JWT.CustomerUsersDetailsService;
import com.project.JWT.JwtFilter;
import com.project.JWT.JwtUtil;
import com.project.POJO.User;
import com.project.Wrapper.UserWrapper;
import com.project.dao.UserDao;

@RestController
public class UserService implements com.project.Rest.UserRest {

	@Autowired
	UserDao userDao;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	CustomerUsersDetailsService customerUsersDetailsService;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	JwtFilter jwtFilter;

	@Autowired
	EmailUtils emailUtils;

//  ---------------------------------------SIGN UP--------------------------------------

	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {

//    	        logger.info("Inside signup{}", requestMap);
		System.out.println("inside signup" + requestMap);
		try {
			if (validateSignUpMap(requestMap)) {
				User user = userDao.findByEmailId(requestMap.get("email"));

				if (Objects.isNull(user)) {
					userDao.save(getUserFromMap(requestMap));
					return CafeUtils.getResponseEntity("Signup, successfully.", HttpStatus.OK);
				} else {
					return CafeUtils.getResponseEntity("Email already exist.", HttpStatus.BAD_REQUEST);
				}
			} else {
				return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	private boolean validateSignUpMap(Map<String, String> requestMap) {
		if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email")
				&& requestMap.containsKey("password")) {
			return true;
		}
		return false;
	}

	private User getUserFromMap(Map<String, String> requestMap) {
		User user = new User();
		user.setName(requestMap.get("name"));
		user.setContactNumber(requestMap.get("contactNumber"));
		user.setEmail(requestMap.get("email"));
		user.setPassword(requestMap.get("password"));
		user.setStatus("true");
		user.setRole("user");
		return user;
	}

	// --------------------------------------------------LOGIN-----------------------------------------------------

	public ResponseEntity<String> login(Map<String, String> requestMap) {
		// log.info("inside login");
		System.out.println("inside login");
		try {
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
			if (auth.isAuthenticated()) {
				if (customerUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
					return new ResponseEntity<String>(
							"{\"token\":\""
									+ jwtUtil.generateToken(customerUsersDetailsService.getUserDetail().getEmail(),
											customerUsersDetailsService.getUserDetail().getRole())
									+ "\"}",
							HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("{\"message\":\"" + "Wait for admin approval." + "\"}",
							HttpStatus.BAD_REQUEST);
				}
			}
		} catch (Exception e) {
			// log.error("{}",e);
//			System.out.println(e);
			e.printStackTrace();
		}
		return new ResponseEntity<String>("{\"message\":\"" + "Wrong Credentials" + "\"}", HttpStatus.BAD_REQUEST);

	}



	// --------------------------------GET All_USERS/ADMINS--------------------------------------
	@Override
	public ResponseEntity<List<UserWrapper>> getAllUser() {
		try {
			if (jwtFilter.isAdmin()) {
				return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}



	//---------------------------UPDATE_STATUS-------------------------
	@Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<User> optionalUser = userDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optionalUser.isEmpty()) {
                    userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    return CafeUtils.getResponseEntity("User Status Updated Successfully!", HttpStatus.OK);
                } else {
                    CafeUtils.getResponseEntity("User id doesn't exist", HttpStatus.OK);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORISED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
