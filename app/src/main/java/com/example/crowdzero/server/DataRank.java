package com.example.crowdzero.server;

public class DataRank {
    private String nome_user;
    private String pontuacao_user;
    private String divisão;

    public String getNome_user() {
        return nome_user;
    }

    public void setNome_user(String nome_user) {
        this.nome_user = nome_user;
    }

    public String getPontuacao_user() {
        return pontuacao_user;
    }

    public void setPontuacao_user(String pontuacao_user) {
        this.pontuacao_user = pontuacao_user;
    }

    public String getDivisao() {
        return divisão;
    }

    public void setDivisao(String divisao) {
        this.divisão = divisao;
    }

    @Override
    public String toString() {
        return "DataRank{" +
                "nome_user='" + nome_user + '\'' +
                ", pontuacao_user='" + pontuacao_user + '\'' +
                ", divisao='" + divisão + '\'' +
                '}';
    }



}
