package br.com;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import br.com.dao.DaoGenerico;
import br.com.entidades.Endereco;
import br.com.entidades.Pessoa;
import br.com.repositorio.IDaoEndereco;
import br.com.repositorio.IDaoEnderecoImpl;
import br.com.repositorio.IDaoPessoa;
import br.com.repositorio.IDaoPessoaImpl;

@ManagedBean(name = "pessoaBean")
@ViewScoped
public class PessoaBean {

	private Pessoa pessoa = new Pessoa();

	private DaoGenerico<Pessoa> daoGenerico = new DaoGenerico<Pessoa>();

	

	private List<Pessoa> pessoas = new ArrayList<Pessoa>();

	private IDaoPessoa iDaoPessoa = new IDaoPessoaImpl();
	
	private List<SelectItem> estados;

	////////////////////// metodos ////////////////////////

	public String salvar() {

		pessoa = daoGenerico.merge(pessoa);
		carregarPessoas();
		mostrarMsg("Cadastrado com Sucesso");
		return "";
	}

	private void mostrarMsg(String msg) {

		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		context.addMessage(null, message);
	}

	public String novo() {
		pessoa = new Pessoa();
		mostrarMsg("Criando um nova pessoa");
		return "";
	}

	public String limpar() {
		pessoa = new Pessoa();
		mostrarMsg("Formulario limpo");
		return "";
	}

	public String remove() {
		daoGenerico.deletePorId(pessoa);
		pessoa = new Pessoa();
		carregarPessoas();
		mostrarMsg("Removido com Sucesso");
		return "";
	}

	
	/*
	public void pegarEndereco() {
		iDaoEndereco.consultar(endereco.getId());
	}

	*/
	
	public void pesquisaCep(AjaxBehaviorEvent event) {

		try {
			URL url = new URL("https://viacep.com.br/ws/" + pessoa.getCep() + "/json");

			URLConnection connection = url.openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			String cep = "";
			StringBuilder jsonCep = new StringBuilder();

			while ((cep = bf.readLine()) != null) {

				jsonCep.append(cep);
			}

			Endereco gsonAux = new Gson().fromJson(jsonCep.toString(), Endereco.class);

			System.out.println(gsonAux);

		} catch (Exception e) {
			e.printStackTrace();
			mostrarMsg("erro ao consultar o cep");
		}

	}

	@PostConstruct
	public void carregarPessoas() {
		pessoas = daoGenerico.getListEntity(Pessoa.class);
	}

	public String logar() {

		Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());

		if (pessoaUser != null) {// achou o usuario
			// adicionar o usuario na sessao usuarioLogado
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();

			HttpServletRequest req = (HttpServletRequest) externalContext.getRequest();
			HttpSession session = req.getSession();

			session.setAttribute("usuariologado", pessoaUser);

			return "primeirapagina.jsf";
		}

		return "index.jsf";
	}

	public boolean permiteAcesso(String acesso) {

		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();

		HttpServletRequest req = (HttpServletRequest) externalContext.getRequest();
		HttpSession session = req.getSession();

		Pessoa pessoaUser = (Pessoa) session.getAttribute("usuariologado");

		return pessoaUser.getCargo().equals(acesso);

	}
	
	

	//////////////////////////// getters e
	//////////////////////////// setters/////////////////////////////////////

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DaoGenerico<Pessoa> getDaoGenerico() {
		return daoGenerico;
	}

	public void setDaoGenerico(DaoGenerico<Pessoa> daoGenerico) {
		this.daoGenerico = daoGenerico;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}


	public List<SelectItem> getEstados() {
		estados = iDaoPessoa.listaEstados();
		return estados;
	}
	
	public void setEstados(List<SelectItem> estados) {
		this.estados = estados;
	}

	

	public void carregaCidades(AjaxBehaviorEvent event) {
		
		String codigoEstado = (String) event.getComponent().getAttributes().get("submittedValue");
		
		if(codigoEstado != null) {
			System.out.println(codigoEstado);
		}
		
	}
}
