package br.com.victor.screenmatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

    public String obterDados(String address){
        // cliente
        HttpClient client = HttpClient.newHttpClient();
        // requisição
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(address))
                .build();
        // resposta
        HttpResponse<String> response = null;

        try {
            response = client
                    .send(request,HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String json = response.body();
        return json;
    }
}
