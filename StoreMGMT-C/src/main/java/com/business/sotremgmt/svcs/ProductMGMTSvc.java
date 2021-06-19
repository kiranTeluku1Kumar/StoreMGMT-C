package com.business.sotremgmt.svcs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.business.sotremgmt.entities.InventoryEntity;
import com.business.sotremgmt.entities.ProductEntity;
import com.business.sotremgmt.exceps.ProductExistsExcpetion;
import com.business.sotremgmt.repos.ProductRepository;
import com.business.sotremgmt.reqs.ProductRequestObj;
import com.business.sotremgmt.resp.InventoryItem;
import com.business.sotremgmt.resp.ProductRespObj;

@Service
public class ProductMGMTSvc {
	@Autowired
	ProductRepository prodRepo;

	public ProductRespObj createProduct(ProductRequestObj prodReqObj) throws ProductExistsExcpetion {
		
		ProductRespObj prodRespObj = new ProductRespObj();
		
		List<InventoryEntity> productInvetoryEntities = prodRepo.inventoryEntriesByProductName(prodReqObj.getProductName());
		
		if(productInvetoryEntities != null && !productInvetoryEntities.isEmpty()) {
			if(isProductaNameWithMeasuredInExists(productInvetoryEntities, prodReqObj.getMeasuredIn())) {
				throw new ProductExistsExcpetion(prodReqObj.getProductName()+" mesaured in "+prodReqObj.getMeasuredIn()+" already exists ");
			}else {
				ProductEntity prodEntityToSave = prodRepo.findByProductName(prodReqObj.getProductName());
				prodEntityToSave.getInventriesForProduct()
								.add(buildInventoryEntityToSave(prodReqObj));
				ProductEntity prodEntity = prodRepo.save(prodEntityToSave);
				prodRespObj = mapperFunction(prodEntity);
				return prodRespObj;
			}
		}else {
			ProductEntity prodEntityToSave = new ProductEntity();
			prodEntityToSave.setProductName(prodReqObj.getProductName());
			prodEntityToSave.setInventriesForProduct(Arrays.asList(buildInventoryEntityToSave(prodReqObj)));
			ProductEntity prodEntity = prodRepo.save(prodEntityToSave);
			prodRespObj = mapperFunction(prodEntity);
			return prodRespObj;
		}
	}

	private InventoryEntity buildInventoryEntityToSave(ProductRequestObj prodReqObj) {
		InventoryEntity invenotry = new InventoryEntity();
		invenotry.setPricePerQty(prodReqObj.getPricePerQty());
		invenotry.setPouchType(prodReqObj.isPouchType());
		invenotry.setMeasuredIn(prodReqObj.getMeasuredIn());
		invenotry.setQuantityInStock(prodReqObj.getQuantityInStock());
		return invenotry;
	}

	private boolean isProductaNameWithMeasuredInExists(List<InventoryEntity> prodEntities, String measuredIn) {
		return prodEntities.stream()
					.flatMap(pI -> Stream.of(pI.getMeasuredIn()))
					.collect(Collectors.toUnmodifiableList())
					.stream()
					.anyMatch(pI -> pI.equalsIgnoreCase(measuredIn));
	}

	public ResponseEntity<List<ProductRespObj>> getAllProducts() {
		return convertEntityListToRespList(prodRepo.findAll());
	}

	
	public ResponseEntity<List<ProductRespObj>> getAllProductsByProductId(int productId) {
		Optional<ProductEntity> findById = prodRepo.findById(new Long(productId));
		if(!findById.isPresent()) {
			return null;
		}
		return convertEntityListToRespList(Arrays.asList(findById.get()));
	}
	
	private ResponseEntity<List<ProductRespObj>> convertEntityListToRespList(List<ProductEntity> findAllCusts) {
		
		return ResponseEntity.of(Optional.ofNullable(findAllCusts.stream()
				.map(entity -> mapperFunction(entity))
				.collect(Collectors.toList())));
	}

	private ProductRespObj mapperFunction(ProductEntity entity) {
		ProductRespObj prodRespObj =new ProductRespObj();
		prodRespObj.setProductName(entity.getProductName());
		prodRespObj.setProductId(entity.getProductId());
		prodRespObj.setInventoriesForProduct(mapperInventoryItems(entity.getInventriesForProduct()));
		return prodRespObj;
	}

	private List<InventoryItem> mapperInventoryItems(List<InventoryEntity> inventoryEntityList) {
		return inventoryEntityList.stream()
						.map(buildInventoryItems())
						.collect(Collectors.toUnmodifiableList());
	}

	private Function<? super InventoryEntity, ? extends InventoryItem> buildInventoryItems() {
		return ent -> {
			InventoryItem invenotry = new InventoryItem();
			invenotry.setPricePerQty(ent.getPricePerQty());
			invenotry.setPouchType(ent.isPouchType());
			invenotry.setMeasuredIn(ent.getMeasuredIn());
			invenotry.setQuantityInStock(ent.getQuantityInStock());
			return invenotry;
		};
	}

}
