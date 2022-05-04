package br.com.repositorio;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Endereco;
import br.com.entidades.Pessoa;
import br.com.jpautil.JPAUtil;

public class IDaoEnderecoImpl implements IDaoEndereco{

	@Override
	public Endereco consultar(Long codigo) {
		
		
		Endereco endereco = null;
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		endereco = (Endereco) entityManager.createQuery("SELECT * FROM endereco  where id = " + codigo).getSingleResult();
		
		transaction.commit();
		entityManager.close();
		
		return endereco;
	}

}
