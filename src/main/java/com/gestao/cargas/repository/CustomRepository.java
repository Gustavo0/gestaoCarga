package com.gestao.cargas.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomRepository<T>{
	
	public List<T> findDeliverys(Long deliveryId, Long vehicle);

	Page<T> findDeliverysPage(Long deliveryId, Long vehicle, Pageable page);

}
