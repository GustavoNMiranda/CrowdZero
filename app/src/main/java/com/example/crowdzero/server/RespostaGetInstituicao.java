package com.example.crowdzero.server;

import java.util.ArrayList;

public class RespostaGetInstituicao {
    private Boolean success;
    private ArrayList<DataInstiuicao> data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArrayList<DataInstiuicao> getData() {
        return data;
    }

    public void setData(ArrayList<DataInstiuicao> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespostaGetInstituicao{" +
                "success=" + success +
                ", data=" + data +
                '}';
    }
}
