package it.polito.tdp.metrodeparis.model;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class CoppiaFermate {

	private FermataConLinea f1;
	private FermataConLinea f2;
	private Double velocita;

	public CoppiaFermate(FermataConLinea f1, FermataConLinea f2, Double velocita){
		this.f1=f1;
		this.f2=f2;
		this.velocita= velocita;
	}

	public FermataConLinea getF1() {
		return f1;
	}

	public void setF1(FermataConLinea f1) {
		this.f1 = f1;
	}

	public FermataConLinea getF2() {
		return f2;
	}

	public void setF2(FermataConLinea f2) {
		this.f2 = f2;
	}

	public Double getVelocita() {
		return velocita;
	}

	public void setVelocita(Double tempo) {
		this.velocita = tempo;
	}
	
	public Double getTempo(){
		Double distanza=LatLngTool.distance(f1.getCoords(), f2.getCoords(), LengthUnit.KILOMETER);
		Double tempo=distanza/velocita*3600+30;
		return tempo;
	}
}
