package com.gestao.cargas.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.gestao.cargas.dto.DeliveryDto;
import com.gestao.cargas.dto.DeliverySaidaDto;
import com.gestao.cargas.entity.Delivery;
import com.gestao.cargas.service.DeliveryService;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
	
	@Autowired
	private DeliveryService deliveryService;
	
	@PostMapping
	public ResponseEntity<DeliverySaidaDto> incluir(@RequestBody @Valid DeliveryDto delivery, UriComponentsBuilder uriBuilder) {
		Delivery deliveryCadastrada = deliveryService.cadastrar(delivery);

		URI uri = uriBuilder.path("/delivery/{id}").buildAndExpand(deliveryCadastrada.getDeliveryId()).toUri();
		return ResponseEntity.created(uri).body(new DeliverySaidaDto(deliveryCadastrada));
	}

}
