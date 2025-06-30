package br.com.victor.screenmatch.service;

public interface IConverterDados {
    <T> T obterDados (String json, Class<T> classe);
}
