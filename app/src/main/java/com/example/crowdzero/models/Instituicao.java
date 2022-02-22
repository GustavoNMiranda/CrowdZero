package com.example.crowdzero.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.crowdzero.Estatisticas;

public class Instituicao implements Parcelable {
    private Integer ID;
    private String Nome;
    private String Endereco;
    private Integer Estado;
    private Double Latitude;
    private Double Longitude;
    private String U_Reporte;
    public Instituicao() {

    }

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

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        this.Endereco = endereco;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        this.Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        this.Longitude = longitude;
    }

    public Integer getEstado() {
        return Estado;
    }

    public void setEstado(Integer estado) {
        this.Estado = estado;
    }

    public String getU_Reporte() {
        return this.U_Reporte;
    }

    public void setU_Reporte(String u_Reporte) {
        U_Reporte = u_Reporte;
    }



    //***************************************************************************************

    protected Instituicao(Parcel in) {
        if (in.readByte() == 0) {
            ID = null;
        } else {
            ID = in.readInt();
        }
        Nome = in.readString();
        Endereco = in.readString();
        if (in.readByte() == 0) {
            Estado = null;
        } else {
            Estado = in.readInt();
        }
        if (in.readByte() == 0) {
            Latitude = null;
        } else {
            Latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            Longitude = null;
        } else {
            Longitude = in.readDouble();
        }
        U_Reporte = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (ID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ID);
        }
        dest.writeString(Nome);
        dest.writeString(Endereco);
        if (Estado == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Estado);
        }
        if (Latitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Latitude);
        }
        if (Longitude == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(Longitude);
        }
        dest.writeString(U_Reporte);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Instituicao> CREATOR = new Creator<Instituicao>() {
        @Override
        public Instituicao createFromParcel(Parcel in) {
            return new Instituicao(in);
        }

        @Override
        public Instituicao[] newArray(int size) {
            return new Instituicao[size];
        }
    };


    //***************************************************************************************

    public static class InstituicaoBuilder {
        private Integer ID;
        private String Nome;
        private String Endereco;
        private Integer Estado;
        private Double Latitude;
        private Double Longitude;
        private String U_Reporte;
        //************************************************************************

        public InstituicaoBuilder setID(Integer ID) {
            this.ID = ID;
            return this;
        }

        public InstituicaoBuilder setNome(String nome) {
            this.Nome = nome;
            return this;
        }

        public InstituicaoBuilder setEndereco(String endereco) {
            this.Endereco = endereco;
            return this;
        }

        public InstituicaoBuilder setLatitude(Double latitude) {
            this.Latitude = latitude;
            return this;
        }

        public InstituicaoBuilder setLongitude(Double longitude) {
            this.Longitude = longitude;
            return this;
        }

        public InstituicaoBuilder setEstado(Integer estado) {
            this.Estado = estado;
            return this;
        }

        public InstituicaoBuilder setU_Reporte(String u_Reporte) {
            U_Reporte = u_Reporte;
            return this;
        }

        //*************************************************************************
        private InstituicaoBuilder(){}
        public static InstituicaoBuilder builder(){return new InstituicaoBuilder();}

        public Instituicao build(){
            Instituicao instituicao = new Instituicao();
            instituicao.ID = ID;
            instituicao.Nome = Nome;
            instituicao.Endereco = Endereco;
            instituicao.Estado = Estado;
            instituicao.Latitude = Latitude;
            instituicao.Longitude = Longitude;
            instituicao.U_Reporte = U_Reporte;
            return instituicao;
        }
    }
}
