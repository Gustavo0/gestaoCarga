package com.gestao.cargas.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gestao.cargas.dto.DeliveryDto;
import com.gestao.cargas.dto.DeliverySaidaDto;
import com.gestao.cargas.dto.SaidaStepsDeliveryDto;
import com.gestao.cargas.entity.Delivery;
import com.gestao.cargas.entity.Package;
import com.gestao.cargas.service.DeliveryService;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
	
	@Autowired
	private DeliveryService deliveryService;
	
	@PostMapping
	@CacheEvict(value = "steps", allEntries = true)
	public ResponseEntity<DeliverySaidaDto> incluir(@RequestBody @Valid DeliveryDto delivery, UriComponentsBuilder uriBuilder) throws Exception {
		Delivery deliveryCadastrada = deliveryService.cadastrar(delivery);

		URI uri = uriBuilder.path("/delivery/{id}").buildAndExpand(deliveryCadastrada.getDeliveryId()).toUri();
		return ResponseEntity.created(uri).body(new DeliverySaidaDto(deliveryCadastrada));
	}
	
	@GetMapping(value="/{deliveryId}/step")
	@Cacheable(value = "steps")
	public ResponseEntity<List<SaidaStepsDeliveryDto>> consultaInstrucoes(@PathVariable Long deliveryId) {
		List<Package> packagesNaDelivery = deliveryService.buscaDeliveryComPackages(deliveryId);
		List<SaidaStepsDeliveryDto> steps = deliveryService.montaStepsDelivery(packagesNaDelivery);

		return ResponseEntity.ok().body(steps);
	}

}
