package com.gestao.cargas.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gestao.cargas.dsl.Dsl;
import com.gestao.cargas.dto.SaidaStepsDeliveryDto;
import com.gestao.cargas.entity.Delivery;
import com.gestao.cargas.entity.Movimentos;
import com.gestao.cargas.entity.Package;
import com.gestao.cargas.repository.DeliveryRepository;

@WebAppConfiguration
@SpringBootTest
public class DeliveryServiceTest extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	private Dsl dsl;
	
	@Autowired
	private DeliveryRepository deliveryRepository;
	
	@Test
	public void cadastrarDelivery() throws Exception {
		Delivery delivery = dsl.dadoUmaDeliveryComPackagesCadastrado(1L, 1L);
		
		Assert.assertEquals(delivery.getDeliveryId().longValue(), 1L);
		Assert.assertEquals(delivery.getVehicle().longValue(), 1L);
	}
	
	@Test
	public void cadastrarDeliveryComPackages() throws Exception {
		Delivery delivery = dsl.dadoUmaDeliveryComPackagesCadastrado(2L, 1L);
		List<Package> packages = dsl.buscaPackagesByDelivery(delivery);
		
		Assert.assertEquals(packages.size(), 1L);
		Assert.assertTrue(Double.compare(packages.get(0).getWeight().doubleValue(), 15.5) == 0);
	}
	
	@Test
	public void cadastrarDeliverys() throws Exception {
		Delivery delivery = dsl.dadoUmaDeliveryComPackagesCadastrado(2L, 1L);
		PageRequest p = PageRequest.of(0, 1);
		Page<Delivery> packages = deliveryRepository.findDeliverysPage(delivery.getDeliveryId(), delivery.getVehicle(), p);
		
		Assert.assertEquals(packages.getContent().get(0).getDeliveryId(), delivery.getDeliveryId());
		Assert.assertEquals(packages.getContent().get(0).getVehicle(), delivery.getVehicle());
		//Assert.assertTrue(Double.compare(packages.get(0).getWeight().doubleValue(), 15.5) == 0);
	}
	
	@Test
	public void deliveryComPackagesComUmPasso() throws Exception {
		Delivery delivery = dsl.dadoUmaDeliveryComPackagesCadastrado(3L, 1L);
		List<Package> packages = dsl.buscaPackagesByDelivery(delivery);
		List<SaidaStepsDeliveryDto> steps = dsl.montaStepsDelivery(packages);
		Assert.assertEquals(steps.size(), 1L);
		Assert.assertEquals(steps.get(0).getFrom(), Movimentos.ZONA_DE_ABASTECIMENTO);
		Assert.assertEquals(steps.get(0).getTo(), Movimentos.ZONA_DO_CAMINHAO);
	}
	
	@Test
	public void deliveryComPackagesComTresPassos() throws Exception {
		Delivery delivery = dsl.dadoUmaDeliveryComPackagesCadastrado(4L, 2L);
		List<Package> packages = dsl.buscaPackagesByDelivery(delivery);
		List<SaidaStepsDeliveryDto> steps = dsl.montaStepsDelivery(packages);
		Assert.assertEquals(steps.size(), 3L);
		Assert.assertEquals(steps.get(0).getFrom(), Movimentos.ZONA_DE_ABASTECIMENTO);
		Assert.assertEquals(steps.get(0).getTo(), Movimentos.ZONA_DE_TRANSFERENCIA);
		Assert.assertEquals(steps.get(1).getFrom(), Movimentos.ZONA_DE_ABASTECIMENTO);
		Assert.assertEquals(steps.get(1).getTo(), Movimentos.ZONA_DO_CAMINHAO);
		Assert.assertEquals(steps.get(2).getFrom(), Movimentos.ZONA_DE_TRANSFERENCIA);
		Assert.assertEquals(steps.get(2).getTo(), Movimentos.ZONA_DO_CAMINHAO);
	}
	
	@Test
	public void deliveryComPackagesComSetePassos() throws Exception {
		Delivery delivery = dsl.dadoUmaDeliveryComPackagesCadastrado(5L, 3L);
		List<Package> packages = dsl.buscaPackagesByDelivery(delivery);
		List<SaidaStepsDeliveryDto> steps = dsl.montaStepsDelivery(packages);
		Assert.assertEquals(steps.size(), 7L);
		Assert.assertEquals(steps.get(0).getFrom(), Movimentos.ZONA_DE_ABASTECIMENTO);
		Assert.assertEquals(steps.get(0).getTo(), Movimentos.ZONA_DO_CAMINHAO);
		Assert.assertEquals(steps.get(1).getFrom(), Movimentos.ZONA_DE_ABASTECIMENTO);
		Assert.assertEquals(steps.get(1).getTo(), Movimentos.ZONA_DE_TRANSFERENCIA);
		Assert.assertEquals(steps.get(2).getFrom(), Movimentos.ZONA_DO_CAMINHAO);
		Assert.assertEquals(steps.get(2).getTo(), Movimentos.ZONA_DE_TRANSFERENCIA);
		Assert.assertEquals(steps.get(3).getFrom(), Movimentos.ZONA_DE_ABASTECIMENTO);
		Assert.assertEquals(steps.get(3).getTo(), Movimentos.ZONA_DO_CAMINHAO);
		Assert.assertEquals(steps.get(4).getFrom(), Movimentos.ZONA_DE_TRANSFERENCIA);
		Assert.assertEquals(steps.get(4).getTo(), Movimentos.ZONA_DE_ABASTECIMENTO);
		Assert.assertEquals(steps.get(5).getFrom(), Movimentos.ZONA_DE_TRANSFERENCIA);
		Assert.assertEquals(steps.get(5).getTo(), Movimentos.ZONA_DO_CAMINHAO);
		Assert.assertEquals(steps.get(6).getFrom(), Movimentos.ZONA_DE_ABASTECIMENTO);
		Assert.assertEquals(steps.get(6).getTo(), Movimentos.ZONA_DO_CAMINHAO);
	}
	
}
