package com.example.crowdzero.server;

import java.util.ArrayList;

public class RespostaLocais {
    private Boolean success;
    private ArrayList<DataLocais> data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArrayList<DataLocais> getData() {
        return data;
    }

    public void setData(ArrayList<DataLocais> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespostaLocais{" +
                "success=" + success +
                ", data=" + data +
                '}';
    }
}
