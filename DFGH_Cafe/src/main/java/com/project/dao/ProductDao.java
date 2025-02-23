package com.project.dao;

import com.project.POJO.Product;
import com.project.Wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
//import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.transaction.Transactional;

public interface ProductDao extends JpaRepository<Product, Integer> {
	List<ProductWrapper> getAllProducts();

	@Modifying
	@Transactional
	Integer updateProductStatus(@Param("status") String status, @Param("id") Integer id);

	List<ProductWrapper> getProductByCategory(@Param("id") Integer id);

	ProductWrapper getProductById(@Param("id") Integer id);
	
	//for test
//	Boolean isProductById(Integer id);
}
