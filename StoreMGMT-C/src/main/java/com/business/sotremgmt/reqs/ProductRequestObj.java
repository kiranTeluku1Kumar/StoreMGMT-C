package com.business.sotremgmt.reqs;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ProductRequestObj {
	
	private String productName;
	
	private String measuredIn;
	
	private int quantityInStock;
	
	private double pricePerQty;
	
	private boolean pouchType;

}
