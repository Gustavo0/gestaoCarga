package com.gestao.cargas.config;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.web.context.WebApplicationContext;

import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;

public abstract class AbstractTestJUnit extends AbstractJUnit4SpringContextTests{
	
	@Autowired
    private WebApplicationContext wac;
 
    @Before
    public void globalSetup() {
    	RestAssuredMockMvc.webAppContextSetup(wac);
    }

}
