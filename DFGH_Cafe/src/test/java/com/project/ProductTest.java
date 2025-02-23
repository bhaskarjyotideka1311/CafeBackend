package com.project;

import com.project.JWT.JwtFilter;
import com.project.POJO.Category;
import com.project.POJO.Product;
import com.project.Rest.ProductRest;
import com.project.RestImpl.ProductService;
import com.project.Wrapper.ProductWrapper;
import com.project.dao.ProductDao;
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
import static org.mockito.Mockito.*;

class ProductTest {

    @Mock
    private ProductDao productDao;

    @Mock
    private JwtFilter jwtFilter;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addNewProduct_WithValidRequestAndAdminUser_ReturnsOkResponse() {
        
        when(jwtFilter.isAdmin()).thenReturn(true);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("name", "New Product");
        requestMap.put("categoryId", "1");
        requestMap.put("price", "10");

        
        ResponseEntity<String> response = productService.addNewProduct(requestMap);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals("Product added successfully!!!", response.getBody());
        verify(productDao).save(any(Product.class));
    }

    @Test
    void addNewProduct_WithValidRequestAndNonAdminUser_ReturnsUnauthorizedResponse() {
        
        when(jwtFilter.isAdmin()).thenReturn(false);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("name", "New Product");
        requestMap.put("categoryId", "1");
        requestMap.put("price", "10");

        
        ResponseEntity<String> response = productService.addNewProduct(requestMap);

        
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        //assertEquals("Unauthorized access", response.getBody());
        verify(productDao, never()).save(any(Product.class));
    }

    @Test
    void addNewProduct_WithInvalidRequest_ReturnsBadRequestResponse() {
        
        when(jwtFilter.isAdmin()).thenReturn(true);
        Map<String, String> requestMap = new HashMap<>();

        
        ResponseEntity<String> response = productService.addNewProduct(requestMap);

        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        //assertEquals("Invalid data", response.getBody());
        verify(productDao, never()).save(any(Product.class));
    }

    @Test
    void getAllProducts_ReturnsAllProductsFromDao() {
        
        List<ProductWrapper> products = Collections.singletonList(new ProductWrapper());
        when(productDao.getAllProducts()).thenReturn(products);

       
        ResponseEntity<List<ProductWrapper>> response = productService.getAllProducts();

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(products, response.getBody());
        verify(productDao).getAllProducts();
    }

    @Test
    void updateProduct_WithValidRequestAndAdminUserAndExistingProductId_ReturnsOkResponse() {
        
        when(jwtFilter.isAdmin()).thenReturn(true);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("id", "1");
        requestMap.put("name", "Updated Product");
        requestMap.put("categoryId", "1");
        requestMap.put("price", "20");
        when(productDao.findById(1)).thenReturn(Optional.of(new Product()));

        
        ResponseEntity<String> response = productService.updateProduct(requestMap);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals("Product Updated Successfully!", response.getBody());
        verify(productDao).save(any(Product.class));
    }

    @Test
    void updateProduct_WithValidRequestAndAdminUserAndNonExistingProductId_ReturnsOkResponse() {
        
        when(jwtFilter.isAdmin()).thenReturn(true);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("id", "1");
        requestMap.put("name", "Updated Product");
        requestMap.put("categoryId", "1");
        requestMap.put("price", "20");
        when(productDao.findById(1)).thenReturn(Optional.empty());

       
        ResponseEntity<String> response = productService.updateProduct(requestMap);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals("Product id doesn't exist", response.getBody());
        verify(productDao, never()).save(any(Product.class));
    }

//    @Test
//    void updateProduct_WithInvalidRequest_ReturnsBadRequestResponse() {
//        
//        when(jwtFilter.isAdmin()).thenReturn(true);
//        Map<String, String> requestMap = new HashMap<>();
//
//        
//        ResponseEntity<String> response = productService.updateProduct(requestMap);
//
//       
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//        //assertEquals("Invalid data", response.getBody());
//        verify(productDao, never()).findById(anyInt());
//        verify(productDao, never()).save(any(Product.class));
//    }

    @Test
    void updateProduct_WithNonAdminUser_ReturnsUnauthorizedResponse() {
       
        when(jwtFilter.isAdmin()).thenReturn(false);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("id", "1");
        requestMap.put("name", "Updated Product");
        requestMap.put("categoryId", "1");
        requestMap.put("price", "20");

        
        ResponseEntity<String> response = productService.updateProduct(requestMap);

        
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        //assertEquals("Unauthorized access", response.getBody());
        verify(productDao, never()).findById(anyInt());
        verify(productDao, never()).save(any(Product.class));
    }

