package br.com;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.dao.DaoGenerico;
import br.com.entidades.Endereco;
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;
import br.com.repositorio.IDaoEndereco;
import br.com.repositorio.IDaoEnderecoImpl;

@ManagedBean(name = "EnderecoBean")
@ViewScoped
public class EnderecoBean {

	private Endereco endereco = new Endereco();

	private DaoGenerico<Endereco> daoGenerico = new DaoGenerico<Endereco>();

	private List<Endereco> enderecos = new ArrayList<Endereco>();

	private IDaoEndereco idaoEndereco = new IDaoEnderecoImpl();

	//////////////////////// metodos //////////////////////
	

	

	//////////////////////// getters e setters /////////////////

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public DaoGenerico<Endereco> getDaoGenerico() {
		return daoGenerico;
	}

	public void setDaoGenerico(DaoGenerico<Endereco> daoGenerico) {
		this.daoGenerico = daoGenerico;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

}
