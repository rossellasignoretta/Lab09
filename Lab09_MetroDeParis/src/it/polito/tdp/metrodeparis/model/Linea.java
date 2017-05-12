package it.polito.tdp.metrodeparis.model;

public class Linea {
	
	private int idlinea;
	private String nome;
	private double intervallo;
	
	public Linea(int idlinea, String nome, double intervallo) {
		super();
		this.idlinea = idlinea;
		this.nome = nome;
		this.intervallo = intervallo;
	}

	public int getIdlinea() {
		return idlinea;
	}

	public void setIdlinea(int idlinea) {
		this.idlinea = idlinea;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getIntervallo() {
		return intervallo;
	}
	
	public void setIntervallo(double intervallo) {
		this.intervallo = intervallo;
	}
	
	
	

}
