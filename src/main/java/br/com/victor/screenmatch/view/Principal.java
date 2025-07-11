package br.com.victor.screenmatch.view;

import br.com.victor.screenmatch.model.DadosEpisodio;
import br.com.victor.screenmatch.model.DadosSerie;
import br.com.victor.screenmatch.model.DadosTemporada;
import br.com.victor.screenmatch.model.Episodio;
import br.com.victor.screenmatch.requestApi.RequestApiKey;
import br.com.victor.screenmatch.service.ConsumoApi;
import br.com.victor.screenmatch.service.ConverteDados;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal implements RequestApiKey {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=" + RequestApiKey.getApiKey() ;

    public Principal() throws FileNotFoundException {
    }

    public  void exibeMenu(){
        System.out.println("Digite o nome de uma série para busca:");
        var nomeSerie = scanner.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ","+") + API_KEY);

        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        		List<DadosTemporada> temporadas = new ArrayList<>();

		for(int i = 1; i <= dadosSerie.totalTemporadas(); i++){
			json =consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ","+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json,DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

        for (int i = 0; i < dadosSerie.totalTemporadas(); i++) {
            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodioList();
            for (int j = 0; j < episodiosTemporada.size(); j++) {
                System.out.println(episodiosTemporada.get(j).titulo());
            }
        }

        temporadas.forEach(t -> t.episodioList().forEach(e -> System.out.println(e.titulo())));

//        List<String> nomes = Arrays.asList("Joao","Victoria", "Victor", "Neide", "Aghata", "Karine");
//
//        nomes.stream()
//                .sorted() // Ordena os nomes
//                //.limit(4) // limita aos 4 primeiros nomes
//                .filter(v-> v.startsWith("V")) // filtra todos os nomes começados com V
//                .map(v -> v.toUpperCase()) // Transforma os nomes em Caixa Alta
//                .forEach(System.out::println); // imprime os dados

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t-> t.episodioList().stream())
                .collect(Collectors.toList());

        dadosEpisodios.stream()
                .filter(e -> !e.avalicao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avalicao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodioList().stream()
                .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("Apartir de que ano você deseja ver os episodios :" );

        var ano = scanner.nextInt();
        scanner.nextLine();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        episodios.stream()
                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(dataBusca))
                .forEach( e -> System.out.println(
                        "\nTemporada: " + e.getTemporada() +
                        "\nEpisódio: " + e.getTitulo() +
                        " | Data lançamento: " + e.getDataLancamento().format(formatter)
                ));
    }
}
