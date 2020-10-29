package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import entity.Deputados;
import entity.Details;
import webservice.GetDetails;
import webservice.WebAPI;

@ApplicationScoped
@ManagedBean
public class CamaraBean {
	
	private Deputados deputado;
	private List<Deputados> deputados;
	private Details detaildeputados;
	private GetDetails detailsListDeputados;
	public int id;
	
	
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
	
	public void exportarjsonIndividual() {
		Gson gson = new Gson();
	
		String json = gson.toJson(detailsListDeputados);
		
		try {
			FileWriter fileWriter= new FileWriter("C:\\Users\\BRAINIAC\\Desktop\\teste\\IDIndividual.json");
			fileWriter.write(json);
			fileWriter.close();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	
	public void exportarjsonList() {
		Gson gson = new Gson();
	
		String json = gson.toJson(deputados);
		
		
		try {
			File file = new File("","IDLista.json");
			if(!file.getParentFile().exists()) {
				if(file.getParentFile().mkdirs()) {
					file.createNewFile();
				} else {
					throw new IOException("Failed to create directory " + file.getParent());
				}
			}
			FileWriter fileWriter= new FileWriter(file);
			fileWriter.write(json);
			fileWriter.close();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@PostConstruct
	public void init() {
		
		buscarDeputados();
		
	}
	
}
