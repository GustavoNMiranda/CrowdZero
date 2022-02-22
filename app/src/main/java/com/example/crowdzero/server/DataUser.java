package com.example.crowdzero.server;

public class DataUser {
    private String id;
    private String nome;
    private String nivel;
    private String conquistas;
    private String email_user;
    private String password_user;
    private String pontuacao;
    private String reportes;
    private String divisao;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getConquistas() {
        return conquistas;
    }

    public void setConquistas(String conquistas) {
        this.conquistas = conquistas;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getPassword_user() {
        return password_user;
    }

    public void setPassword_user(String password_user) {
        this.password_user = password_user;
    }

    public String getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(String pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getReportes() {
        return reportes;
    }

    public void setReportes(String reportes) {
        this.reportes = reportes;
    }

    public String getDivisao() {
        return divisao;
    }

    public void setDivisao(String divisao) {
        this.divisao = divisao;
    }

    @Override
    public String toString() {
        return "DataUser{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", nivel='" + nivel + '\'' +
                ", conquistas='" + conquistas + '\'' +
                ", email_user='" + email_user + '\'' +
                ", password_user='" + password_user + '\'' +
                ", pontuacao='" + pontuacao + '\'' +
                ", reportes='" + reportes + '\'' +
                ", divisao='" + divisao + '\'' +
                '}';
    }
}
