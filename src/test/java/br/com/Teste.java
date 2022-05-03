package br.com;

import javax.persistence.Persistence;

public class Teste {
	
	public static void main(String[] args) {
		
		Persistence.createEntityManagerFactory("cursojsf");
		
	}

}
