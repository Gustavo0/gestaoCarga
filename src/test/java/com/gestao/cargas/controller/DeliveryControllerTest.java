package com.gestao.cargas.controller;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gestao.cargas.config.AbstractTestJUnit;
import com.gestao.cargas.dsl.Dsl;
import com.gestao.cargas.dto.DeliverySaidaDto;
import com.gestao.cargas.entity.Delivery;
import com.jayway.restassured.http.ContentType;

@WebAppConfiguration
@SpringBootTest
public class DeliveryControllerTest extends AbstractTestJUnit{
	
	@Autowired
	private Dsl dsl;
	
	@Test
	public void cadastroCreatedTest() {
		Delivery delivery = dsl.dadoUmaDeliveryComPackagesCadastrado();
		
		DeliverySaidaDto deliverySaida = given()
											.contentType(ContentType.JSON)
											.body(dsl.dadoUmaRequestBody(true))
											.when()
												.post("/delivery")
											.then()
												.log().all()
												.assertThat()
													.statusCode(HttpStatus.CREATED.value())
													.extract().as(DeliverySaidaDto.class);
		
		Assert.assertEquals(deliverySaida.getDeliveryId(), delivery.getDeliveryId());
	}
	
}
