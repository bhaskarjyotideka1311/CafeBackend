package com.project.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.project.POJO.User;
import com.project.Wrapper.UserWrapper;

public interface UserDao extends JpaRepository<com.project.POJO.User, Integer> {

	com.project.POJO.User findByEmailId(@Param("email") String email);

	List<UserWrapper> getAllUser();

	List<String> getAllAdmin();
	

	
	@Transactional
	@Modifying
	Integer updateStatus(@Param("status")String status,@Param("id")Integer id);


}
