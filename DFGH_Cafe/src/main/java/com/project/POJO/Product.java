package com.project.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Product.getAllProducts", query = "select new com.project.Wrapper.ProductWrapper(p.id,p.name,p.price,p.status,p.category.id,p.category.name) from Product p ")
@NamedQuery(name = "Product.updateProductStatus", query = "update Product p set p.status=:status where p.id=:id")
@NamedQuery(name = "Product.getProductByCategory", query = "select new com.project.Wrapper.ProductWrapper(p.id,p.name) from Product p where p.category.id=:id and p.status='true'")
@NamedQuery(name = "Product.getProductById", query = "select new com.project.Wrapper.ProductWrapper(p.id,p.name,p.price) from Product p where p.id=:id")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "product")
public class Product implements Serializable {
	public static final long serialVersionUID = 123456L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_fk", nullable = false)
	private Category category;

//	@Column(name = "description")
//	private String description;

	@Column(name = "price")
	private Integer price;

	@Column(name = "status")
	private String status;

//	public void setId(Integer id) {
//		this.id = id;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public void setCategory(Category category) {
//		this.category = category;
//	}
//
////	public void setDescription(String description) {
////		this.description = description;
////	}
//
//	public void setPrice(Integer price) {
//		this.price = price;
//	}
//
//	public void setStatus(String status) {
//		this.status = status;
//	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

//	public Integer getId() {
//		return id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public Category getCategory() {
//		return category;
//	}
//
////	public String getDescription() {
////		return description;
////	}
//
//	public Integer getPrice() {
//		return price;
//	}
//
//	public String getStatus() {
//		return status;
//	}

	//for test
//	public Product(int i, String string, String string2, int j) {
//		// TODO Auto-generated constructor stub
//	}

}
