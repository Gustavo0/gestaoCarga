package com.gestao.cargas.dto;

import java.util.List;

import com.gestao.cargas.entity.Delivery;
import com.gestao.cargas.entity.Package;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class DeliveryDto {
	
	private Long deliveryId;
	private Long vehicle;
	private List<Package> packages;
	
	public DeliveryDto(Delivery delivery) {
		super();
		this.deliveryId = delivery.getDeliveryId();
		this.vehicle = delivery.getVehicle();
	}
	
	public DeliveryDto(Long deliveryId, Long vehicle, List<Package> packages) {
		super();
		this.deliveryId = deliveryId;
		this.vehicle = vehicle;
		this.packages = packages;
	}
	
	

}
