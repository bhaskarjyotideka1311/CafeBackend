package com.project;

import com.project.JWT.JwtFilter;
import com.project.POJO.Category;
import com.project.Rest.CategoryRest;
import com.project.RestImpl.CategoryService;
import com.project.dao.CategoryDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TryTestCategory {

    @Mock
    private JwtFilter jwtFilter;

    @Mock
    private CategoryDao categoryDao;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addNewCategory_WithValidRequestAndAdminUser_ReturnsOkResponse() {
        
        when(jwtFilter.isAdmin()).thenReturn(true);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("name", "New Category");

        
        ResponseEntity<String> response = categoryService.addNewCategory(requestMap);

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals("Category Added Successfully!", response.getBody());
        verify(categoryDao).save(any(Category.class));
    }

    @Test
    void addNewCategory_WithValidRequestAndNonAdminUser_ReturnsUnauthorizedResponse() {
        
        when(jwtFilter.isAdmin()).thenReturn(false);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("name", "New Category");

        
        ResponseEntity<String> response = categoryService.addNewCategory(requestMap);

        
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        //assertEquals("Unauthorized access", response.getBody());
        verify(categoryDao, never()).save(any(Category.class));
    }

    @Test
    void addNewCategory_WithInvalidRequest_ReturnsInternalServerErrorResponse() {
       
        when(jwtFilter.isAdmin()).thenReturn(true);
        Map<String, String> requestMap = new HashMap<>();

        
        ResponseEntity<String> response = categoryService.addNewCategory(requestMap);

        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        //assertEquals("Something went wrong", response.getBody());
        verify(categoryDao, never()).save(any(Category.class));
    }

    @Test
    void getAllCategory_WithFilterValueTrue_ReturnsCategoryListFromDao() {
        
        when(categoryDao.getAllCategory()).thenReturn(Collections.singletonList(new Category("Category 1", 1)));
        String filterValue = "true";

        
        ResponseEntity<List<Category>> response = categoryService.getAllCategory(filterValue);

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        //assertEquals(1, response.getBody().get(0).getId());
        assertEquals("Category 1", response.getBody().get(0).getName());
        verify(categoryDao, never()).findAll();
    }

    @Test
    void getAllCategory_WithNullFilterValue_ReturnsAllCategoriesFromDao() {
        
        when(categoryDao.findAll()).thenReturn(Collections.singletonList(new Category("Category 1", 1)));
        String filterValue = null;

        
        ResponseEntity<List<Category>> response = categoryService.getAllCategory(filterValue);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        //assertEquals(1, response.getBody().get(0).getId());
        assertEquals("Category 1", response.getBody().get(0).getName());
        verify(categoryDao, never()).getAllCategory();
    }

    @Test
    void updateCategory_WithValidRequestAndAdminUserAndExistingCategoryId_ReturnsOkResponse() {
        
        when(jwtFilter.isAdmin()).thenReturn(true);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("id", "1");
        requestMap.put("name", "Updated Category");
        when(categoryDao.findById(1)).thenReturn(Optional.of(new Category("Category 1", 1)));

        
        ResponseEntity<String> response = categoryService.updateCategory(requestMap);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals("Category Updated Successfully", response.getBody());
        verify(categoryDao).save(any(Category.class));
    }

    @Test
    void updateCategory_WithValidRequestAndAdminUserAndNonExistingCategoryId_ReturnsOkResponse() {
        
        when(jwtFilter.isAdmin()).thenReturn(true);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("id", "1");
        requestMap.put("name", "Updated Category");
        when(categoryDao.findById(1)).thenReturn(Optional.empty());

        
        ResponseEntity<String> response = categoryService.updateCategory(requestMap);

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals("Category ID doesn't exist!", response.getBody());
        verify(categoryDao, never()).save(any(Category.class));
    }

    @Test
    void updateCategory_WithInvalidRequest_ReturnsBadRequestResponse() {
        
        when(jwtFilter.isAdmin()).thenReturn(true);
        Map<String, String> requestMap = new HashMap<>();

        
        ResponseEntity<String> response = categoryService.updateCategory(requestMap);

        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        //assertEquals("Invalid data", response.getBody());
        verify(categoryDao, never()).findById(anyInt());
        verify(categoryDao, never()).save(any(Category.class));
    }

    @Test
    void updateCategory_WithNonAdminUser_ReturnsUnauthorizedResponse() {
        
        when(jwtFilter.isAdmin()).thenReturn(false);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("id", "1");
        requestMap.put("name", "Updated Category");

        
        ResponseEntity<String> response = categoryService.updateCategory(requestMap);

        
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        //assertEquals("Unauthorized access", response.getBody());
        verify(categoryDao, never()).findById(anyInt());
        verify(categoryDao, never()).save(any(Category.class));
    }
    
    

}

