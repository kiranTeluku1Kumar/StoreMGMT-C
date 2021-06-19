package com.business.sotremgmt.ctrls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.business.sotremgmt.exceps.ProductExistsExcpetion;
import com.business.sotremgmt.reqs.ProductRequestObj;
import com.business.sotremgmt.resp.ProductRespObj;
import com.business.sotremgmt.resp.RequestStatus;
import com.business.sotremgmt.svcs.ProductMGMTSvc;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ProductMGMTController {

	@Autowired
	ProductMGMTSvc prodMgmtSvc;

	@RequestMapping(method = RequestMethod.POST, path = "/saveProductDet")
	public ProductRespObj productReg(@RequestBody ProductRequestObj prodReqObj) {
		ProductRespObj prodRespObjOpt = null;
		try {
			Optional.of(prodMgmtSvc.createProduct(prodReqObj));
		}catch (ProductExistsExcpetion cee){
			return buildRespObj(null, HttpStatus.CONFLICT.name(), cee.getMessage());
		}catch (Exception e) {
			return buildRespObj(null, HttpStatus.INTERNAL_SERVER_ERROR.name(), e.getMessage());
		}
		prodRespObjOpt = buildRespObj(prodRespObjOpt, HttpStatus.OK.name(), "your Request Completed Successfully");
		return prodRespObjOpt;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/products")
	public ResponseEntity<List<ProductRespObj>> fetchAllProducts(){
		return prodMgmtSvc.getAllProducts();
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/products/{productId}")
	public ResponseEntity<List<ProductRespObj>> fetchAllProducts(int productId){
		return prodMgmtSvc.getAllProductsByProductId(productId);
	}

	private ProductRespObj buildRespObj(ProductRespObj prodRespObj, String statusCode, String statusDesc) {

		RequestStatus reqStats = new RequestStatus();
		reqStats.setReqStatCode(statusCode);
		reqStats.setReqStatDesc(statusDesc);
		if(prodRespObj ==null) {
			prodRespObj = new ProductRespObj();
		}
		prodRespObj.setReqStatus(reqStats);
		return prodRespObj;
	}

}
