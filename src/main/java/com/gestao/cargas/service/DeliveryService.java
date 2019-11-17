package com.gestao.cargas.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestao.cargas.dto.DeliveryDto;
import com.gestao.cargas.dto.SaidaStepsDeliveryDto;
import com.gestao.cargas.entity.Delivery;
import com.gestao.cargas.entity.Movimentos;
import com.gestao.cargas.entity.Package;
import com.gestao.cargas.repository.DeliveryRepository;
import com.gestao.cargas.repository.PackagesRepository;

@Service
public class DeliveryService {
	
	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Autowired
	private PackagesRepository packagesRepository;

	@Transactional
	public Delivery cadastrar(@Valid DeliveryDto delivery) throws Exception {
		verificaDeliveryRepetida(delivery);
		
		Delivery deliveryCad = deliveryRepository.save(new Delivery(delivery.getDeliveryId(), delivery.getVehicle()));
		cadastraVinculaPackageToDelivery(delivery, deliveryCad);
		return deliveryCad;
	}

	@Transactional
	private void verificaDeliveryRepetida(DeliveryDto delivery) throws Exception {
		Delivery deliveryBanco = deliveryRepository.findByDeliveryId(delivery.getDeliveryId());
		if(deliveryBanco != null){
			throw new Exception("Já existe uma delivery com esse Id.");
		}
	}

	private void cadastraVinculaPackageToDelivery(DeliveryDto delivery, Delivery deliveryCad) {
		delivery.getPackages().stream().forEach(p -> packagesRepository.save(new Package(p.getId(), p.getWeight(), deliveryCad)));
	}
	
	public List<Package> buscaDeliveryComPackages(Long deliveryId) {
		return packagesRepository.findByDelivery(deliveryRepository.findById(deliveryId).get());
	}
	
	public List<SaidaStepsDeliveryDto> montaStepsDelivery(List<Package> packagesNaDelivery){
		List<SaidaStepsDeliveryDto> lista = new ArrayList<>();
		
		Package[] pilhaEntradaApi = new Package[packagesNaDelivery.size()];
		  
        preenchePilhaEntrada(packagesNaDelivery, pilhaEntradaApi);
        
        Package[] pilhaOrdenada = pilhaEntradaApi;
        
        ordena(packagesNaDelivery, pilhaOrdenada);
        
        criaSteps(lista, pilhaEntradaApi, pilhaOrdenada);
		
		return lista;
	}

	private void criaSteps(List<SaidaStepsDeliveryDto> lista, Package[] pilhaEntradaApi, Package[] pilhaOrdenada) {
		/*
         *   Pelo enunciado a pilha pode ter no máximo 3 volumes
         *	 A pilha de abastecimento sempre estará ordenada, afinal tudo parte da zona de abastecimento que deve ter uma pilha
         * 	 e nunca um pacote mais pesado pode estar por cima de um menor.
         */
        if(pilhaEntradaApi.length == 1) {
        	criaStep(pilhaEntradaApi[0].getId(), lista, Movimentos.ZONA_DE_ABASTECIMENTO,Movimentos.ZONA_DO_CAMINHAO, 1L);
        }else if(pilhaEntradaApi.length == 2) {
    		criaStep(pilhaEntradaApi[0].getId(), lista, Movimentos.ZONA_DE_ABASTECIMENTO,Movimentos.ZONA_DE_TRANSFERENCIA, 1L);
    		criaStep(pilhaEntradaApi[1].getId(), lista, Movimentos.ZONA_DE_ABASTECIMENTO,Movimentos.ZONA_DO_CAMINHAO, 2L);
    		criaStep(pilhaEntradaApi[0].getId(), lista, Movimentos.ZONA_DE_TRANSFERENCIA,Movimentos.ZONA_DO_CAMINHAO, 3L);
        }else if(pilhaEntradaApi.length == 3) {
    		criaStep(pilhaOrdenada[0].getId(), lista, Movimentos.ZONA_DE_ABASTECIMENTO,Movimentos.ZONA_DO_CAMINHAO, 1L);
    		criaStep(pilhaOrdenada[1].getId(), lista, Movimentos.ZONA_DE_ABASTECIMENTO,Movimentos.ZONA_DE_TRANSFERENCIA, 2L);
    		criaStep(pilhaOrdenada[0].getId(), lista, Movimentos.ZONA_DO_CAMINHAO,Movimentos.ZONA_DE_TRANSFERENCIA, 3L);
    		criaStep(pilhaOrdenada[2].getId(), lista, Movimentos.ZONA_DE_ABASTECIMENTO,Movimentos.ZONA_DO_CAMINHAO, 4L);
    		criaStep(pilhaOrdenada[0].getId(), lista, Movimentos.ZONA_DE_TRANSFERENCIA,Movimentos.ZONA_DE_ABASTECIMENTO, 5L);
    		criaStep(pilhaOrdenada[1].getId(), lista, Movimentos.ZONA_DE_TRANSFERENCIA,Movimentos.ZONA_DO_CAMINHAO, 6L);
    		criaStep(pilhaOrdenada[0].getId(), lista, Movimentos.ZONA_DE_ABASTECIMENTO,Movimentos.ZONA_DO_CAMINHAO, 7L);
        }
	}

	private void ordena(List<Package> packagesNaDelivery, Package[] pilhaOrdenada) {
		quickSort(pilhaOrdenada, 0, packagesNaDelivery.size()-1);
        
        for (int i = 0; i < pilhaOrdenada.length; i++) {
        	System.out.println("Saída: \n" + "Id - " + pilhaOrdenada[i].getId() + "\n Weight - " + pilhaOrdenada[i].getWeight());
		}
	}

	private void preenchePilhaEntrada(List<Package> packagesNaDelivery, Package[] pilhaEntradaApi) {
		for (int i = 0; i < packagesNaDelivery.size(); i++) {
        	pilhaEntradaApi[i] = packagesNaDelivery.get(i);
        }
        
        for (int i = 0; i < pilhaEntradaApi.length; i++) {
        	System.out.println("Entrada: \n" + "Id - " + pilhaEntradaApi[i].getId() + "\n Weight - " + pilhaEntradaApi[i].getWeight());
		}
	}
	
	private static void quickSort(Package[] vetor, int inicio, int fim) {
        if (inicio < fim) {
           int posicaoPivo = separar(vetor, inicio, fim);
           quickSort(vetor, inicio, posicaoPivo - 1);
           quickSort(vetor, posicaoPivo + 1, fim);
        }
	}
	
	private static int separar(Package[] vetor, int inicio, int fim) {
        Package pivo = vetor[inicio];
        int i = inicio + 1, f = fim;
        while (i <= f) {
           if (vetor[i].getWeight() <= pivo.getWeight())
              i++;
           else if (pivo.getWeight() < vetor[f].getWeight())
              f--;
           else {
              Package troca = vetor[i];
              vetor[i] = vetor[f];
              vetor[f] = troca;
              i++;
              f--;
           }
        }
        vetor[inicio] = vetor[f];
        vetor[f] = pivo;
        return f;
	}

	private void criaStep(Long ordem, List<SaidaStepsDeliveryDto> lista, Movimentos from, Movimentos to, Long step) {
		SaidaStepsDeliveryDto saidaSteps = new SaidaStepsDeliveryDto();
		saidaSteps.setPackageId(ordem);
		saidaSteps.setStep(step);
		saidaSteps.setFrom(from);
		saidaSteps.setTo(to);
		lista.add(saidaSteps);
	}
	
}
