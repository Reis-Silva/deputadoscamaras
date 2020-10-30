package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

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
	private List<Deputados> filtrodeputados;
	private Details detaildeputados;
	private GetDetails detailsListDeputados;	
	
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
			setDetailsListDeputados(WebAPI.listardetalhesdeputados(id));
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
			successExport(true);
		} catch (JsonIOException e) {
			e.printStackTrace();
			successExport(false);
		} catch (IOException e) {
			e.printStackTrace();
			successExport(false);
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
			successExport(true);
		} catch (JsonIOException e) {
			e.printStackTrace();
			successExport(false);
		} catch (IOException e) {
			e.printStackTrace();
			successExport(false);
		}
	}

	public void successExport(Boolean success) {
		if (success) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Arquivo salvo em C:\\JsonExport"));
		}else {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed!", "Nao foi possivel salvar..."));
		}
	}

	@PostConstruct
	public void init() {

		buscarDeputados();
	}
	
	public void onRowSelect(SelectEvent event) {
    	Deputados detail = ((Deputados)event.getObject());
    	setDeputado(detail);
    }
}
