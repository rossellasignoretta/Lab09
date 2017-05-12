package it.polito.tdp.metrodeparis.model;

import java.util.List;
import java.util.Set;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		List <Fermata> fermate= model.getFermate();
		
		int contatore=0;
		for(Fermata temp: fermate){
			System.out.println(temp.getIdFermata()+ " "+ temp.getNome());
			contatore++;
		}
		System.out.println("Totale fermate: "+contatore);
		
		
		Set <FermataConLinea> ferm= model.getGrafo().vertexSet();
		
		int cont=0;
		for(Fermata temp: ferm){
			System.out.println(temp.getIdFermata()+ " "+ temp.getNome());
			cont++;
		}
		System.out.println("Totale vertici: "+cont);
		
	}

}
