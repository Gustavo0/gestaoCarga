package com.gestao.cargas.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
@Entity
public class Package {

	@Id
	private Long id;
	private Double weight;
	
	@ManyToOne
	@JoinColumn(name = "deliveryId")
	private Delivery delivery;
	
	public Package(){
		
	}

	public Package(Long id, Double weight, Delivery delivery) {
		super();
		this.id = id;
		this.weight = weight;
		this.delivery = delivery;
	}

}
