package com.business.sotremgmt.resp;

import lombok.Data;

@Data
public class InventoryItem {

	private String measuredIn;
	
	private int quantityInStock;
	
	private double pricePerQty;
	
	private boolean isPouchType;
}
