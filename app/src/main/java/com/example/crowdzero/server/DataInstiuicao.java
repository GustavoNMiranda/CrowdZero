package com.example.crowdzero.server;

public class DataInstiuicao {
    private String id;
    private String nome_instituicao;
    private String estado_instituicao;
    private String latitude;
    private String longitude;
    private String token_acesso;
    private String qnt_espacos;
    private String qnt_associados;
    private String contacto_instituicao;
    private String ultimo_reporte;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome_instituicao() {
        return nome_instituicao;
    }

    public void setNome_instituicao(String nome_instituicao) {
        this.nome_instituicao = nome_instituicao;
    }

    public String getEstado_instituicao() {
        return estado_instituicao;
    }

    public void setEstado_instituicao(String estado_instituicao) {
        this.estado_instituicao = estado_instituicao;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getToken_acesso() {
        return token_acesso;
    }

    public void setToken_acesso(String token_acesso) {
        this.token_acesso = token_acesso;
    }

    public String getQnt_espacos() {
        return qnt_espacos;
    }

    public void setQnt_espacos(String qnt_espacos) {
        this.qnt_espacos = qnt_espacos;
    }

    public String getQnt_associados() {
        return qnt_associados;
    }

    public void setQnt_associados(String qnt_associados) {
        this.qnt_associados = qnt_associados;
    }

    public String getContacto_instituicao() {
        return contacto_instituicao;
    }

    public void setContacto_instituicao(String contacto_instituicao) {
        this.contacto_instituicao = contacto_instituicao;
    }

    public String getUpdatedAT() {
        return ultimo_reporte;
    }

    public void setUpdatedAT(String updatedAT) {
        this.ultimo_reporte = updatedAT;
    }

    @Override
    public String toString() {
        return "DataInstiuicao{" +
                "id='" + id + '\'' +
                ", nome_instituicao='" + nome_instituicao + '\'' +
                ", estado_instituicao='" + estado_instituicao + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", token_acesso='" + token_acesso + '\'' +
                ", qnt_espacos='" + qnt_espacos + '\'' +
                ", qnt_associados='" + qnt_associados + '\'' +
                ", contacto_instituicao='" + contacto_instituicao + '\'' +
                ", updatedAT='" + ultimo_reporte + '\'' +
                '}';
    }
}
