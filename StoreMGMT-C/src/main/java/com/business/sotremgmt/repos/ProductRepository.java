package com.business.sotremgmt.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.business.sotremgmt.entities.InventoryEntity;
import com.business.sotremgmt.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{

	@Query("SELECT p.inventriesForProduct FROM ProductEntity p where product_name= :productName")
	List<InventoryEntity> inventoryEntriesByProductName(String productName);

	ProductEntity findByProductName(String productName);
	
}