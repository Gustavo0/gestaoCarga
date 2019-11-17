package com.gestao.cargas.dto;

import javax.persistence.Id;

import com.gestao.cargas.entity.Movimentos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class SaidaStepsDeliveryDto {
	
	@Id
	private Long step;
	
	private Long packageId;
	private Movimentos from;
	private Movimentos to;
	
	
	public SaidaStepsDeliveryDto(Long step, Long packageId, Movimentos from, Movimentos to) {
		super();
		this.step = step;
		this.packageId = packageId;
		this.from = from;
		this.to = to;
	}


	public SaidaStepsDeliveryDto() {
	}
	
	

}
