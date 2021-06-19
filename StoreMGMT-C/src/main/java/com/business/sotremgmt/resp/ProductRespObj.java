package com.business.sotremgmt.resp;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;


@Data
@JsonInclude(Include.NON_NULL)
public class ProductRespObj {
	
	RequestStatus reqStatus;
	
	private Long productId;
	
	private String productName;
	
	private List<InventoryItem> inventoriesForProduct; 
}
