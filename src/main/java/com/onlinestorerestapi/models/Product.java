package com.onlinestorerestapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
