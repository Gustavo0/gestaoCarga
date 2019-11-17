package com.gestao.cargas.dsl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gestao.cargas.dto.DeliveryDto;
import com.gestao.cargas.dto.SaidaStepsDeliveryDto;
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
	
	public Delivery dadoUmaDeliveryComPackagesCadastrado(Long deliveryId, Long qtdPackage) throws Exception {
		DeliveryDto delivery = criaDeliveryDto(deliveryId, qtdPackage);
		
		return deliveryService.cadastrar(delivery);
	}

	public List<Package> buscaPackagesByDelivery(Delivery delivery) {
		return packagesRepository.findByDelivery(delivery);
	}
	
	public List<SaidaStepsDeliveryDto> montaStepsDelivery(List<Package> packagesNaDelivery) {
		return deliveryService.montaStepsDelivery(packagesNaDelivery);
	}

	private DeliveryDto criaDeliveryDto(Long deliveryId, Long qtdPackage) {
		return DeliveryDto.builder()
						  .deliveryId(deliveryId)
						  .vehicle(1L)
						  .packages(criaListaPackage(qtdPackage))
						  .build();
	}

	private List<Package> criaListaPackage(Long qtdPackage) {
		List<Package> listPack = new ArrayList<>();
		for (int i = 1; i <= qtdPackage; i++) {
			Package pack = Package.builder()
					  .id(Long.valueOf(i))
					  .weight(14.5 + i)
					  .build();
			listPack.add(pack);
		}		
		
		return listPack;
	}

	public String dadoUmaRequestBody(boolean isSemErro) {
		if(isSemErro){
			return "{\"vehicle\":\"1\",\"deliveryId\":\"2\",\"packages\":[{\"id\":\"1\",\"weight\":\"14.5\"},{\"id\":\"2\",\"weight\":\"12.15\"},{\"id\":\"3\",\"weight\":\"19.5\"}]}";
		}else {
			return "{\"vehicle\":\"1\",\"deliveryId\":\"1\",\"packages\":[{\"id\":\"1\",\"weight\":\"14.5\"},{\"id\":\"2\",\"weight\":\"12.15\"},{\"id\":\"3\",\"weight\":\"19.5\"}]}";
		}
	}
		

}
