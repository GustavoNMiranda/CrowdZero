package com.example.crowdzero.server;

public class DataConquista {
    private String nivel;
    private String divisao;
    private String pontuacao;

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getDivisao() {
        return divisao;
    }

    public void setDivisao(String divisao) {
        this.divisao = divisao;
    }

    public String getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(String pontuacao) {
        this.pontuacao = pontuacao;
    }

    @Override
    public String toString() {
        return "DataConquista{" +
                "nivel='" + nivel + '\'' +
                ", divisao='" + divisao + '\'' +
                ", pontuacao='" + pontuacao + '\'' +
                '}';
    }
}
