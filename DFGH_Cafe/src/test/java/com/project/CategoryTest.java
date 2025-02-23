package com.project;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

//import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.project.POJO.Category;
import com.project.RestImpl.CategoryService;
import com.project.dao.CategoryDao;
//import static org.junit.Assert.*;

@SpringBootTest(classes= {CategoryTest.class})
public class CategoryTest {
	
	@Mock
	CategoryDao categoryDao;
	
	@InjectMocks
	CategoryService categoryService;
//  *******************************************	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testAddNewCategory() {
		Category category1 = new Category("testType1", 1);
		Mockito.when(categoryDao.save(Mockito.any(Category.class))).thenReturn(category1);
//		String Map;
		assertEquals(category1, categoryService.addNewCategory(null));
	}
	
	private void assertEquals(Category category1, ResponseEntity<String> addNewCategory) {
		// TODO Auto-generated method stub
		
	}
	

	@Test
	public void testFindAllCategory() {
		Category category1 = new Category("testType1", 1);
		Category category2 = new Category("testType2", 2);
		List<Category> categories = Arrays.asList(category1, category2);
		Mockito.when(categoryDao.findAll()).thenReturn(categories);
		assertEquals(categories, categoryService.getAllCategory("filter value"));
	}
//  *******************************************	
	
	//public List<Category> mycategory;
	//Category category;
//	@Test
//	public void test_getAllCategory()
//	{
//		
//		List<Category> mycategory = new ArrayList<Category>();
//		mycategory.add(new Category("south indian",1));
//		mycategory.add(new Category("north indian",2));
//		
//		when(categoryDao.findAll()).thenReturn(mycategory);
//      ResponseEntity<List<Category>> res = categoryService.getAllCategory()
//		assertEquals(2,categoryService.getAllCategory("filterValue"));
//      assertEquals(HttpStatus.OK, res.getStatusCode());
//      assertEquals(2,res.getBody().size());
//	}
//*********************************************
	private void assertEquals(List<Category> categories, ResponseEntity<List<Category>> allCategory) {
		// TODO Auto-generated method stub
		
	}
	
//	@Test
//	public void test_getCatagoryById() 
//	{
//		List<Category> mycategory = new ArrayList<Category>();
//		mycategory.add(new Category("south indian",1));
//		mycategory.add(new Category("north indian",2));
//		
//		int categoryId=1;
//		when(categoryDao.findAll()).thenReturn(mycategory);
//		assertEquals(categoryId,categoryRestImpl.findById(categoryId).getId());
//	}
	

}
