package com.example.crowdzero.models;

public class Usuario {
    private Integer ID;
    private String Nome;
    private Integer Nivel;
    private Integer Pontos;
    private Integer Reportes;
    private Integer Conquistas;
    private Integer Divisao;
    private String Token;
//*************************************************************************************

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        this.Nome = nome;
    }

    public Integer getNivel() {
        return Nivel;
    }

    public void setNivel(Integer nivel) {
        this.Nivel = nivel;
    }

    public Integer getPontos() {
        return Pontos;
    }

    public void setPontos(Integer pontos) {
        this.Pontos = pontos;
    }

    public Integer getReportes() {
        return Reportes;
    }

    public void setReportes(Integer reportes) {
        this.Reportes = reportes;
    }

    public Integer getConquistas() {
        return Conquistas;
    }

    public void setConquistas(Integer conquistas) {
        this.Conquistas = conquistas;
    }

    public Integer getDivisao() {
        return Divisao;
    }

    public void setDivisao(Integer divisao) {
        this.Divisao = divisao;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        this.Token = token;
    }

    //***************************************************************************************
    public static class UsuarioBuilder{
        private Integer ID;
        private String Nome;
        private Integer Nivel;
        private Integer Pontos;
        private Integer Reportes;
        private Integer Conquistas;
        private Integer Divisao;
        private String Token;
        //************************************************************************

    public UsuarioBuilder setID(Integer ID) {
        this.ID = ID;
        return this;
    }

    public UsuarioBuilder setNome(String nome) {
        this.Nome = nome;
        return this;
    }

    public UsuarioBuilder setNivel(Integer nivel) {
        Nivel = nivel;
        return this;
    }

    public UsuarioBuilder setPontos(Integer pontos) {
        this.Pontos = pontos;
        return this;
    }

    public UsuarioBuilder setReportes(Integer reportes) {
        this.Reportes = reportes;
        return this;
    }

    public UsuarioBuilder setConquistas(Integer conquistas) {
        this.Conquistas = conquistas;
        return this;
    }

    public UsuarioBuilder setDivisao(Integer divisao) {
        this.Divisao = divisao;
        return this;
    }

    public UsuarioBuilder setToken(String token) {
        Token = token;
        return this;
    }

        //*************************************************************************
    private UsuarioBuilder(){}
    public  static  UsuarioBuilder builder(){return new UsuarioBuilder();}

    public Usuario build(){
        Usuario usuario = new Usuario();
        usuario.ID = ID;
        usuario.Nome = Nome;
        usuario.Nivel = Nivel;
        usuario.Pontos = Pontos;
        usuario.Reportes = Reportes;
        usuario.Conquistas = Conquistas;
        usuario.Divisao = Divisao;
        usuario.Token = Token;
        return usuario;
    }
}


}
