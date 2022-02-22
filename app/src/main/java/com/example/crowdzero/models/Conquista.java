package com.example.crowdzero.models;

public class Conquista
{
    private String Nome;
    private String NPontos;
    private boolean Feito;

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public String getNPontos() {
        return NPontos;
    }

    public void setNPontos(String NPontos) {
        this.NPontos = NPontos;
    }

    public boolean isFeito() {
        return Feito;
    }

    public void setFeito(boolean feito) {
        this.Feito = feito;
    }

    public static class ConquistaBuilder {
        private String Nome;
        private String NPontos;
        private boolean Feito;

        public ConquistaBuilder setNome(String nome) {
            this.Nome = nome;
            return this;
        }

        public ConquistaBuilder setNPontos(String NPontos) {
            this.NPontos = NPontos;
            return this;
        }

        public ConquistaBuilder setFeito(boolean feito) {
            this.Feito = feito;
            return this;
        }

        private ConquistaBuilder(){}
        public static ConquistaBuilder builder(){
            return new ConquistaBuilder();
        }

        public Conquista build(){
            Conquista conquista = new Conquista();
            conquista.Nome = Nome;
            conquista.NPontos = NPontos;
            conquista.Feito = Feito;
            return conquista;
        }

    }
}
