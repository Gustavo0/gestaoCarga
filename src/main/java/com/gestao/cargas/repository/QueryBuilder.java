package com.gestao.cargas.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class QueryBuilder<T> {
	
	private final Session session;
	private final List<Parametro> parametros = new ArrayList<>();
	private StringBuilder consulta;
	
	private static class Parametro {
		Object filtro;
		String placeHolderFiltro;

		public Parametro(Object filtro, String placeHolderFiltro) {
			this.filtro = filtro;		
			this.placeHolderFiltro = placeHolderFiltro;
		}
	}
	
	public QueryBuilder (String from, Session session) {
		consulta = new StringBuilder(from);
		consulta.append(" WHERE 1=1 ");
		this.session = session;
	}
	
	public QueryBuilder<T> adicionaCondicaoSeNaoForNulo(Object filtro, String condicao, String placeHolderFiltro) {
		if (isFiltroNaoNulo(filtro)) {
			adicionaCondicao(filtro, condicao, placeHolderFiltro);
		}
		
		return this;
	}
	
	public QueryBuilder<T> adicionaCondicao(Object filtro, String condicao, String placeHolderFiltro) {
		consulta.append(" AND ").append(condicao);
		incluiParametroNaListaParametros(filtro, placeHolderFiltro);
		
		return this;
	}

	/*
	 * ORDER BY METHODS
	 */
	
	public QueryBuilder<T> adicionaOrderBy(String campo) {
	    consulta.append(" ORDER BY ").append(campo);
	    return this;
	}
	
	public QueryBuilder<T> adicionaCampoAoOrderBy(String campo) {
	    consulta.append(", ").append(campo);
	    return this;
	}
	
	public QueryBuilder<T> adicionaTipoOrderByDESC() {
        consulta.append(" DESC ");
        return this;
	}
	
	private void incluiParametroNaListaParametros(Object filtro, String placeHolderFiltro) {
		parametros.add(new Parametro(filtro, placeHolderFiltro));
	}
	
	private boolean isFiltroNaoNulo(Object filtro) {
		return Optional.ofNullable(filtro).isPresent();
	}
	
	@SuppressWarnings("unchecked")
	private Query<T> criaQuery() {
		Query<T> query = session.createQuery(consulta.toString());
		parametros.forEach(p -> {
			if (p.filtro instanceof Collection) {
				query.setParameterList(p.placeHolderFiltro, (Collection<?>) p.filtro);
			} else {
				query.setParameter(p.placeHolderFiltro, p.filtro);
			}
		});
		return query;
	}
	
	public Query<T> build () {
		return criaQuery();
	}
	
	public Page<T> buildPageable (Pageable page ) {
		List<T> lista = criaQuery().setFirstResult(page.getPageNumber() * page.getPageSize())
								   .setMaxResults(page.getPageSize())
								   .list();
		return new PageImpl<>(lista, page, lista.size());
	}

}
