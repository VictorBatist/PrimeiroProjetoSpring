package br.com.victor.screenmatch.requestApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public interface RequestApiKey {
    public static String getApiKey() throws FileNotFoundException {
        File file = new File("/home/victor/Projects_IntelliJ/screenmatch/src/api/key/api_key.txt");
        Scanner scannerApi = new Scanner(file);
        String apiKey = scannerApi.nextLine();
        return apiKey;
    }
}
