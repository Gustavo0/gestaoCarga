package com.gestao.cargas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestao.cargas.entity.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
	
	Delivery findByDeliveryId(Long deliveryId);

}
