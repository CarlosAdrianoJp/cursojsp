package br.com.repositorio;

import br.com.entidades.Pessoa;

public interface IDaoPessoa {

	Pessoa consultarUsuario(String login, String senha);
}
