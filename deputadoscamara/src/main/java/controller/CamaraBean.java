package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import entity.Deputados;
import entity.Details;
import webservice.GetDetails;
import webservice.WebAPI;

@ViewScoped
@ManagedBean
public class CamaraBean implements Serializable{


	private static final long serialVersionUID = 1L;
	private Deputados deputado = new Deputados();
	private String selecaoDeputados;
	private List<Deputados> deputados;
	private List<Deputados> filtrodeputados;
	private Details detaildeputados;
	private GetDetails detailsListDeputados;	
	
	public Deputados getDeputado() {
		return deputado;
	}

	public void setDeputado(Deputados deputado) {
		this.deputado = deputado;
	}

	public String getSelecaoDeputados() {
		return selecaoDeputados;
	}

	public void setSelecaoDeputados(String selecaoDeputados) {
		this.selecaoDeputados = selecaoDeputados;
	}

	public List<Deputados> getDeputados() {
		return deputados;
	}

	public void setDeputados(List<Deputados> deputados) {
		this.deputados = deputados;
	}

	public List<Deputados> getFiltrodeputados() {
		return filtrodeputados;
	}

	public void setFiltrodeputados(List<Deputados> filtrodeputados) {
		this.filtrodeputados = filtrodeputados;
	}

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

	public void buscarDeputados() {
		try {
			setDeputados(WebAPI.listardeputados());

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void buscarDetalhesDeputados(int id) {
		try {
			if(getDeputado().getId() == null) {
				successExport(true, "! - ", "Selecione um deputado");
			}else {
				setDetailsListDeputados(WebAPI.listardetalhesdeputados(id));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exportarjsonIndividual() {
		Gson gson = new Gson();

		String json = gson.toJson(detailsListDeputados);

		try {

			File file = new File("C:\\JsonExport", "IDIndividual.json");
			if (!file.getParentFile().exists()) {
				if (file.getParentFile().mkdirs()) {
					file.createNewFile();
				} else {
					throw new IOException("Failed to create directory " + file.getParent());
				}
			}
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(json);
			fileWriter.close();
			successExport(true, "Success!", "Arquivo salvo em C:\\JsonExport");
		} catch (JsonIOException e) {
			e.printStackTrace();
			successExport(false, "Failed!", "Nao foi possivel salvar...");
		} catch (IOException e) {
			e.printStackTrace();
			successExport(false, "Failed!", "Nao foi possivel salvar...");
		}
	}

	public void exportarjsonList() {
		Gson gson = new Gson();

		String json = gson.toJson(deputados);

		try {

			File file = new File("C:\\JsonExport", "IDLista.json");
			if (!file.getParentFile().exists()) {
				if (file.getParentFile().mkdirs()) {
					file.createNewFile();
				} else {
					throw new IOException("Failed to create directory " + file.getParent());
				}
			}
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(json);
			fileWriter.close();
			successExport(true,"Success!", "Arquivo salvo em C:\\JsonExport");
		} catch (JsonIOException e) {
			e.printStackTrace();
			successExport(false, "Failed!", "Nao foi possivel salvar...");
		} catch (IOException e) {
			e.printStackTrace();
			successExport(false, "Failed!", "Nao foi possivel salvar...");
		}
	}

	public void successExport(Boolean success, String Afirmation, String details) {
		if (success) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, Afirmation, details));
		}else {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, Afirmation, details));
		}
	}

	@PostConstruct
	public void init() {

		buscarDeputados();
	}
	
	@SuppressWarnings("rawtypes")
	public void onRowSelect(SelectEvent event) {
    	Deputados detail = ((Deputados)event.getObject());
    	setDeputado(detail);
    	setSelecaoDeputados("PF('details').show();");
    }
}
