package com.gestao.cargas.dto;

import com.gestao.cargas.entity.Delivery;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class DeliverySaidaDto {
	
	private Long deliveryId;
	private Long vehicle;
	
	public DeliverySaidaDto(Delivery delivery) {
		super();
		this.deliveryId = delivery.getDeliveryId();
		this.vehicle = delivery.getVehicle();
	}

	public DeliverySaidaDto(Long deliveryId, Long vehicle) {
		super();
		this.deliveryId = deliveryId;
		this.vehicle = vehicle;
	}
	
}
