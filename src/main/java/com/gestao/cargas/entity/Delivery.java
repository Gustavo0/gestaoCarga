package com.gestao.cargas.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@Entity
public class Delivery {
	
	@Id
	private Long deliveryId;
	private Long vehicle;
	
	public Delivery(Long deliveryId, Long vehicle) {
		super();
		this.deliveryId = deliveryId;
		this.vehicle = vehicle;
	}
	
	public Delivery() {
		
	}

}
