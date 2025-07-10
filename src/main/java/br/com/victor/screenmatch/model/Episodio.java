package br.com.victor.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    private Integer temporada;
    private String titulo;
    private Integer numeroEposodios;
    private Double avalicao;
    private LocalDate dataLancamento;

    public Episodio(Integer numeroTemporada, DadosEpisodio dadosEpisodio) {
        this.temporada = numeroTemporada;
        this.titulo = dadosEpisodio.titulo();
        this.numeroEposodios = dadosEpisodio.numero();

        try {
            this.avalicao = Double.valueOf(dadosEpisodio.avalicao());
        } catch (NumberFormatException e) {
            this.avalicao = 0.0;
        }

        try{
            this.dataLancamento = LocalDate.parse(dadosEpisodio.dataLancamento());
        }catch (DateTimeParseException e){
            this.dataLancamento = null;
        }

    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroEposodios() {
        return numeroEposodios;
    }

    public void setNumeroEposodios(Integer numeroEposodios) {
        this.numeroEposodios = numeroEposodios;
    }

    public Double getAvalicao() {
        return avalicao;
    }

    public void setAvalicao(Double avalicao) {
        this.avalicao = avalicao;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    @Override
    public String toString() {
        return  "temporada=" + temporada +
                ", titulo='" + titulo + '\'' +
                ", numeroEposodios=" + numeroEposodios +
                ", avalicao=" + avalicao +
                ", dataLancamento=" + dataLancamento ;
    }
}
