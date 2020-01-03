package com.gestao.cargas.repository;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Repository;

@Repository
public class CustomRepositoryImpl<T> implements CustomRepository<T>{
	
	@Autowired
    private LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;
	
	@Override
	public List<T> findDeliverys(Long deliveryId, Long vehicle) {
		return new QueryBuilder<T>("from Delivery t", getSession())
				.adicionaCondicaoSeNaoForNulo(deliveryId, "t.deliveryId = :deliveryId", "deliveryId")
				.adicionaCondicao(vehicle, "t.vehicle = :vehicle", "vehicle")
				.adicionaOrderBy("deliveryId")
				.adicionaTipoOrderByDESC()
				.build()
				  .list();
	}
	
	@Override
	public Page<T> findDeliverysPage(Long deliveryId, Long vehicle, Pageable page) {
		return new QueryBuilder<T>("from Delivery t", getSession())
				.adicionaCondicaoSeNaoForNulo(deliveryId, "t.deliveryId = :deliveryId", "deliveryId")
				.adicionaCondicao(vehicle, "t.vehicle = :vehicle", "vehicle")
				.adicionaOrderBy("deliveryId")
				.adicionaTipoOrderByDESC()
				.buildPageable(page);
	}

	private Session getSession() {
		Session session = localContainerEntityManagerFactoryBean.getObject().createEntityManager().unwrap(Session.class);
		return session;
	}

}
