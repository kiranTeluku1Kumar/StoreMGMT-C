package com.business.sotremgmt.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class ProductEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	
	private String productName;
	
	@OneToMany(targetEntity = InventoryEntity.class,
				    cascade = CascadeType.ALL)
	@JoinColumn(name="productId")
	private List<InventoryEntity> inventriesForProduct; 
	
	

}
