package com.onlinestorerestapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	
	private Integer id;
	private String code;
	private String name;
	private double price;
	private int quantity;
	private String image;
}
