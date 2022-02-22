package com.example.crowdzero.server;

public class RespostaConquista {

    private String success;
    private DataConquista data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public DataConquista getData() {
        return data;
    }

    public void setData(DataConquista data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespostaConquista{" +
                "success='" + success + '\'' +
                ", data=" + data +
                '}';
    }
}
