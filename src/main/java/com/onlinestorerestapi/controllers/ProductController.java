package com.onlinestorerestapi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onlinestorerestapi.models.Product;
import com.onlinestorerestapi.requests.ProductForm;

@RestController
@RequestMapping("/products")
public class ProductController {
	private static List<Product> products = new ArrayList<>();
	private static Integer idCount = 0;
	
	   static {
	        products.add(new Product(++idCount, "SS-S9", "Samsung Galaxy S9", 500D, 50, "samsung-s9.png"));
	        products.add(new Product(++idCount, "NK-5P", "Nokia 5.1 Plus", 60D, 60, null));
	        products.add(new Product(++idCount, "IP-7", "iPhone 7", 600D, 30, "iphone-7.png"));
	    }
	   //get all products 
	   @RequestMapping(value = "/list")
	   public ResponseEntity<Object> getProducts() { 
			return new ResponseEntity<> (products,HttpStatus.OK);

	   }
	   //get product by id
	   @RequestMapping(value = "/get/{id}")
	   public ResponseEntity<Object> getPersonsById(@PathVariable("id") Integer id) {
		   for (Product product : products) {
			   if (product.getId() == id) {
				   return new ResponseEntity<>(product, HttpStatus.OK);
			   }
		   } 
		   return new ResponseEntity<>("Failed: Product not found", HttpStatus.NOT_FOUND);
	   }

	   
	   // Creates a new product record.
	   @RequestMapping(value = "/create", method = RequestMethod.POST)
	   public ResponseEntity<Object> createProduct(@RequestBody ProductForm productForm) {
		   products.add(new Product(++idCount,productForm.getCode(), productForm.getName(),productForm.getPrice(), productForm.getQuantity(),productForm.getImage()));
		   return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);		  
	   }
	   
	   // Updates an existing Product record.
	   @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	   public ResponseEntity<Object> updateProduct(@PathVariable("id") Integer id,
	   @RequestBody ProductForm productForm) {
		   for (Product product : products) {
			   if (product.getId() == id) {
				   product.setCode(productForm.getCode());
				   product.setName(productForm.getName());
				   product.setPrice(productForm.getPrice());
				   product.setQuantity(productForm.getQuantity());
				   product.setImage(productForm.getImage());
			   return new ResponseEntity<>("Product is updated successfully", HttpStatus.OK);
			   } } 
			   return new ResponseEntity<>("Failed: Product not found", HttpStatus.NOT_FOUND)	;	   
		   }
	   
	   //Delete a Product record.
	   @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	   public ResponseEntity<Object> deleteProduct(@PathVariable("id") Integer id) {
		   for (Product product : products) {
			   if (product.getId() == id) {
				   products.remove(product);
			   return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);
			   }
			   } 
			   return new ResponseEntity<>("Failed: Product not found", HttpStatus.NOT_FOUND);
		   }
	   
}
