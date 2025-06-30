package br.com.victor.screenmatch;

import br.com.victor.screenmatch.model.DadosEpisodio;
import br.com.victor.screenmatch.model.DadosSerie;
import br.com.victor.screenmatch.model.DadosTemporada;
import br.com.victor.screenmatch.requestApi.RequestApiKey;
import br.com.victor.screenmatch.service.ConsumoApi;
import br.com.victor.screenmatch.service.ConverterDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner, RequestApiKey {

	public static void main(String[] args){
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String serie;

		serie = "gilmore+girls";

		var consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=" + serie + "&apikey=" + RequestApiKey.getApiKey());
//		System.out.println(json);
//		json = consumoApi.obterDados("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);

		ConverterDados conversor = new ConverterDados();
		DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dadosSerie);

		json =consumoApi.obterDados("https://www.omdbapi.com/?t=" + serie + "&season=1&episode=1&apikey=" + RequestApiKey.getApiKey());
		DadosEpisodio dadosEpisodio = conversor.obterDados(json,DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		List<DadosTemporada> temporadas = new ArrayList<>();

		for(int i = 1; i <= dadosSerie.totalTemporadas(); i++){
			json =consumoApi.obterDados("https://www.omdbapi.com/?t=" + serie + "&season=" + i + "&apikey=" + RequestApiKey.getApiKey());
			DadosTemporada dadosTemporada = conversor.obterDados(json,DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);
	}
}
