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
import br.com.entidades.Lancamento;
import br.com.entidades.Pessoa;
import br.com.repositorio.IDaoLancamento;
import br.com.repositorio.IDaoLancamentoImpl;

@ManagedBean(name = "LancamentoBean")
@ViewScoped
public class LancamentoBean {
	
	
	private Lancamento lancamento = new Lancamento();
	
	private DaoGenerico<Lancamento> daoGenerico = new DaoGenerico<Lancamento>();
	
	private List<Lancamento> lancamentos = new ArrayList<Lancamento>();
	
	private IDaoLancamento daoLancamento = new IDaoLancamentoImpl();


	////////////////////////////// metodos ///////////////////////////
	public String salvar() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();

		HttpServletRequest req = (HttpServletRequest) externalContext.getRequest();
		HttpSession session = req.getSession();
		
		Pessoa pessoaUser = (Pessoa) session.getAttribute("usuariologado");
		
		lancamento.setUsuario(pessoaUser);
		
		
		
		lancamento =  daoGenerico.merge(lancamento);
		
		carregarLancamentos();
		return "";
	}
	
	public String novo() {
	lancamento = new Lancamento();
		return "";
	}
	
	
	public String remove() {
		daoGenerico.deletePorId(lancamento);
		lancamento = new Lancamento();
		carregarLancamentos();
		return "";
	}

	@PostConstruct
	public void carregarLancamentos() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();

		HttpServletRequest req = (HttpServletRequest) externalContext.getRequest();
		HttpSession session = req.getSession();
		
		Pessoa pessoaUser = (Pessoa) session.getAttribute("usuariologado");
		
		lancamentos = daoLancamento.consultar(pessoaUser.getId());
		
		
	}
	
	
	///////////////////////////// getters e setters/////////////////////
	
	
	public Lancamento getLancamento() {
		return lancamento;
	}

	public void setLancamento(Lancamento lancamento) {
		this.lancamento = lancamento;
	}

	public DaoGenerico<Lancamento> getDaoGenerico() {
		return daoGenerico;
	}

	public void setDaoGenerico(DaoGenerico<Lancamento> daoGenerico) {
		this.daoGenerico = daoGenerico;
	}

	public List<Lancamento> getLancamentos() {
		return lancamentos;
	}

	public void setLancamentos(List<Lancamento> lancamentos) {
		this.lancamentos = lancamentos;
	}
	
}
