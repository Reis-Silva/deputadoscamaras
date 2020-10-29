package webservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import entity.Deputados;

public class WebAPI {
	
	// Armazenando Dados na lista
		public static List<Deputados> listarCotas(String moeda, String dataInicial, String dataFinal) throws Exception {

			WebAPI ws = new WebAPI();
			String url = "https://dadosabertos.camara.leg.br/api/v2/deputados?ordem=ASC&ordenarPor=nome&formato=json";

			String json = ws.obterDados(url);
			Gson g = new Gson();
			GetDeputados deputadosReposit = new GetDeputados();
			deputadosReposit = g.fromJson(json, GetDeputados.class);

			List<Deputados> dadosDeputados = convertArrayToList(deputadosReposit.getDados());
			return dadosDeputados;
		}

		// Obtendo dados da URL
		public String obterDados(String url) throws Exception {

			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				System.out.println("Erro " + conn.getResponseCode() + " ao obter dados da URL " + url);
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			StringBuffer response = new StringBuffer();

			String line;
			while ((line = br.readLine()) != null) {
				response.append(line);
			}

			conn.disconnect();
			return response.toString();
		}

		// Conversão de Arrays
		public static <T> List<T> convertArrayToList(T array[]) {
			List<T> list = new ArrayList<>();
			for (T t : array) {
				list.add(t);
			}
			return list;
		}
	
}
