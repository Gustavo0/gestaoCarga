package com.gestao.cargas.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gestao.cargas.dsl.Dsl;
import com.gestao.cargas.entity.Delivery;
import com.gestao.cargas.entity.Package;

@WebAppConfiguration
@SpringBootTest
public class DeliveryServiceTest extends AbstractJUnit4SpringContextTests{
	
	@Autowired
	private Dsl dsl;
	
	@Test
	public void cadastrarDelivery() {
		Delivery delivery = dsl.dadoUmaDeliveryComPackagesCadastrado();
		
		Assert.assertEquals(delivery.getDeliveryId().longValue(), 1L);
		Assert.assertEquals(delivery.getVehicle().longValue(), 1L);
	}
	
	@Test
	public void cadastrarDeliveryComPackages() {
		Delivery delivery = dsl.dadoUmaDeliveryComPackagesCadastrado();
		List<Package> packages = dsl.buscaPackagesByDelivery(delivery);
		
		Assert.assertEquals(packages.size(), 1L);
		Assert.assertTrue(Double.compare(packages.get(0).getWeight().doubleValue(), 14.5) == 0);
	}

}
