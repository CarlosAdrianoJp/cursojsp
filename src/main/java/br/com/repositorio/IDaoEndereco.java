package br.com.repositorio;

import br.com.entidades.Endereco;
import br.com.entidades.Pessoa;

public interface IDaoEndereco {

	Endereco consultarCep(String cep);
}
