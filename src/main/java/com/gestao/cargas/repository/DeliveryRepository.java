package com.gestao.cargas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestao.cargas.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>, CustomRepository<Delivery> {
	
	Delivery findByDeliveryId(Long deliveryId);

}
