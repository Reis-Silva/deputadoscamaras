package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import entity.Deputados;
import entity.Details;
import webservice.GetDetails;
import webservice.WebAPI;

@ManagedBean
public class CamaraBean {
	
	private Deputados deputado;
	private List<Deputados> deputados;
	private Details detaildeputados;
	private GetDetails detailsListDeputados;
	
	public Details getDetaildeputados() {
		return detaildeputados;
	}
	public void setDetaildeputados(Details detaildeputados) {
		this.detaildeputados = detaildeputados;
	}
	public GetDetails getDetailsListDeputados() {
		return detailsListDeputados;
	}
	public void setDetailsListDeputados(GetDetails detailsListDeputados) {
		this.detailsListDeputados = detailsListDeputados;
	}
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
	
	public void buscarDetalhesDeputados(int id) {
		try {
			setDetailsListDeputados(WebAPI.listardetalhesdeputados(id));
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
