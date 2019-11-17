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
	public void cadastroCreatedTest() throws Exception {
		Delivery delivery = dsl.dadoUmaDeliveryComPackagesCadastrado(4L, 1L);
		
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
		
		Assert.assertFalse(deliverySaida.getDeliveryId().equals(delivery.getDeliveryId()));
	}
	
	@Test
	public void cadastroBadRequestTest() throws Exception {
		dsl.dadoUmaDeliveryComPackagesCadastrado(1L, 1L);
		
		given()
			.contentType(ContentType.JSON)
			.body(dsl.dadoUmaRequestBody(false))
			.when()
				.post("/delivery")
			.then()
				.log().all()
				.assertThat()
					.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void consultaInstrucoesTest() throws Exception {
		dsl.dadoUmaDeliveryComPackagesCadastrado(5L, 1L);
		
		given()
		.when()
			.get("/delivery/{deliveryId}/step", 5)
		.then()
			.log().all()
			.assertThat()
				.statusCode(HttpStatus.OK.value());
	}
	
}
