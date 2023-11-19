package com.onlinestorerestapi.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onlinestorerestapi.models.Product;
import com.onlinestorerestapi.models.requests.ProductForm;

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
		   if(products.size()==0)
		   {
				return new ResponseEntity<> ("No product found",HttpStatus.NO_CONTENT);
			   
		   }
			return new ResponseEntity<> (products,HttpStatus.OK);

	   }
	   //get product by id
	   //or you can use GetMapping("/{id}")
	   @RequestMapping(value = "/get/{id}")
	   public ResponseEntity<Object> getProductById(@PathVariable("id") String id) {
		  /* for (Product product : products) {
			   if (product.getId() == id) {*/
		   		Product product=this.findProductById(Integer.parseInt(id));
		   			if(product!=null)
		   			{
						   return new ResponseEntity<>(product, HttpStatus.OK);
		   				
		   			}
			  // }
		   
		   return new ResponseEntity<>("Failed: Product not found", HttpStatus.NOT_FOUND);
	   }
	   
	   @GetMapping("/code/{code}")
	   public ResponseEntity<Object> getProductBycode(@PathVariable("code") String code) {
			Product product=this.findProductByCode(code);
   			if(product!=null)
   			{
				   return new ResponseEntity<>(product, HttpStatus.OK);
   				
   			}
	
		   return new ResponseEntity<>("Failed: Product not found", HttpStatus.NOT_FOUND);

	   }
	   
	   @GetMapping("/sorted")
	   public ResponseEntity<Object> getSortedProductByPrice() {
			
		   return new ResponseEntity<>(this.listProductSortedAsc(), HttpStatus.OK);
   				   
	   }
	   

	   @GetMapping("/sortedRev")
	   public ResponseEntity<Object> getSortedProductByPriceDesc() {
			
		   return new ResponseEntity<>(this.listProductSortedDesc(), HttpStatus.OK);
   				   
	   }
	   
	   
	   // you can create a method find productById 
	   private Product findProductById(Integer id)
	   {
		   for (Product product : products) {
			   if (product.getId() == id) 
				   return product;
			   
		   }
		   return null;
	   }
	   
	   //filtrage 

	   // you can create a method find productById 
	  

	   private Product findProductByIdFilter(Integer id) {
		   //compare evey product id exist with the id in pathVariable
		    return this.products.stream()
		            .filter(p -> p.getId() == id)
		            .findFirst().get();
		            //add try catch while i call this method
		    }
	   
	   
	   // you can create a method find productByCode 
	   private Product findProductByCode(String code)
	   {
		   for (Product product : products) {
			   if (product.getCode().equals(code)) 
				   return product;
			   
		   }
		   return null;
	   }
	   
	   
	   //sorted method
	   private ArrayList<Product> listProductSortedAsc ()
	   {
		   ArrayList<Product> productList=new ArrayList<>(products);
		   //interface collections disponible sous java on utilise la methode sort pour le tri
		   // pour la sortDesc just compare(p2.getPrice(), p1.getPrice())
		   Collections.sort(productList,(p1,p2)->Double.compare(p1.getPrice(), p2.getPrice()) );
		   return productList;
		   
		   
	   }

	   //sorted method des
	   private ArrayList<Product> listProductSortedDesc ()
	   {
		   ArrayList<Product> productList=new ArrayList<>(products);
		   //interface collections disponible sous java on utilise la methode sort pour le tri
		   // pour la sortDesc just compare(p2.getPrice(), p1.getPrice())
		   Collections.sort(productList,(p1,p2)->Double.compare(p2.getPrice(), p1.getPrice()) );
		   return productList;
		   
		   
	   }
	   
	   // Creates a new product record.
	   
	   @RequestMapping(value = "/create", method = RequestMethod.POST)
	   public ResponseEntity<Object> createProduct(@RequestBody ProductForm productForm) {
		   products.add(new Product(++idCount,
				   productForm.getCode(),
				   productForm.getName(),
				   productForm.getPrice(),
				   productForm.getQuantity(),
				   productForm.getImage()));
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
	   public ResponseEntity<Object> deleteProduct(@PathVariable("id") String id) {
		 /*  for (Product product : products) {
			   if (product.getId() == id) {
				   products.remove(product);
			   return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);
			   }
			   } */
		   	
			Product product=this.findProductById(Integer.parseInt(id));
   			if(product!=null)
   			{
   			   products.remove(product);
			   return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);
			     				
   			}
	
			   return new ResponseEntity<>("Failed: Product not found", HttpStatus.NOT_FOUND);
		   }
	   
}
