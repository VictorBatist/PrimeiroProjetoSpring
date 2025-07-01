package br.com.victor.screenmatch.view;

import br.com.victor.screenmatch.requestApi.RequestApiKey;
import br.com.victor.screenmatch.service.ConsumoApi;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Principal implements RequestApiKey {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=" + RequestApiKey.getApiKey() ;

    public Principal() throws FileNotFoundException {
    }

    public  void exibeMenu(){
        System.out.println("Digite o nome de uma série para busca:");
        var nomeSerie = scanner.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ","+") + API_KEY);
    }
}
