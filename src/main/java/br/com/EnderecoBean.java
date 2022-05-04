package br.com;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

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
	
public String salvar() {
		
		endereco  =  daoGenerico.merge(endereco);
		carregarEndereco();
	
		return "";
	}

public String novo() {
	endereco = new Endereco();
	return "";
}

public String remove() {
	daoGenerico.deletePorId(endereco);
	endereco = new Endereco();
	carregarEndereco();
	return "";
}


@PostConstruct
public void carregarEndereco() {
	
	
	
	enderecos = daoGenerico.getListEntity(Endereco.class);
	
	
	
}
	

public void consultarCep(AjaxBehaviorEvent event) {

	try {
		URL url = new URL("https://viacep.com.br/ws/"+ endereco.getCep() +"/json");
		
		URLConnection connection = url.openConnection();
		InputStream is = connection.getInputStream();
		BufferedReader bf = new BufferedReader( new InputStreamReader(is, "UTF-8"));
		
		String cep = "";
		StringBuilder jsonCep = new StringBuilder();
		
		while ((cep = bf.readLine()) != null) {
		
			jsonCep.append(cep);
		}
		
		Endereco gsonAux = new Gson().fromJson(jsonCep.toString(), Endereco.class);
		
		endereco.setCep(gsonAux.getCep());
		endereco.setLogradouro(gsonAux.getLogradouro());
		endereco.setComplemento(gsonAux.getComplemento());
		endereco.setBairro(gsonAux.getBairro());
		endereco.setLocalidade(gsonAux.getLocalidade());
		endereco.setUf(gsonAux.getUf());
		endereco.setIbge(gsonAux.getIbge());
		endereco.setDdd(gsonAux.getDdd());
		
		
		System.out.println(gsonAux);
		System.out.println(endereco);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	}

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
