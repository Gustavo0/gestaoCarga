package com.gestao.cargas.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestao.cargas.dto.DeliveryDto;
import com.gestao.cargas.entity.Delivery;
import com.gestao.cargas.entity.Package;
import com.gestao.cargas.repository.DeliveryRepository;
import com.gestao.cargas.repository.PackagesRepository;

@Service
public class DeliveryService {
	
	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Autowired
	private PackagesRepository packagesRepository;

	public Delivery cadastrar(@Valid DeliveryDto delivery) {
		Delivery deliveryCad = deliveryRepository.save(new Delivery(delivery.getDeliveryId(), delivery.getVehicle()));
		cadastraVinculaPackageToDelivery(delivery, deliveryCad);
		return deliveryCad;
	}

	private void cadastraVinculaPackageToDelivery(DeliveryDto delivery, Delivery deliveryCad) {
		delivery.getPackages().stream().forEach(p -> packagesRepository.save(new Package(p.getId(), p.getWeight(), deliveryCad)));
	}
	
}
