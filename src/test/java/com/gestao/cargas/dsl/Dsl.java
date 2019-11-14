package com.gestao.cargas.dsl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestao.cargas.dto.DeliveryDto;
import com.gestao.cargas.entity.Delivery;
import com.gestao.cargas.entity.Package;
import com.gestao.cargas.repository.PackagesRepository;
import com.gestao.cargas.service.DeliveryService;

@Component
public class Dsl {
	
	@Autowired
	private PackagesRepository packagesRepository;
	
	@Autowired
	private DeliveryService deliveryService;
	
	public Delivery dadoUmaDeliveryComPackagesCadastrado() {
		DeliveryDto delivery = criaDeliveryDto();
		
		return deliveryService.cadastrar(delivery);
	}
	
	public List<Package> buscaPackagesByDelivery(Delivery delivery) {
		return packagesRepository.findByDelivery(delivery);
	}

	private DeliveryDto criaDeliveryDto() {
		return DeliveryDto.builder()
						  .deliveryId(1L)
						  .vehicle(1L)
						  .packages(criaListaPackage())
						  .build();
	}

	private List<Package> criaListaPackage() {
		Package pack = Package.builder()
							  .id(1L)
							  .weight(14.5)
							  .build();
		
		List<Package> listPack = new ArrayList<>();
		listPack.add(pack);
		return listPack;
	}

	public String dadoUmaRequestBody(boolean isSemErro) {
		if(isSemErro){
			return "{\"vehicle\":\"1\",\"deliveryId\":\"1\",\"packages\":[{\"id\":\"1\",\"weight\":\"14.5\"},{\"id\":\"2\",\"weight\":\"12.15\"},{\"id\":\"3\",\"weight\":\"19.5\"}]}";
		}else {
			return "{\"vehicle\":\"A\",\"deliveryId\":\"1\",\"packages\":[{\"id\":\"1\",\"weight\":\"14.5\"},{\"id\":\"2\",\"weight\":\"12.15\"},{\"id\":\"3\",\"weight\":\"19.5\"}]}";
		}
	}
		

}
