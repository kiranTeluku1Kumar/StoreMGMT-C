package com.business.sotremgmt.exceps;

public class ProductExistsExcpetion extends Exception {

	private static final long serialVersionUID = -3179884553026245021L;

	public ProductExistsExcpetion(String errorMsg) {
		super(errorMsg);
	}

	
}
