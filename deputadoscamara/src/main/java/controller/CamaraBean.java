package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import entity.Deputados;
import webservice.WebAPI;

@ManagedBean
public class CamaraBean {
	
	private Deputados deputado;
	private List<Deputados> deputados;
	
	public Deputados getDeputado() {
		return deputado;
	}
	public void setDeputado(Deputados deputado) {
		this.deputado = deputado;
	}
	public List<Deputados> getDeputados() {
		return deputados;
	}
	public void setDeputados(List<Deputados> deputados) {
		this.deputados = deputados;
	}
	
	public void buscarDeputados(){
		try {
			setDeputados(WebAPI.listardeputados());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PostConstruct
	public void init() {
		
		buscarDeputados();
		
	}
	
}
