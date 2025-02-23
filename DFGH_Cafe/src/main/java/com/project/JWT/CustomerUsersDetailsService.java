package com.project.JWT;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.dao.UserDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerUsersDetailsService implements UserDetailsService{

	@Autowired
	UserDao userDao;
	
	private com.project.POJO.User userDetail;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
//		log.info("Inside loadUserByUsername{}",username);
		userDetail = userDao.findByEmailId(username);
		if(!Objects.isNull(userDetail))
			return new User(userDetail.getEmail(),userDetail.getPassword(),new ArrayList<>());
		else
			throw new UsernameNotFoundException("User Not Found.");
	}
	
	public com.project.POJO.User getUserDetail(){
//		com.project.POJO.User user = userDetail;
//		user.setPassword(null);
//		return user;

		return userDetail;
	}

}
