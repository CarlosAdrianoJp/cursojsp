package br.com.repositorio;

import java.util.List;

import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;

public interface IDaoLancamento {

List<Lancamento> consultar(Long codUsuario);
}
