package it.polito.tdp.metrodeparis.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	MetroDAO dao;
	List<Fermata> tutteLeFermate;
	WeightedGraph <FermataConLinea, DefaultWeightedEdge> graph;
	GraphPath<FermataConLinea, DefaultWeightedEdge> path; 
	Map<Integer, Linea> linee;
	
	public List<Fermata> getFermate(){
		if(tutteLeFermate==null){
			dao= new MetroDAO();
			tutteLeFermate=dao.getAllFermate();
		}
		return tutteLeFermate;
	}
	
	public WeightedGraph <FermataConLinea, DefaultWeightedEdge> getGrafo(){
		if (graph==null){
			creaGrafo();
		}
		return graph;
	}

	public Map<Integer, Linea> getLinee(){
		if (linee==null){
			dao=new MetroDAO();
			linee=dao.getLinee();
		}
		return linee;
	}
	
	private void creaGrafo() {
		
		this.graph= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		dao= new MetroDAO();
		
		Graphs.addAllVertices(graph, dao.getFermateconLinea()) ;
		
		//archi tra fermate diverse
		List<CoppiaFermate> coppie= dao.getCoppieFermate();
		for (CoppiaFermate c: coppie){
			DefaultWeightedEdge de= graph.addEdge(c.getF1(), c.getF2());
			if (de!=null){
				graph.setEdgeWeight(de, c.getTempo());
			}
		}
		
		//archi nella stessa fermata
		Map<Integer, Linea> lineeMetro=getLinee();
		Set <FermataConLinea> vertici= graph.vertexSet();
		for(FermataConLinea f1: vertici){
			for(FermataConLinea f2: vertici){
				if (f1.getIdFermata()==f2.getIdFermata() && f1.getIdLinea()!=f2.getIdLinea()){
					//se le fermate hanno lo stesso id ma linea diversa aggiungo un arco
					DefaultWeightedEdge de= graph.addEdge(f1,f2);
					if (de!=null){
						graph.setEdgeWeight(de, lineeMetro.get(f2.getIdLinea()).getIntervallo());
					}
				}
			}
		}
		
	}

	public String stampaPercorso(Fermata partenza, Fermata arrivo){
		List <FermataConLinea> fermate=getPercorso(partenza, arrivo);
		
		String risultato="Percorso: \n";
		
		if (fermate.size()==0){
			return risultato+="Nessuna fermata intermedia";
		}
		
		int idlinea=fermate.get(0).getIdLinea();
		String nomeLinea=linee.get(idlinea).getNome();
		risultato+="\nPrendo Linea: "+nomeLinea+"\n";
		
		List<Fermata> temp= new ArrayList<Fermata>();
		for(FermataConLinea f: fermate){
			if(f.getIdLinea()==idlinea){
				temp.add(f);
			}else{
				risultato+=temp.toString()+"\n";
				temp.clear();
				temp.add(f);
				idlinea=f.getIdLinea();
				nomeLinea=linee.get(idlinea).getNome();
				risultato+="\nCambio su Linea: "+nomeLinea+"\n";
			}
		
		}
		return risultato;
	}
	
	private List<FermataConLinea> getPercorso(Fermata partenza, Fermata arrivo) {
		//trovo tutte le fermate di partenza e arrivo con lo stesso id
		List<FermataConLinea> fermatePartenza=new ArrayList<FermataConLinea>();
		List<FermataConLinea> fermateArrivo=new ArrayList<FermataConLinea>();
		
		for(FermataConLinea f: graph.vertexSet()){
			if(f.getIdFermata()==partenza.getIdFermata())
				fermatePartenza.add(f);
			if(f.getIdFermata()==arrivo.getIdFermata())
				fermateArrivo.add(f);
		}
		
		//calcolo i cammini minimi tra tutte le fermate che ho trovato
		//e scelgo quello con peso minore
		double peso=Double.MAX_VALUE;
		for(FermataConLinea fa: fermateArrivo){
			for (FermataConLinea fp: fermatePartenza){
				DijkstraShortestPath <FermataConLinea,DefaultWeightedEdge> dsp= new DijkstraShortestPath<FermataConLinea,DefaultWeightedEdge>(graph, fp, fa);
				if(dsp.getPath().getWeight()<peso){
					path = dsp.getPath();
					peso=path.getWeight();
				}
			}
		}
		
		//restituisco l'elenco di fermate intermedie
		List <FermataConLinea> listaFermate= new ArrayList<FermataConLinea>();
		for (DefaultWeightedEdge dwe: path.getEdgeList()){
			listaFermate.add(graph.getEdgeTarget(dwe));
		}		
		return listaFermate;
	}

	public String getTempo(){
		double totale=path.getWeight()-30;
		/*int ore=(int) (totale/3600);
		int minuti= (int) ((totale-(ore*3600))/60);
		int secondi= (int) ((totale-(ore*3600)-(minuti*60)));
		return ore+":"+minuti+":"+secondi; */
		LocalTime timeOfDay = LocalTime.ofSecondOfDay((long) totale);
		return timeOfDay.toString();
		
	}
	
	
	

}