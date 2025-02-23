package com.project.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Category.getAllCategory", query = "select c from Category c where c.id in (select p.category from Product p where p.status='true')")
//@NamedQuery(name = "Category.getAllCategory", query = "select c from Category c")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "category")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "name")
	public String name;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer id;

//	public void setId(int id) {
//		// TODO Auto-generated method stub
//		this.id = id;
//
//	}
//
//	public void setName(String name) {
//		// TODO Auto-generated method stub
//		this.name = name;
//
//	}

	//test
//	public Category(int id,String name) {
//		super();
//		this.name = name;
//		this.id=id;
//	}
//	
	
	

}
