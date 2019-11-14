package com.gestao.cargas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestao.cargas.entity.Delivery;
import com.gestao.cargas.entity.Package;

@Repository
public interface PackagesRepository extends JpaRepository<Package, Long> {
	
	List<Package> findByDelivery(Delivery delivery);

}