    @Test
    void deleteProduct_WithValidIdAndAdminUserAndExistingProductId_ReturnsOkResponse() {
        
        when(jwtFilter.isAdmin()).thenReturn(true);
        int productId = 1;
        when(productDao.findById(productId)).thenReturn(Optional.of(new Product()));

        
        ResponseEntity<String> response = productService.deleteProduct(productId);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals("Product deleted successfully.", response.getBody());
        verify(productDao).deleteById(productId);
    }

    @Test
    void deleteProduct_WithValidIdAndAdminUserAndNonExistingProductId_ReturnsOkResponse() {
        
        when(jwtFilter.isAdmin()).thenReturn(true);
        int productId = 1;
        when(productDao.findById(productId)).thenReturn(Optional.empty());

        
        ResponseEntity<String> response = productService.deleteProduct(productId);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals("Product id does not exist.", response.getBody());
        verify(productDao, never()).deleteById(productId);
    }

    @Test
    void deleteProduct_WithNonAdminUser_ReturnsUnauthorizedResponse() {
        
        when(jwtFilter.isAdmin()).thenReturn(false);
        int productId = 1;

        
        ResponseEntity<String> response = productService.deleteProduct(productId);

        
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        //assertEquals("Unauthorized access", response.getBody());
        verify(productDao, never()).findById(anyInt());
        verify(productDao, never()).deleteById(anyInt());
    }

    @Test
    void updateStatus_WithValidRequestAndAdminUserAndExistingProductId_ReturnsOkResponse() {
        
        when(jwtFilter.isAdmin()).thenReturn(true);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("id", "1");
        requestMap.put("status", "active");
        when(productDao.findById(1)).thenReturn(Optional.of(new Product()));

        
        ResponseEntity<String> response = productService.updateStatus(requestMap);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals("Product status updated successfully.", response.getBody());
        verify(productDao).updateProductStatus("active", 1);
    }

    @Test
    void updateStatus_WithValidRequestAndAdminUserAndNonExistingProductId_ReturnsOkResponse() {
        
        when(jwtFilter.isAdmin()).thenReturn(true);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("id", "1");
        requestMap.put("status", "active");
        when(productDao.findById(1)).thenReturn(Optional.empty());

        
        ResponseEntity<String> response = productService.updateStatus(requestMap);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals("Product id does not exist", response.getBody());
        verify(productDao, never()).updateProductStatus(anyString(), anyInt());
    }

//    @Test
//    void updateStatus_WithNonAdminUser_ReturnsUnauthorizedResponse() {
//        
//        when(jwtFilter.isAdmin()).thenReturn(false);
//        Map<String, String> requestMap = new HashMap<>();
//        requestMap.put("id", "1");
//        requestMap.put("status", "active");
//
//        
//        ResponseEntity<String> response = productService.updateStatus(requestMap);
//
//       
//        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
//        //assertEquals("Unauthorized access", response.getBody());
//        verify(productDao, never()).findById(anyInt());
//        verify(productDao, never()).updateProductStatus(anyString(), anyInt());
//    }

    @Test
    void getByCategory_ReturnsProductsByCategoryIdFromDao() {
        
        int categoryId = 1;
        List<ProductWrapper> products = Collections.singletonList(new ProductWrapper());
        when(productDao.getProductByCategory(categoryId)).thenReturn(products);

       
        ResponseEntity<List<ProductWrapper>> response = productService.getByCategory(categoryId);

        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(products, response.getBody());
        verify(productDao).getProductByCategory(categoryId);
    }

    @Test
    void getProductById_WithExistingProductId_ReturnsProductFromDao() {
       
        int productId = 1;
        ProductWrapper product = new ProductWrapper();
        when(productDao.getProductById(productId)).thenReturn(product);

        
        ResponseEntity<ProductWrapper> response = productService.getProductById(productId);

       
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(product, response.getBody());
        verify(productDao).getProductById(productId);
    }

//    @Test
//    void getProductById_WithNonExistingProductId_ReturnsInternalServerErrorResponse() {
//        
//        int productId = 1;
//        when(productDao.getProductById(productId)).thenReturn(null);
//
//        
//        ResponseEntity<ProductWrapper> response = productService.getProductById(productId);
//
//        
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertEquals(new ProductWrapper(), response.getBody());
//        verify(productDao).getProductById(productId);
//    }
}

