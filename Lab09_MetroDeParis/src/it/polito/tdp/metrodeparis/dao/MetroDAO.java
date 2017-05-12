package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.model.CoppiaFermate;
import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.FermataConLinea;
import it.polito.tdp.metrodeparis.model.Linea;

public class MetroDAO {
	
	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}

	public List<FermataConLinea> getFermateconLinea() {

		final String sql = "SELECT DISTINCT id_fermata, nome, coordx, coordy, id_linea FROM connessione c, fermata f WHERE c.id_stazP=f.id_fermata ORDER BY nome ASC";
		List<FermataConLinea> fermate = new ArrayList<FermataConLinea>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				FermataConLinea f = new FermataConLinea(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")), rs.getInt("id_linea"));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}
	
	public List<CoppiaFermate> getCoppieFermate() {

		final String sql = " SELECT f1.id_fermata AS id1, f1.nome as nome1, f1.coordX as x1, f1.coordY as y1, "+
				" f2.id_fermata AS id2, f2.nome as nome2, f2.coordX as x2, f2.coordY as y2, c.id_linea, velocita " +
				" FROM connessione c, fermata f1, fermata f2, linea l " +
				" WHERE c.id_stazP=f1.id_fermata AND c.id_stazA=f2.id_fermata AND c.id_linea=l.id_linea";		
		
		List<CoppiaFermate> coppie = new ArrayList<CoppiaFermate>();

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				FermataConLinea f1 = new FermataConLinea(rs.getInt("id1"), rs.getString("nome1"), new LatLng(rs.getDouble("x1"), rs.getDouble("y1")),rs.getInt("id_linea"));
				FermataConLinea f2 = new FermataConLinea(rs.getInt("id2"), rs.getString("nome2"), new LatLng(rs.getDouble("x2"), rs.getDouble("y2")),rs.getInt("id_linea"));
				Double velocita= rs.getDouble("velocita");
				
				CoppiaFermate c= new CoppiaFermate(f1, f2, velocita);
				coppie.add(c);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return coppie;
	}
	
	public Map<Integer, Linea> getLinee() {

		final String sql = "SELECT id_linea, nome, intervallo FROM linea";
		
		Map <Integer, Linea> linee=new HashMap<Integer, Linea>();
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				linee.put(rs.getInt("id_linea"),new Linea(rs.getInt("id_linea"),rs.getString("nome"), rs.getDouble("intervallo")));
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return linee;
	}

}

