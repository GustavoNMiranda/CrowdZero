package com.example.crowdzero.server;

import java.util.ArrayList;

public class RespostaRank {
    private boolean success;
    private ArrayList<DataRank> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<DataRank> getData() {
        return data;
    }

    public void setData(ArrayList<DataRank> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespostaRank{" +
                "success=" + success +
                ", data=" + data +
                '}';
    }
}
