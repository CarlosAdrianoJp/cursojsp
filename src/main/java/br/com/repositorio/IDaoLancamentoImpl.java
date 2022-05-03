package br.com.repositorio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;

public class IDaoLancamentoImpl implements IDaoLancamento{

public List<Lancamento> consultar(Long codUsuario){
	
	List<Lancamento> lista = null;
	
	EntityManager entityManager = JPAUtil.getEntityManager();
	EntityTransaction transaction = entityManager.getTransaction();
	transaction.begin();
	
	lista = entityManager.createQuery("from Lancamento where usuario.id = " + codUsuario).getResultList();
	
	transaction.commit();
	entityManager.close();
	
	return lista;
}

	

}
